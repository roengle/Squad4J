package server;

import concurrent.GlobalThreadPool;
import entity.Player;
import entity.Squad;
import event.Event;
import event.EventType;
import event.rcon.LayerInfoUpdatedEvent;
import event.rcon.PlayerListUpdatedEvent;
import event.rcon.SquadListUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rcon.Rcon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RconUpdater {
    private static final Logger LOGGER = LoggerFactory.getLogger(RconUpdater.class);

    private static boolean initialized = false;

    private static final Pattern playerPattern = Pattern.compile("ID: ([0-9]+) \\| SteamID: ([0-9]+) \\| Name: (.+) \\| Team ID: (1|2) \\| Squad ID: ([0-9]+|N\\/A) \\| Is Leader: (True|False) \\| Role: (.+)");
    private static final Pattern squadPattern = Pattern.compile("ID: ([0-9]+) \\| Name: (.+) \\| Size: ([0-9]+) \\| Locked: (True|False) \\| Creator Name: (.+) \\| Creator Steam ID: ([0-9]{17})");
    private static final Pattern currentLayerPattern = Pattern.compile("Current level is (?:.+), layer is (.+)");
    private static final Pattern nextLayerPattern = Pattern.compile("Next level is (?:.+), layer is (.+)");

    private RconUpdater(){
        throw new IllegalStateException("This class cannot be instantiated.");
    }

    public static void init(){
        if(initialized)
            throw new IllegalStateException(RconUpdater.class.getSimpleName() + " has already been initialized.");

        GlobalThreadPool.getScheduler().scheduleAtFixedRate(() -> {
            updatePlayerList();
            updateSquadList();
            updateLayerInfo();
        }, 5, 30, TimeUnit.SECONDS);

        initialized = true;
    }

    protected static void updatePlayerList(){
        LOGGER.info("Retrieving player list.");
        String response = Rcon.command("ListPlayers");
        List<Player> players = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(response, "\n");
        while(tokenizer.hasMoreElements()){
            String line = tokenizer.nextToken();

            Matcher matcher = playerPattern.matcher(line);
            if(matcher.find()){
                Integer id = Integer.valueOf(matcher.group(1));
                String steam64id = matcher.group(2);
                String name = matcher.group(3);
                Integer teamId = Integer.valueOf(matcher.group(4));
                Integer squadId = matcher.group(5).equals("N/A") ? null : Integer.valueOf(matcher.group(5));
                Boolean isLeader = Boolean.valueOf(matcher.group(6));
                String role = matcher.group(7);

                Player player = new Player(id, steam64id, name, teamId, squadId, isLeader, role);
                players.add(player);
            }
        }
        LOGGER.info("Retrieved {} players.", players.size());

        Event event = new PlayerListUpdatedEvent(new Date(), EventType.PLAYERLIST_UPDATED, players);

        EventEmitter.emit(event);
    }

    protected static void updateSquadList(){
        LOGGER.info("Retrieving squad list.");
        String response = Rcon.command("ListSquads");
        List<Squad> squads = new ArrayList<>();
        int teamId = 1;
        StringTokenizer tokenizer = new StringTokenizer(response, "\n");
        while(tokenizer.hasMoreElements()){
            String line = tokenizer.nextToken();
            if(line.startsWith("Team ID: 2")){
                teamId = 2;
                continue;
            }

            Matcher matcher = squadPattern.matcher(line);
            if(matcher.find()){
                Integer id = Integer.valueOf(matcher.group(1));
                String name = matcher.group(2);
                Integer size = Integer.valueOf(matcher.group(3));
                Boolean isLocked = Boolean.valueOf(matcher.group(4));
                String creatorName = matcher.group(5);
                String creatorSteam64id = matcher.group(6);

                Squad squad = new Squad(teamId, id, name, size, isLocked, creatorName, creatorSteam64id);
                squads.add(squad);
            }
        }
        LOGGER.info("Retrieved {} squads.", squads.size());

        Event event = new SquadListUpdatedEvent(new Date(), EventType.SQUADLIST_UPDATED, squads);

        EventEmitter.emit(event);
    }

    protected static void updateLayerInfo(){
        LOGGER.info("Retrieving layer information");
        String currentLayer = "";
        String nextLayer = "";

        LOGGER.trace("Getting current map");
        String response = Rcon.command("ShowCurrentMap");
        Matcher matcher = currentLayerPattern.matcher(response);
        if(matcher.find()){
            currentLayer = matcher.group(1);
            LOGGER.trace("Current layer is {}", currentLayer);
        }

        LOGGER.trace("Getting next map");
        response = Rcon.command("ShowNextMap");
        matcher = nextLayerPattern.matcher(response);
        if(matcher.find()){
            nextLayer = matcher.group(1);
            LOGGER.trace("Next layer is {}", nextLayer);
        }

        Event event = new LayerInfoUpdatedEvent(new Date(), EventType.LAYERINFO_UPDATED, currentLayer, nextLayer);

        LOGGER.info("Retrieved layer information");
        EventEmitter.emit(event);
    }
}
