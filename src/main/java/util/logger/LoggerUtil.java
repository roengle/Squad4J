package util.logger;

import ch.qos.logback.classic.Level;
import util.ConfigLoader;

/**
 * Simple class to control logger as configured through configuration file.
 *
 * @author Robert Engle
 */
public class LoggerUtil {
    private static boolean initialized = false;

    private LoggerUtil(){
        throw new IllegalStateException("Utility classes cannot be instantiated.");
    }

    public static void init(){
        if(initialized)
            throw new IllegalStateException("This class is already initialized.");

        //Get root logger
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);

        String loggerLevel = ConfigLoader.get("$.logging.level", String.class).toLowerCase();
        //Set root logger level as per configuration
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
