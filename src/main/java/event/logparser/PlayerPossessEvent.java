package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a player posesses an entity.
 *
 * @see listener.logparser.PlayerPossessListener
 */
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

    /**
     * Gets the name of the player who possessed the entity.
     *
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the name of the entity that was possessed.
     *
     * @return the name of the entity
     */
    public String getEntityPossessName() {
        return entityPossessName;
    }
}
