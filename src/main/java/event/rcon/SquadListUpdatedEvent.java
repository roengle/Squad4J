package event.rcon;

import entity.Squad;
import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Describes an event where a squad list is updated from {@link server.RconUpdater}.
 *
 * @see server.RconUpdater
 * @see listener.rcon.SquadListUpdatedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class SquadListUpdatedEvent extends Event {
    private final List<Squad> squadList;

    /**
     * Constructs a {@link SquadListUpdatedEvent}
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param squadList a {@link List} representing the squads
     */
    public SquadListUpdatedEvent(Date date, EventType type, List<Squad> squadList) {
        super(date, type);
        this.squadList = squadList;
    }

    /**
     * Gets an unmodifiable {@link List} representing the squads
     *
     * @return a {@link List} representing the squads
     */
    public List<Squad> getSquadList() {
        return Collections.unmodifiableList(squadList);
    }
}
