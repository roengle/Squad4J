package server;

import a2s.response.A2SInfoResponse;
import a2s.response.A2SRulesResponse;
import entity.Player;
import event.Event;
import event.EventType;
import event.a2s.A2SUpdatedEvent;
import event.logparser.NewGameEvent;
import event.rcon.LayerInfoUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SquadServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SquadServer.class);

    private static boolean initialized = false;

    private static Integer id;
    private static Map<String, Player> players;
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

    private SquadServer(){

    }

    public static void init(){
        if(initialized)
            throw new IllegalStateException("This class is already initialized.");

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
            case NEW_GAME:
                NewGameEvent newGameEvent = (NewGameEvent) ev;
                maxTickRate = newGameEvent.getMaxTickRate();
                currentLayer = newGameEvent.getLayerName();
                break;
            case LAYERINFO_UPDATED:
                currentLayer = ((LayerInfoUpdatedEvent) ev).getCurrentLayer();
                nextLayer = ((LayerInfoUpdatedEvent) ev).getNextLayer();
                break;

        }
    }
}
