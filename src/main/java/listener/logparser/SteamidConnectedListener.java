package listener.logparser;

import event.logparser.SteamidConnectedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface SteamidConnectedListener extends GloballyAttachableListener {
    public void onSteamidConnected(SteamidConnectedEvent steamidConnectedEvent);
}
