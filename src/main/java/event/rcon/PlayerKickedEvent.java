package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a player is kicked from the server.
 *
 * @see listener.rcon.PlayerKickedListener
 */
public class PlayerKickedEvent extends Event {
    private final String playerid;
    private final String steamid;
    private final String name;

    /**
     * Constructs a {@link PlayerKickedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param playerid the id the player kicked. This is NOT the steam64id
     * @param steamid the steam64id of the player kicked
     * @param name the name of the player kicked
     */
    public PlayerKickedEvent(Date date, EventType type, String playerid, String steamid, String name){
        super(date, type);
        this.playerid = playerid;
        this.steamid = steamid;
        this.name = name;
    }

    /**
     * Gets the playerid of the player kicked.
     *
     * @return the playerid of the player kicked
     */
    public String getPlayerid() {
        return playerid;
    }

    /**
     * Gets the steam64id of the player kicked.
     *
     * @return the steam64id of the player kicked
     */
    public String getSteamid() {
        return steamid;
    }

    /**
     * Gets the name of the player kicked.
     *
     * @return the name of the player kicked
     */
    public String getName() {
        return name;
    }
}
