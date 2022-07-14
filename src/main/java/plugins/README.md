# Creating Your Own Plugins

To write plugins, you need to possess basic knowledge of writing Java code.

Typically, plugins you write will fall into one of these categories:

- Plugins that "react" to one or more events occurring in the server. For instance, a plugin that reacts to all chat messages.
- Plugins that run at some set interval, for example once every minute.
- Plugins that both "react" to events and run code periodically.

## Important Information
The following are important for **all** plugins to function properly for Squad4J. Read the following carefully.

- Due to how Squad4J does event binding using the Java reflections framework, all plugin classes **MUST** be inside the `plugins` package. 
- Plugin classes must have the `public` access modifier. For example `public class MyPlugin`.
- Plugins classes must have a **public**, **no-argument** constructor (default constructor works fine here). This is due to how Squad4J utilizes the Java reflections framework to create an instance of your plugin class.
  - Additionally, you can use the `@NoArgsConstructor` annotation above the class declaration.
- Each plugin **must** have a configuration in the `plugins` configuration in *config.json* that matches **the class name**. See below for more information on this.
  - If a plugin does not have a configuration, it won't be bounded to events.

## Reacting to Events
To have your plugin react to events, simply implement one or more listener interfaces into your class. For example:

```java
import listener.rcon.PossessedAdminCameraListener;
import listener.rcon.UnpossessedAdminCameraListener;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdminCamTracker implements PossessedAdminCameraListener, UnpossessedAdminCameraListener {
    
    //Notice that since @NoArgsConstructor was used, a constructor
    //doesn't need to be made.
    
    @Override
    public void onPossessedAdminCamera(PossessedAdminCameraEvent possessedAdminCameraEvent) {
      //Execute code here.
      //You can use the PossessedAdminCameraEvent passed into this method for details of the event.
      //Each event has its own set of fields that can be accessed to figure out details of the event.
    }

    @Override
    public void onUnpossessedAdminCamera(UnpossessedAdminCameraEvent unpossessedAdminCameraEvent) {
      //More code execution here.
    }
}
```

## Running Code Periodically
To have your plugin run code periodically, you can use executor services and schedulers provided by Java. For convenience,
Squad4J provides a global through pool through `GlobalThreadPool` that contains methods to get the global `ExecutorService` 
and `ScheduledExecutorService`. See [ExecutorService](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html) and 
[ScheduledExecutorService](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ScheduledExecutorService.html) for
documentation on how to use these.

To have code run periodically, you can schedule these in the constructor for the plugin class. 

**Note:** Remember all plugin classes must have a public no-argument constructor. When one isn't explicitly defined,
the default constructor is made, which fits these constraints. Since we will be writing a constructor, we must ensure that
these constraints are met.

```java
import concurrent.GlobalThreadPool;
import lombok.NoArgsConstructor;
import rcon.Rcon;

import java.util.concurrent.TimeUnit;

public class UselessBroadcasterPlugin {
    
  //You can also define your own no-argument constructor if you want
  //to execute some code on plugin instantiation.
  public UselessBroadcasterPlugin() {
    //Schedule code to run every 5 minutes, with an initial delay of 1 minute.
    GlobalThreadPool.getScheduler().scheduleAtFixedRate(() -> {
      Rcon.command("AdminBroadcast I broadcast this message every 5 minutes.");
    }, 1, 5, TimeUnit.MINUTES);
  }
}
```

The rest of this README is a **work in progress**.

TODO: List plugin listeners that be implemented.