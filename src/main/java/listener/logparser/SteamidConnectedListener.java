package listener.logparser;

import event.logparser.SteamidConnectedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface SteamidConnectedListener extends GloballyAttachbleListener {
    public void onSteamidConnected(SteamidConnectedEvent steamidConnectedEvent);
}
