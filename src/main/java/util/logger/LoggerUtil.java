package util.logger;

import ch.qos.logback.classic.Level;
import util.ConfigLoader;

/**
 * Simple class to control logger as configured through configuration file.
 */
public class LoggerUtil {
    private static boolean initialized = false;

    public static void init(){
        if(initialized)
            throw new IllegalStateException("This class is already initialized.");

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);

        String loggerLevel = ConfigLoader.get("$.logging.level", String.class).toLowerCase();
        switch(loggerLevel){
            case "trace":
                root.setLevel(Level.TRACE);
                break;
            case "debug":
                root.setLevel(Level.DEBUG);
                break;
            case "warn":
                root.setLevel(Level.WARN);
                break;
            case "error":
                root.setLevel(Level.ERROR);
                break;
            case "all":
                root.setLevel(Level.ALL);
                break;
            default:
                root.setLevel(Level.INFO);
        }

        initialized = true;
    }
}
