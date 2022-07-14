package listener.logparser;

import event.logparser.PlayerDiedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface PlayerDiedListener extends GloballyAttachableListener {
    public void onPlayerDied(PlayerDiedEvent playerDiedEvent);
}
