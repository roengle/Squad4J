package listener.logparser;

import event.logparser.PlayerDisconnectedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface PlayerDisconnectedListener extends GloballyAttachbleListener {
    public void onPlayerDisconnected(PlayerDisconnectedEvent playerDisconnectedEvent);
}
