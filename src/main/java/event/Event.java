package event;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * @author Robert Engle
 *
 *
 * An abstract class defining an event that can occur.
 *
 * Events that occur from the log parser have a date/time, type, and chain ID. Events that occur from anywhere else
 * do not have a chain ID.
 */
public abstract class Event {
    private final Date time;
    private final EventType type;
    private final Integer chainID;

    /**
     * Constructor for events that occur from the log parser, where a chain ID is present.
     *
     * @param date the date and time the event occurred
     * @param type the type of event, outlined by {@link EventType}
     * @param chainID the chain ID of the event
     */
    public Event(Date date, EventType type, Integer chainID){
        this.time = date;
        this.type = type;
        this.chainID = chainID;
    }

    /**
     * Constructor for events that occur from the RCON console, where a chain ID is not present.
     *
     * @param date the date and time the event occurred
     * @param type the type of event, outlined by {@link EventType}
     */
    protected Event(Date date, EventType type){
        this.time = date;
        this.type = type;
        this.chainID = null;
    }

    /**
     * Gets the time the event occured.
     *
     * @return a {@link Date} representing when the event occurred
     */
    public Date getTime(){
        return this.time;
    }

    /**
     * Gets the type of event.
     *
     * @return an {@link EventType} of this event's type
     */
    public EventType getType() {
        return this.type;
    }

    /**
     * Gets an optional which represents the state of the chain ID. If not null, the optional will contain the chain ID
     * value. If null, then the opitonal will be empty.
     *
     * @return an {@link Optional} for the chain ID for this event
     */
    public Optional<Integer> getChainID() {
        return Optional.ofNullable(this.chainID);
    }

    /**
     * Override of toString method.
     *
     * @return a string representation of this event
     */
    @Override
    public String toString() {
        return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]")
                .add("time=" + time)
                .add("type=" + type)
                .add("chainID=" + chainID)
                .toString();
    }

    /**
     * Compares this Event against another for equality. Two events are equal if they have the same exact time,
     * type, and chain ID.
     *
     * @param o the object to compare this event to
     * @return true if the two are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getTime().equals(event.getTime()) && getType() == event.getType() && Objects.equals(getChainID(), event.getChainID());
    }

    /**
     * Generates a hashcode for this event
     *
     * @return a hashcode for this event
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getType(), getChainID());
    }
}
