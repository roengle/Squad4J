package listener.rcon;

import event.rcon.SquadListUpdatedEvent;
import listener.GloballyAttachableListener;

public interface SquadListUpdatedListener extends GloballyAttachableListener {
    public void onSquadListUpdated(SquadListUpdatedEvent squadListUpdatedEvent);
}
