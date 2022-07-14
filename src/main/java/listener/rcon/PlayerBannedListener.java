package listener.rcon;

import event.rcon.PlayerBannedEvent;
import listener.GloballyAttachableListener;

public interface PlayerBannedListener extends GloballyAttachableListener {
    public void onPlayerBanned(PlayerBannedEvent playerBannedEvent);
}
