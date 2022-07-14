package listener.rcon;

import event.rcon.PlayerKickedEvent;
import listener.GloballyAttachableListener;

public interface PlayerKickedListener extends GloballyAttachableListener {
    public void onPlayerKicked(PlayerKickedEvent playerKickedEvent);
}
