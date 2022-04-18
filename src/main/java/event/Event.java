package event;

import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * @author Robert Engle
 *
 * An abstract class defining an event that can occur.
 *
 * Events that occur from the log parser have a date/time, type, and chain ID. Events that occur from the RCON console
 * only have a date/time and type.
 */
public abstract class Event {
    private final Date time;
    private final EventType type;
    private final Integer chainID;

    /**
     * Constructor for events that occur from the log parser, where a chain ID is present.
     *
     * @param date the date and time the event occured
     * @param type the type of event, outlined by {@link EventType}
     * @param chainID the chain ID of the event
     */
    protected Event(Date date, EventType type, Integer chainID){
        this.time = date;
        this.type = type;
        this.chainID = chainID;
    }

    /**
     * Constructor for events that occur from the RCON console, where a chain ID is not present.
     *
     * @param date the date and time the event occured
     * @param type the type of event, outlined by {@link EventType}
     */
    protected Event(Date date, EventType type){
        this.time = date;
        this.type = type;
        this.chainID = null;
    }

    protected Date getTime(){
        return this.time;
    }

    protected EventType getType() {
        return this.type;
    }

    protected Optional<Integer> getChainID() {
        return Optional.ofNullable(this.chainID);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]")
                .add("time=" + time)
                .add("type=" + type)
                .add("chainID=" + chainID)
                .toString();
    }
}
