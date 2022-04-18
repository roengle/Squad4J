package listener.logparser;

import event.logparser.SquadCreatedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface SquadCreatedListener extends GloballyAttachbleListener {
    public void onSquadCreated(SquadCreatedEvent squadCreatedEvent);
}
