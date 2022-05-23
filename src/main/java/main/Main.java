package main;

import a2s.Query;
import connector.MySQLConnector;
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
        //Initialize service to update A2S and RCON information every 30 seconds.
        A2SUpdater.init();
        RconUpdater.init();
        //Intialize event emitter service
        EventEmitter.init();
        //Initialize log tailer service
        TailerService.init();

        MySQLConnector.init();

        //Initialize squad server
        SquadServer.init();
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
