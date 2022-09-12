package main;

import a2s.Query;
import connector.MySQLConnector;
import event.Event;
import event.EventType;
import event.logparser.ServerTickRateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rcon.Rcon;
import server.EventEmitter;
import server.SquadServer;
import server.tailer.TailerService;
import util.logger.LoggerUtil;

import java.util.Date;

/**
 *    _____                       _ _  _       _
 *   / ____|                     | | || |     | |
 *  | (___   __ _ _   _  __ _  __| | || |_    | |
 *   \___ \ / _` | | | |/ _` |/ _` |__   _|   | |
 *   ____) | (_| | |_| | (_| | (_| |  | || |__| |
 *  |_____/ \__, |\__,_|\__,_|\__,_|  |_| \____/
 *             | |
 *             |_|
 * <p>
 * Main entry point for Squad4J. Initializes all services needed to run Squad4J.
 *
 * @author Robert Engle
 */

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args){
        //Initialize logger before pushing any output to console
        LoggerUtil.init();

        printLogo();

        /* Initialize services */
        //Initialize RCON service
        Rcon.init();
        //Initialize query service
        Query.init();
        //Initialize log tailer service
        TailerService.init();

        /*Initialize servers */
        //Initialize squad server
        SquadServer.init();

        /* Initialize connectors */
        MySQLConnector.init();

        /* IMPORTANT: Initialize event emitter last */
        //Initialize event emitter service
        EventEmitter.init();

        //TODO: Remove me after debugging
        Event testEvent = new ServerTickRateEvent(new Date(), EventType.SERVER_TICK_RATE, 0, 35.5);

        LOGGER.debug("Emitting {}", testEvent);

        EventEmitter.emit(testEvent);
    }

    private static void printLogo() {
        LOGGER.info("   _____                       _   _  _       _ ");
        LOGGER.info("  / ____|                     | | | || |     | |");
        LOGGER.info(" | (___   __ _ _   _  __ _  __| | | || |_    | |");
        LOGGER.info("  \\___ \\ / _` | | | |/ _` |/ _` | |__   _|   | |");
        LOGGER.info("  ____) | (_| | |_| | (_| | (_| |    | || |__| |");
        LOGGER.info(" |_____/ \\__, |\\__,_|\\__,_|\\__,_|    |_| \\____/ ");
        LOGGER.info("            | |                                 ");
        LOGGER.info("            |_|                                 ");
    }
}
