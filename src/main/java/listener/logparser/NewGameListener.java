package listener.logparser;

import event.logparser.NewGameEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface NewGameListener extends GloballyAttachableListener {
    public void onNewGame(NewGameEvent newGameEvent);
}
