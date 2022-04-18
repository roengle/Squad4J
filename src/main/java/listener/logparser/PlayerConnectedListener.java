package listener.logparser;

import event.logparser.PlayerConnectedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface PlayerConnectedListener extends GloballyAttachbleListener {
    public void onPlayerConnected(PlayerConnectedEvent playerConnectedEvent);
}
