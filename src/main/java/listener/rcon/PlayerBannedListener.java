package listener.rcon;

import event.rcon.PlayerBannedEvent;
import listener.GloballyAttachbleListener;

public interface PlayerBannedListener extends GloballyAttachbleListener {
    public void onPlayerBanned(PlayerBannedEvent playerBannedEvent);
}
