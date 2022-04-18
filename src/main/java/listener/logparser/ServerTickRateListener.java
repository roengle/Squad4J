package listener.logparser;

import event.logparser.ServerTickRateEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface ServerTickRateListener extends GloballyAttachbleListener {
    public void onServerTickRate(ServerTickRateEvent serverTickRateEvent);
}
