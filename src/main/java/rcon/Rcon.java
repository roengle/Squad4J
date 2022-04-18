package rcon;

import logparser.LogParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rcon.ex.AuthenticationException;
import util.ConfigLoader;

import java.util.function.Consumer;

/**
 * @author Robert Engle
 *
 * Singleton wrapper for RconImpl
 */
public class Rcon {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rcon.class);

    private static RconImpl rcon;
    private static boolean initialized = false;

    public static void init(){
        if(initialized)
            throw new IllegalStateException("Rcon has already been initialized, you cannot re-initialize it.");

        String host = ConfigLoader.get("$.server.host", String.class);
        Integer port = ConfigLoader.get("$.server.rconPort", Integer.class);
        String password = ConfigLoader.get("$.server.rconPassword", String.class);
        try {
            LOGGER.info("Connecting to RCON server.");
            rcon = new RconImpl(host, port, password);
        } catch (AuthenticationException e) {
            LOGGER.error("Error authenticating with RCON server.");
            LOGGER.error(e.getMessage());
            System.exit(1);
        }
        LOGGER.info("Connected to RCON server.");

        rcon.onRconPacket(rconPacket -> {
            if(rconPacket.getType() == RconImpl.SERVERDATA_BROADCAST){
                LogParser.parseLine(rconPacket.getPayloadAsString());
            }
        });

        initialized = true;

    }

    private Rcon(){}

    public static String command(String cmd){
        return initialized ? rcon.command(cmd) : "";
    }

    //TODO: Figure out if I should be removed.
    public static void onRconPacket(Consumer<RconPacket> consumer){
        rcon.onRconPacket(consumer);
    }
}
