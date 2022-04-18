package listener.logparser;

import event.logparser.PlayerPossessEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface PlayerPossessListener extends GloballyAttachbleListener {
    public void onPlayerPossess(PlayerPossessEvent playerPossessEvent);
}
