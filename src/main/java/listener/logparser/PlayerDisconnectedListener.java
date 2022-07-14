package listener.logparser;

import event.logparser.PlayerDisconnectedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface PlayerDisconnectedListener extends GloballyAttachableListener {
    public void onPlayerDisconnected(PlayerDisconnectedEvent playerDisconnectedEvent);
}
