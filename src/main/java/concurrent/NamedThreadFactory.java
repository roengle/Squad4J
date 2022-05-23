package concurrent;

import java.util.concurrent.ThreadFactory;

/**
 * Class that extends {@link ThreadFactory} to provides a name that can be passed into the Threads constructor
 * via the {@link Thread#Thread(Runnable, String)} method.
 */
public class NamedThreadFactory implements ThreadFactory {
    private final String name;

    /**
     * Constructs a {@link NamedThreadFactory}.
     *
     * @param name the name of the thread
     */
    public NamedThreadFactory(String name){
        this.name = name;
    }

    /**
     * Overrides the {@link ThreadFactory#newThread(Runnable)} method, by creating a new thread with a given name.
     *
     * @param r the {@link Runnable} to create the thread upon
     * @return a new {@link Thread} with the desired {@link Runnable} and name
     */
    @Override
    public Thread newThread(Runnable r) {
        if(name != null){
            return new Thread(r, name);
        }
        return null;
    }
}
