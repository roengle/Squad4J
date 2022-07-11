package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a player unposssesses an entity.
 *
 * Note: The chain ID of a {@link PlayerUnPossessEvent} will match its corresponding {@link PlayerPossessEvent}.
 *
 * @see PlayerPossessEvent
 * @see listener.logparser.PlayerUnPossessListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class PlayerUnPossessEvent extends Event {
    private final String playerName;

    /**
     * Constructs a {@link PlayerUnPossessEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param playerName the name of the player who unpossessed the entity
     */
    public PlayerUnPossessEvent(Date date, EventType type, Integer chainID, String playerName){
        super(date, type, chainID);
        this.playerName = playerName;
        //TODO: Remove stored chain ID from somewhere
    }
}
