package event.rcon;

import entity.Squad;
import event.Event;
import event.EventType;

import java.util.Date;
import java.util.List;

public class SquadListUpdatedEvent extends Event {
    private final List<Squad> squadList;

    public SquadListUpdatedEvent(Date date, EventType type, List<Squad> squadList) {
        super(date, type);
        this.squadList = squadList;
    }

    public List<Squad> getSquadList() {
        return squadList;
    }
}
