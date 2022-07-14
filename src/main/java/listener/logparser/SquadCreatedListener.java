package listener.logparser;

import event.logparser.SquadCreatedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface SquadCreatedListener extends GloballyAttachableListener {
    public void onSquadCreated(SquadCreatedEvent squadCreatedEvent);
}
