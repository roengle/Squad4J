package listener.logparser;

import event.logparser.NewGameEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface NewGameListener extends GloballyAttachbleListener {
    public void onNewGame(NewGameEvent newGameEvent);
}
