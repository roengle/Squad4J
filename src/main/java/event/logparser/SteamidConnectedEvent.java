package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Described an event where a player connects to a server an the steam64id is provided. This event is fired
 * BEFORE the corresponding {@link PlayerConnectedEvent}, in which only the player name is provided.
 *
 * @see PlayerConnectedEvent
 * @see listener.logparser.SteamidConnectedListener
 */
public class SteamidConnectedEvent extends Event {
    private final String steamid;
    private final String name;

    /**
     * Constructs a {@link SteamidConnectedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param steamid the steam64id of the player that connected
     * @param name the name of the player that connected
     */
    public SteamidConnectedEvent(Date date, EventType type, Integer chainID, String steamid, String name){
        super(date, type, chainID);
        this.steamid = steamid;
        this.name = name;
        //TODO: Cache steamid with playername for connected, remove from cache when disconnected
    }

    /**
     * Gets the steam64id of the player connected.
     *
     * @return the steam64id of the player
     */
    public String getSteamid() {
        return steamid;
    }

    /**
     * Gets the name of the player connected.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }
}
