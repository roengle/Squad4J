package listener.rcon;

import event.rcon.PlayerKickedEvent;
import listener.GloballyAttachbleListener;

public interface PlayerKickedListener extends GloballyAttachbleListener {
    public void onPlayerKicked(PlayerKickedEvent playerKickedEvent);
}
