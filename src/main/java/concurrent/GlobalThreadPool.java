package concurrent;

import java.util.concurrent.*;

/**
 * Class to provide global access to thread pool executors.
 *
 * Some implementation from:
 * https://github.com/Javacord/Javacord/blob/master/javacord-core/src/main/java/org/javacord/core/util/concurrent/ThreadPoolImpl.java
 */
public class GlobalThreadPool {
    private static final int CORE_POOL_SIZE = 1;
    private static final int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private static final ExecutorService executorService = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, new SynchronousQueue<>(),
            new NamedThreadFactory("Squad4J - Central ExecutorService"));
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
            CORE_POOL_SIZE, new NamedThreadFactory("Squad4J - Central ScheduledExecutorService"));

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
