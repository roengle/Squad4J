package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a player is revived.
 *
 * @see listener.logparser.PlayerRevivedListener
 */
public class PlayerRevivedEvent extends Event {
    private final String reviverName;
    private final String victimName;

    /**
     * Constructs a {@link PlayerRevivedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param reviverName the name of the reviver
     * @param victimName the name of the player who was revived
     */
    public PlayerRevivedEvent(Date date, EventType type, Integer chainID, String reviverName, String victimName){
        super(date, type, chainID);
        this.reviverName = reviverName;
        this.victimName = victimName;
    }

    /**
     * Gets the name of the reviver.
     *
     * @return the name of the reviver
     */
    public String getReviverName() {
        return reviverName;
    }

    /**
     * Gets the name of the player who was revived.
     *
     * @return the name of the victim who was revived
     */
    public String getVictimName() {
        return victimName;
    }
}
