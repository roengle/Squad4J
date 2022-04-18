package listener.logparser;

import event.logparser.PlayerUnPossessEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface PlayerUnPossessListener extends GloballyAttachbleListener {
    public void onPlayerUnPossess(PlayerUnPossessEvent playerUnPossessEvent);
}
