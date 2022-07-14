package server;

import com.jayway.jsonpath.PathNotFoundException;
import event.Event;
import listener.GloballyAttachableListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ClassHelper;
import util.ConfigLoader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 *
 * @author Robert Engle
 */
public class EventEmitter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventEmitter.class);

    private static boolean initialized = false;

    //Map plugin classes to a single instance for that plugin class
    private static final Map<Class<? extends GloballyAttachableListener>, GloballyAttachableListener> pluginInstances = new HashMap<>();
    //Map an event class to the name of the method it is supposed to call
    private static final Map<Class<? extends Event>, String> eventMethodNames = new HashMap<>();
    //Key: event class, value: List of plugin class instances that contain method corresponding to event
    private static final Map<Class<? extends Event>, List<GloballyAttachableListener>> eventPluginInstances = new HashMap<>();
    //Key: class of the listener for this event, value: event class
    private static final Map<Class<? extends GloballyAttachableListener>, Class<? extends Event>> listenerEvents = new HashMap<>();

    private EventEmitter(){
        throw new IllegalStateException("Utility classes cannot be instantiated.");
    }

    /**
     * Initializes various maps used for event binding.
     */
    @SuppressWarnings("unchecked")
    private static void initMaps(){
        //Get method names for each type of event
        try{
            ClassHelper.getClassesInPackage("listener").filter(intf -> intf != GloballyAttachableListener.class).forEach(listener -> {
                if(!GloballyAttachableListener.class.isAssignableFrom(listener)){
                    LOGGER.error("{} does not extend {}", listener.getSimpleName(), GloballyAttachableListener.class.getSimpleName());
                    return;
                }
                if(listener.getMethods().length != 1){
                    LOGGER.error("{} should only have 1 method. It currently has {} methods.", listener.getSimpleName(), listener.getMethods().length);
                    //Skips this instance in the forEach iteration
                    return;
                }
                Method[] methods = listener.getMethods();
                for (Method method : methods) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    if(!Event.class.isAssignableFrom(paramType)){
                        LOGGER.error("{} does not extend {}", paramType.getSimpleName(), Event.class.getSimpleName());
                        continue;
                    }
                    //Cast safe
                    listenerEvents.put((Class<? extends GloballyAttachableListener>) listener, (Class<? extends Event>) paramType);

                    //Ensure that the parameter type being checked extends Event
                    if(!Event.class.isAssignableFrom(paramType)){
                        LOGGER.error("{} is not a subclass of {}.", paramType.getSimpleName(), Event.class.getSimpleName());
                        //Skips this instance in the forEach iteration
                        return;
                    }
                    eventMethodNames.put((Class<? extends Event>) paramType, method.getName());
                }
            });
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }

        //Initialize single instances for each class in the "plugins" package & associated Event classes with list of plugin classes
        try {
            ClassHelper.getClassesInPackage("plugins").forEach(pluginClass -> {
                if(ClassHelper.getInterfacesAsStream(pluginClass).noneMatch(clazz -> clazz == GloballyAttachableListener.class)){
                    LOGGER.warn("{} does not implement any listeners, skipping event binding.", pluginClass.getSimpleName());
                    //Return here skips this iteration of the forEach loop
                    return;
                }

                String pluginName = pluginClass.getSimpleName();
                try{
                    LinkedHashMap<String, Object> pluginConfiguration = ConfigLoader.get(String.format("$.plugins.%s", pluginName), LinkedHashMap.class);
                    boolean enabled = (boolean) pluginConfiguration.get("enabled");
                    if(!enabled){
                        LOGGER.warn("{} not enabled in config.json, skipping event binding.", pluginClass.getSimpleName());
                        //Return here skips this iteration of the forEach loop
                        return;
                    }
                } catch(PathNotFoundException exp){
                    LOGGER.warn("{} does not have a configuration in config.json, skipping event binding.", pluginClass.getSimpleName());
                    //Return here skips this iteration of the forEach loop
                    return;
                }

                try {
                    Constructor<?> ctor = pluginClass.getConstructor();
                    GloballyAttachableListener pluginInstance = (GloballyAttachableListener) ctor.newInstance();
                    //Cast safe, if statement above ensures it can be cast
                    pluginInstances.put((Class<? extends GloballyAttachableListener>) pluginClass, pluginInstance);
                } catch (NoSuchMethodException e) {
                    LOGGER.error("{} must have a public no-argument constructor.", pluginClass.getSimpleName());
                    LOGGER.error(e.getMessage());
                } catch (InvocationTargetException e) {
                    LOGGER.error("Constructor for {} threw an exception.", pluginClass.getSimpleName());
                    LOGGER.error(e.getMessage());
                } catch (InstantiationException e) {
                    LOGGER.error("{} cannot be instantiated. Make sure it is NOT abstract.", pluginClass.getSimpleName());
                    LOGGER.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    LOGGER.error("{} has a non-public constructor, it cannot be instantiated from {}.", pluginClass.getSimpleName(), EventEmitter.class.getSimpleName());
                    LOGGER.error(e.getMessage());
                }

                ClassHelper.getInterfacesAsStream(pluginClass).filter(intf -> intf != GloballyAttachableListener.class).forEach(intf -> {
                    Class<? extends Event> event = listenerEvents.get(intf);
                    if((!eventPluginInstances.containsKey(event)) || eventPluginInstances.get(event) == null){
                        eventPluginInstances.put(event, new ArrayList<>());
                    }
                    eventPluginInstances.get(event).add(pluginInstances.get(pluginClass));
                });
            });
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }

    /**
     * Initialize singleton EventEmitter. Builds various mappings for event binding, and displays plugin instances
     * as well as plugin instances that fire for respective events.
     */
    public static void init(){
        if(initialized)
            throw new IllegalStateException("This class has already been initialized.");

        initMaps();

        LOGGER.trace("Plugin Instances:");
        pluginInstances.forEach((k, v) -> {
            LOGGER.trace("\t{} - {}", k ,v);
        });

        LOGGER.trace("Event plugin instances:");
        eventPluginInstances.forEach((k, list) -> {
            LOGGER.trace("\t{}", k);
            list.forEach(v -> {
                LOGGER.trace("\t\t{}" ,v);
            });
        });

        initialized = true;
    }

    /**
     * Emits an event to {@link SquadServer} and all plugins that are event-bound.
     *
     * @param event the event to emit.
     */
    public static void emit(Event event) {
        LOGGER.trace("New event emitted: {}", event);
        SquadServer.receiveEvent(event);

        String methodName = eventMethodNames.get(event.getClass());

        List<GloballyAttachableListener> pluginsToRun = eventPluginInstances.get(event.getClass());

        //If event doesn't have any plugins listening in on it, return
        if(pluginsToRun == null){
            return;
        }
        pluginsToRun.forEach(plugin -> {
            try {
                Method methodToRun = plugin.getClass().getMethod(methodName, event.getClass());
                methodToRun.invoke(plugin, event);
            } catch (NoSuchMethodException e) {
                LOGGER.error("Method with name {} not found.", methodName);
                LOGGER.error(e.getMessage());
            } catch (InvocationTargetException e) {
                LOGGER.error("{} threw an exception running on plugin {}.", methodName, plugin.getClass().getSimpleName());
                LOGGER.error(e.getMessage());
            } catch (IllegalAccessException e) {
                LOGGER.error("{} is inaccessible. Ensure that the method is public.", methodName);
                LOGGER.error(e.getMessage());
            }
        });
    }
}
