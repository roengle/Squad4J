package listener.logparser;

import event.logparser.DeployableDamagedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface DeployableDamagedListener extends GloballyAttachableListener {
    public void onDeployableDamaged(DeployableDamagedEvent deployableDamagedEvent);
}
