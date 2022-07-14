package listener.logparser;

import event.logparser.PlayerWoundedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface PlayerWoundedListener extends GloballyAttachableListener {
    public void onPlayerWoundedEvent(PlayerWoundedEvent playerWoundedEvent);
}
