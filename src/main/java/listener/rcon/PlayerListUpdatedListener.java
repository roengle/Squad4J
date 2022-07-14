package listener.rcon;

import event.rcon.PlayerListUpdatedEvent;
import listener.GloballyAttachableListener;

public interface PlayerListUpdatedListener extends GloballyAttachableListener {
    public void onPlayerListUpdated(PlayerListUpdatedEvent playerListUpdatedEvent);
}
