package listener.rcon;

import event.rcon.PlayerWarnedEvent;
import listener.GloballyAttachbleListener;

public interface PlayerWarnedListener extends GloballyAttachbleListener {
    public void onPlayerWarned(PlayerWarnedEvent playerWarnedEvent);
}
