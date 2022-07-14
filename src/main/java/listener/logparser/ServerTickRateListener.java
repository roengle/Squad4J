package listener.logparser;

import event.logparser.ServerTickRateEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface ServerTickRateListener extends GloballyAttachableListener {
    public void onServerTickRate(ServerTickRateEvent serverTickRateEvent);
}
