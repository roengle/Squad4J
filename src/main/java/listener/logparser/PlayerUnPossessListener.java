package listener.logparser;

import event.logparser.PlayerUnPossessEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface PlayerUnPossessListener extends GloballyAttachableListener {
    public void onPlayerUnPossess(PlayerUnPossessEvent playerUnPossessEvent);
}
