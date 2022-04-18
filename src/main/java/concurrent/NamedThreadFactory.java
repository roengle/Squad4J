package concurrent;

import java.util.concurrent.ThreadFactory;

/**
 * Class to extend {@link ThreadFactory} that provides a name that can be passed into the Threads constructor
 * via the {@link Thread#Thread(Runnable, String)} method.
 */
public class NamedThreadFactory implements ThreadFactory {
    private final String name;

    public NamedThreadFactory(String name){
        this.name = name;
    }

    public Thread newThread(Runnable r) {
        if(name != null){
            return new Thread(r, name);
        }
        return null;
    }
}
