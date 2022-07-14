package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where an admin broadcasts a message. An {@link AdminBroadcastEvent} contains the message
 * and the name of the broadcast sender.
 *
 * @see listener.logparser.AdminBroadcastListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class AdminBroadcastEvent extends Event {
    private final String message;
    private final String from;

    /**
     * Constructs an {@link AdminBroadcastEvent}.
     *
     * @param date the {@link Date} representing when the event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param message the message that was broadcasted
     * @param from the name of the admin who broadcasted the message
     */
    public AdminBroadcastEvent(Date date, EventType type, Integer chainID, String message, String from){
        super(date, type, chainID);
        this.message = message;
        this.from = from;
    }
}
