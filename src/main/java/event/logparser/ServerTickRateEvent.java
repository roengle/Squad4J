package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where the tick rate of the server is updated.
 *
 * @see listener.logparser.ServerTickRateListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class ServerTickRateEvent extends Event {
    private final Double tickRate;

    /**
     * Constructs a {@link ServerTickRateEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param tickRate the reported tick rate for this event
     */
    public ServerTickRateEvent(Date date, EventType type, Integer chainID, Double tickRate){
        super(date, type, chainID);
        this.tickRate = tickRate;
    }
}
