package logparser;

import a2s.Query;
import a2s.response.A2SCombinedResponse;
import a2s.response.A2SInfoResponse;
import concurrent.GlobalThreadPool;
import event.Event;
import event.EventType;
import event.a2s.A2SUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class A2SUpdater {
    private static final Logger LOGGER = LoggerFactory.getLogger(A2SUpdater.class);

    private static boolean initialized = false;

    private A2SUpdater(){
        throw new IllegalStateException("This class cannot be instantiated.");
    }

    public static void init(){
        if(initialized)
            throw new IllegalStateException(A2SUpdater.class.getSimpleName() + " has already been initialized.");

        GlobalThreadPool.getScheduler().scheduleAtFixedRate(() -> {
            LOGGER.debug("Retrieving A2S info");
            A2SCombinedResponse response = Query.queryBoth();

            Event event = new A2SUpdatedEvent(new Date(), EventType.A2S_UPDATED, response);

            LOGGER.debug("A2S info received.");

            EventEmitter.emit(event);
        }, 5, 30, TimeUnit.SECONDS);

        initialized = true;

        LOGGER.info("Query service initialized.");
    }
}
