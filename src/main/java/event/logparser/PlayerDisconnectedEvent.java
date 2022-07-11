package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a player disconnects from the server.
 *
 * @see listener.logparser.PlayerDisconnectedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class PlayerDisconnectedEvent extends Event {
    private final String steamid;

    /**
     * Constructs a {@link PlayerDisconnectedEvent}.
     *
     * @param date a {@link Date} representing when the event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param steamid the Steam64ID of the player who disconnected
     */
    public PlayerDisconnectedEvent(Date date, EventType type, Integer chainID, String steamid){
        super(date, type, chainID);
        this.steamid = steamid;
    }
}
