package listener.logparser;

import event.logparser.PlayerConnectedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface PlayerConnectedListener extends GloballyAttachableListener {
    public void onPlayerConnected(PlayerConnectedEvent playerConnectedEvent);
}
