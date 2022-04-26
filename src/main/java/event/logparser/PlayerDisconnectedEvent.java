package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a player disconnects from the server.
 *
 * @see listener.logparser.PlayerDisconnectedListener
 */
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

    /**
     * Gets the steam64id of the player who disconnected.
     *
     * @return the steam64id of the player
     */
    public String getSteamid() {
        return steamid;
    }
}
