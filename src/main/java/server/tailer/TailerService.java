package server.tailer;

import server.A2SUpdater;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigLoader;

import java.io.File;

public class TailerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TailerService.class);
    private static boolean initialized = false;

    private TailerService(){
        throw new IllegalStateException("This class cannot be instantiated.");
    }

    public static void init(){
        if(initialized)
            throw new IllegalStateException(A2SUpdater.class.getSimpleName() + " has already been initialized.");

        //Configure log file tailer
        TailerListener listener = new LogTailer();
        String filePath = ConfigLoader.get("$.server.logFilePath", String.class);
        LOGGER.debug("File Path in Config: {}", filePath);
        File configFile = null;
        if(filePath != null){
            configFile = new File(filePath);
        }else{
            LOGGER.error("There was an error reading the file path to the server log file. See above.");
            System.exit(1);
        }
        Tailer tailer = new Tailer(configFile,
                listener,
                50,
                true,
                false,
                10000);
        String absolutePath = configFile.getAbsolutePath();
        LOGGER.info("Watching logfile {}", absolutePath);
        new Thread(tailer).start();

        initialized = true;
        LOGGER.info("Log tailer service initialized");
    }
}
