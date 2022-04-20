package server;

import entity.Player;
import event.Event;
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

    private SquadServer(){

    }

    public static void init(){
        if(initialized)
            throw new IllegalStateException("This class is already initialized.");

        initialized = true;
    }

    protected static void receiveEvent(Event event){

    }
}
