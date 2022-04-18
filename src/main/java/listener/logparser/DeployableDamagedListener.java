package listener.logparser;

import event.logparser.DeployableDamagedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface DeployableDamagedListener extends GloballyAttachbleListener {
    public void onDeployableDamaged(DeployableDamagedEvent deployableDamagedEvent);
}
