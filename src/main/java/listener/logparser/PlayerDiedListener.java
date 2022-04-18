package listener.logparser;

import event.logparser.PlayerDiedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface PlayerDiedListener extends GloballyAttachbleListener {
    public void onPlayerDied(PlayerDiedEvent playerDiedEvent);
}
