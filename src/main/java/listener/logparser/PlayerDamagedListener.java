package listener.logparser;

import event.logparser.PlayerDamagedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface PlayerDamagedListener extends GloballyAttachableListener {
    public void onPlayerDamaged(PlayerDamagedEvent playerDamagedEvent);
}
