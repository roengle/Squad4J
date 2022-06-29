package main;

import a2s.Query;
import a2s.response.A2SCombinedResponse;
import connector.MySQLConnector;
import event.Event;
import event.EventType;
import event.a2s.A2SUpdatedEvent;
import event.logparser.NewGameEvent;
import event.logparser.PlayerDiedEvent;
import event.logparser.ServerTickRateEvent;
import server.A2SUpdater;
import server.EventEmitter;
import server.RconUpdater;
import server.tailer.TailerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rcon.Rcon;
import server.SquadServer;
import util.ConfigLoader;
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
 *
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

        /* Initialize services, squad server instance, log tailer in order. */
        //Initialize RCON service
        Rcon.init();
        //Initailize query service
        Query.init();

        //Initialize log tailer service
        TailerService.init();

        //Initialize squad server
        SquadServer.init();

        MySQLConnector.init();

        //Intialize event emitter service
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
