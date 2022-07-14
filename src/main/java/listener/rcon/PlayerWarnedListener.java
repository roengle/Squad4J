package listener.rcon;

import event.rcon.PlayerWarnedEvent;
import listener.GloballyAttachableListener;

public interface PlayerWarnedListener extends GloballyAttachableListener {
    public void onPlayerWarned(PlayerWarnedEvent playerWarnedEvent);
}
