package listener.rcon;

import event.rcon.PlayerListUpdatedEvent;
import listener.GloballyAttachbleListener;

public interface PlayerListUpdatedListener extends GloballyAttachbleListener {
    public void onPlayerListUpdated(PlayerListUpdatedEvent playerListUpdatedEvent);
}
