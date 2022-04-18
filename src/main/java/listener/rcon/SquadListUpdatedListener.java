package listener.rcon;

import event.rcon.SquadListUpdatedEvent;
import listener.GloballyAttachbleListener;

public interface SquadListUpdatedListener extends GloballyAttachbleListener {
    public void onSquadListUpdated(SquadListUpdatedEvent squadListUpdatedEvent);
}
