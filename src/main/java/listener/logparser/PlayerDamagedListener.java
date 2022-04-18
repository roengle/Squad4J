package listener.logparser;

import event.logparser.PlayerDamagedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface PlayerDamagedListener extends GloballyAttachbleListener {
    public void onPlayerDamaged(PlayerDamagedEvent playerDamagedEvent);
}
