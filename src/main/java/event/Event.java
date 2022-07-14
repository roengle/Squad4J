package event;

import lombok.*;

import javax.annotation.Nullable;
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
@Getter
@ToString
@EqualsAndHashCode
public abstract class Event {
    private final Date time;
    private final EventType type;
    @Getter(AccessLevel.NONE)
    @Nullable
    private final Integer chainID;

    /**
     * Constructor for events that occur from the log parser, where a chain ID is present.
     *
     * @param date the date and time the event occurred
     * @param type the type of event, outlined by {@link EventType}
     * @param chainID the chain ID of the event
     */
    protected Event(Date date, EventType type, @Nullable Integer chainID){
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
     * Gets an optional which represents the state of the chain ID. If not null, the optional will contain the chain ID
     * value. If null, then the opitonal will be empty.
     *
     * @return an {@link Optional} for the chain ID for this event
     */
    public Optional<Integer> getChainID() {
        return Optional.ofNullable(this.chainID);
    }
}
