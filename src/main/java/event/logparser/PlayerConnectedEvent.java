package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a player connects to the server. Is fired AFTER the corresponding {@link SteamidConnectedEvent}
 * for the player, meaning the player's steam ID should be in memory.
 *
 * @see listener.logparser.PlayerConnectedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class PlayerConnectedEvent extends Event {
    private final String playerName;

    /**
     * Constructs a {@link PlayerConnectedEvent}.
     *
     * @param date a {@link Date} representing when the event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param playerName the name of the player that connected
     */
    public PlayerConnectedEvent(Date date, EventType type, Integer chainID, String playerName){
        super(date, type, chainID);
        this.playerName = playerName;
    }
}
