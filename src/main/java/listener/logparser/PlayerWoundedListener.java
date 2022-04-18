package listener.logparser;

import event.logparser.PlayerWoundedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface PlayerWoundedListener extends GloballyAttachbleListener {
    public void onPlayerWoundedEvent(PlayerWoundedEvent playerWoundedEvent);
}
