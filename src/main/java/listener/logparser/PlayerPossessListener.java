package listener.logparser;

import event.logparser.PlayerPossessEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface PlayerPossessListener extends GloballyAttachableListener {
    public void onPlayerPossess(PlayerPossessEvent playerPossessEvent);
}
