package server;

import a2s.response.A2SInfoResponse;
import a2s.response.A2SRulesResponse;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jayway.jsonpath.JsonPathException;
import entity.Player;
import entity.Squad;
import event.Event;
import event.EventType;
import event.a2s.A2SUpdatedEvent;
import event.logparser.*;
import event.rcon.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AdminListReader;
import util.ConfigLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SquadServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SquadServer.class);

    private static boolean initialized = false;

    private static Integer id;

    private static final BiMap<String, String> nameSteamIds = HashBiMap.create();

    private static Collection<Player> players;
    private static Collection<Squad> squads;

    private static List<String> adminSteamIds = new ArrayList<>();

    private static Collection<Player> admins;
    private static Collection<Player> adminsInAdminCam;

    private static String currentLayer;
    private static String nextLayer;

    private static String serverName;
    private static Integer maxPlayers;
    private static Integer publicSlots;
    private static Integer reserveSlots;

    private static Integer playerCount;
    private static Integer publicQueue;
    private static Integer reserveQueue;

    private static String gameVersion;
    private static Double matchTimeout;

    private static Integer maxTickRate;

    private static String mostRecentWinner;

    private SquadServer(){

    }

    public static void init(){
        if(initialized)
            throw new IllegalStateException("This class is already initialized.");

        id = ConfigLoader.get("server.id", Integer.class);
        String sourceRef = null;
        //TODO: Improve admin reading to take union all permissions for UNIQUE ADMINS    across all files. Currently, this logic actually sucks
        try{
            Integer numAdminLists = ConfigLoader.get("server.adminLists.length()", Integer.class);
            for(int i = 0; i < numAdminLists; i++){
                Map<Object, Object> adminListMap = ConfigLoader.get("server.adminLists[" + i + "]", Map.class);
                boolean isRemote = adminListMap.get("type").equals("remote");
                String source = (String) adminListMap.get("source");
                sourceRef = source;
                LOGGER.trace("Reading admins from {}", source);
                //Only use add all for initialization step, when updating the list will need to be replaced
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try{
                        adminSteamIds.addAll(
                                isRemote ? AdminListReader.getAdminIdsFromRemote(source) : AdminListReader.getAdminIdsFromLocal(source)
                        );

                        adminSteamIds = adminSteamIds.stream().distinct().collect(Collectors.toList());
                    } catch(FileNotFoundException fe){
                        LOGGER.error("File {} not found", source, fe);
                    } catch (IOException e) {
                        LOGGER.error("IO Error", e);
                    }
                });
                try{
                    future.get(5, TimeUnit.SECONDS);
                } catch (ExecutionException e) {
                    LOGGER.error("Asynchronous thread threw an exception", e);
                } catch (InterruptedException e) {
                    LOGGER.error("Asynchronous thread interrupted", e);
                } catch (TimeoutException e) {
                    LOGGER.warn("Timeout reading admin list from {}", sourceRef);
                }

                LOGGER.trace("Read {} admins from {}", adminSteamIds.size(), source);
            }
        }catch (JsonPathException jsexp){
            LOGGER.error("Error reading admin list configuration.", jsexp);
        }

        LOGGER.trace("Parsed {} unique admins", adminSteamIds.size());

        initialized = true;
    }

    /**
     * Receives all events from {@link EventEmitter}, updating the server as necessary.
     *
     * @param ev the {@link Event} emitted by {@link EventEmitter}
     */
    protected static void receiveEvent(final Event ev){
        EventType type = ev.getType();

        switch(type){
            //A2S
            case A2S_UPDATED:
                LOGGER.trace("Updating SquadServer A2S info");
                A2SUpdatedEvent a2sEvent = (A2SUpdatedEvent) ev;
                A2SInfoResponse info = a2sEvent.getResponse().getInfo();
                A2SRulesResponse rules = a2sEvent.getResponse().getRules();

                serverName = info.getName();
                maxPlayers = (int) info.getMaxPlayers();

                publicSlots = Integer.valueOf(rules.getRuleValue("NUMPUBCONN"));
                reserveSlots = Integer.valueOf(rules.getRuleValue("NUMPRIVCONN"));
                
                playerCount = Integer.valueOf(rules.getRuleValue("PlayerCount_i"));
                publicQueue = Integer.valueOf(rules.getRuleValue("PublicQueue_i"));
                reserveQueue = Integer.valueOf(rules.getRuleValue("ReservedQueue_i"));

                matchTimeout = Double.valueOf(rules.getRuleValue("MatchTimeout_f"));
                gameVersion = info.getVersion();

                LOGGER.trace("Done updating SquadServer A2S info");

                break;
            //Log Parser
            case NEW_GAME:
                LOGGER.trace("Updating SquadServer for NEW_GAME");
                NewGameEvent newGameEvent = (NewGameEvent) ev;
                maxTickRate = newGameEvent.getMaxTickRate();
                currentLayer = newGameEvent.getLayerName();
                A2SUpdater.updateA2S();
                RconUpdater.updatePlayerList();
                RconUpdater.updateSquadList();
                RconUpdater.updateLayerInfo();

                //TODO: Update admins since these can change between games
                LOGGER.trace("Done updating SquadServer for NEW_GAME");
                break;
            case PLAYER_CONNECTED:
                //TODO: Update admins if player connected has steam id in adminSteamIds
                break;
            case PLAYER_DISCONNECTED:
                LOGGER.trace("Updating SquadServer for PLAYER_DISCONNECTED");
                PlayerDisconnectedEvent playerDisconnectedEvent = (PlayerDisconnectedEvent) ev;
                nameSteamIds.inverse().remove(playerDisconnectedEvent.getSteamid());
                //TODO: Update admins if player disconnected has steam id in adminSteamIds
                LOGGER.trace("Done updating SquadServer for PLAYER_DISCONNECTED");
                break;
            case ROUND_WINNER:
                LOGGER.trace("Updating SquadServer for ROUND_WINNER");
                RoundWinnerEvent roundWinnerEvent = (RoundWinnerEvent) ev;
                mostRecentWinner = roundWinnerEvent.getWinningFaction();
                LOGGER.trace("Done updating SquadServer for ROUDN_WINNER");
                break;
            case STEAMID_CONNECTED:
                LOGGER.trace("Updating SquadServer for STEAMID_CONNECTED");
                SteamidConnectedEvent steamidConnectedEvent = (SteamidConnectedEvent) ev;
                nameSteamIds.put(steamidConnectedEvent.getName(), steamidConnectedEvent.getSteamid());
                LOGGER.trace("Done updating SquadServer for STEAMID_CONNECTED");
                break;
            //Rcon
            case LAYERINFO_UPDATED:
                LOGGER.trace("Updating SquadServer for LAYERINFO_UPDATED");
                currentLayer = ((LayerInfoUpdatedEvent) ev).getCurrentLayer();
                nextLayer = ((LayerInfoUpdatedEvent) ev).getNextLayer();
                LOGGER.trace("Done updating SquadServer for LAYERINFO_UPDATED");
                break;
            case PLAYER_BANNED:
                LOGGER.trace("Updating SquadServer for PLAYER_BANNED");
                PlayerBannedEvent playerBannedEvent = (PlayerBannedEvent) ev;
                nameSteamIds.inverse().remove(playerBannedEvent.getSteamid());
                LOGGER.trace("Done updating SquadServer for PLAYER_BANNED");
                break;
            case PLAYER_KICKED:
                LOGGER.trace("Updating SquadServer for PLAYER_KICKED");
                PlayerKickedEvent playerKickedEvent = (PlayerKickedEvent) ev;
                nameSteamIds.inverse().remove(playerKickedEvent.getSteamid());
                LOGGER.trace("Done updating SquadServer for PLAYER_KICKED");
                break;
            case PLAYERLIST_UPDATED:
                LOGGER.trace("Updating SquadServer for PLAYERLIST_UPDATED");
                PlayerListUpdatedEvent playerListUpdatedEvent = (PlayerListUpdatedEvent) ev;
                players = playerListUpdatedEvent.getPlayerList();
                LOGGER.trace("Done updating SquadServer for PLAYERLIST_UPDATED");
                break;
            case POSSESSED_ADMIN_CAM:
                LOGGER.trace("Updating SquadServer for POSSESSED_ADMIN_CAM");
                PossessedAdminCameraEvent possessedAdminCameraEvent = (PossessedAdminCameraEvent) ev;
                getPlayerBySteamId(possessedAdminCameraEvent.getSteamid()).ifPresent(p -> {
                    adminsInAdminCam.add(p);
                });
                LOGGER.trace("Done updating SquadServer for POSSESSED_ADMIN_CAM");
                break;
            case SQUADLIST_UPDATED:
                LOGGER.trace("Updating SquadServer for SQUADLIST_UPDATED");
                SquadListUpdatedEvent squadListUpdatedEvent = (SquadListUpdatedEvent) ev;
                squads = squadListUpdatedEvent.getSquadList();
                LOGGER.trace("Done updating SquadServer for SQUADLIST_UPDATED");
                break;
            case UNPOSSESSED_ADMIN_CAM:
                LOGGER.trace("Updating SquadServer for UNPOSSESSED_ADMIN_CAM");
                UnpossessedAdminCameraEvent unpossessedAdminCameraEvent = (UnpossessedAdminCameraEvent) ev;
                getPlayerBySteamId(unpossessedAdminCameraEvent.getSteamid()).ifPresent(p -> {
                    adminsInAdminCam.remove(p);
                });
                LOGGER.trace("Done updating SquadServer for UNPOSSESSED_ADMIN_CAM");
                break;
        }
    }

    public static Optional<Player> getPlayerBySteamId(String steam64id){
        return players.stream().filter(player -> player.getSteam64id().equals(steam64id)).findFirst();
    }

    public static Integer getId() {
        return id;
    }

    public static Collection<Player> getPlayers() {
        return Collections.unmodifiableCollection(players);
    }

    public static Collection<Squad> getSquads() {
        return Collections.unmodifiableCollection(squads);
    }

    public static Collection<Player> getAdmins() {
        return Collections.unmodifiableCollection(admins);
    }

    public static Collection<Player> getAdminsInAdminCam() {
        return Collections.unmodifiableCollection(adminsInAdminCam);
    }

    public static String getCurrentLayer() {
        return currentLayer;
    }

    public static String getNextLayer() {
        return nextLayer;
    }

    public static String getServerName() {
        return serverName;
    }

    public static Integer getMaxPlayers() {
        return maxPlayers;
    }

    public static Integer getPublicSlots() {
        return publicSlots;
    }

    public static Integer getReserveSlots() {
        return reserveSlots;
    }

    public static Integer getPlayerCount() {
        return playerCount;
    }

    public static Integer getPublicQueue() {
        return publicQueue;
    }

    public static Integer getReserveQueue() {
        return reserveQueue;
    }

    public static String getGameVersion() {
        return gameVersion;
    }

    public static Double getMatchTimeout() {
        return matchTimeout;
    }

    public static Integer getMaxTickRate() {
        return maxTickRate;
    }

    public static String getMostRecentWinner() {
        return mostRecentWinner;
    }
}
