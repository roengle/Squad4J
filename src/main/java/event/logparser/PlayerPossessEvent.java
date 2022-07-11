package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a player possesses an entity.
 *
 * @see listener.logparser.PlayerPossessListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class PlayerPossessEvent extends Event {
    private final String playerName;
    private final String entityPossessName;

    /**
     * Constructs a {@link PlayerPossessEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param playerName the name of the player
     * @param possessName the name of the entity possessed
     */
    public PlayerPossessEvent(Date date, EventType type, Integer chainID, String playerName, String possessName){
        super(date, type, chainID);
        this.playerName = playerName;
        this.entityPossessName = possessName;
        //TODO: Store chain ID somewhere
    }
}
