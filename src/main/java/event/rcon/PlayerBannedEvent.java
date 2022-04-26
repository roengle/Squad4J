package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a player is banned. This information is sent from the RCON console, not the squad server log
 * file.
 *
 * @see listener.rcon.PlayerBannedListener
 */
public class PlayerBannedEvent extends Event {
    private final String playerid;
    private final String steamid;
    private final String playerName;
    private final String bannedUntil;

    /**
     * Constructs a {@link PlayerBannedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param playerid the ID of the player banned. This is NOT the steam64id of the player.
     * @param steamid the steam64id of the player banned.
     * @param playerName the name of the player banned
     * @param bannedUntil unix epoch time of when the player is unbanned
     */
    public PlayerBannedEvent(Date date, EventType type, String playerid, String steamid, String playerName, String bannedUntil){
        super(date, type);
        this.playerid = playerid;
        this.steamid = steamid;
        this.playerName = playerName;
        this.bannedUntil = bannedUntil;
    }

    /**
     * Gets the player ID of the player banned.
     *
     * Note: This is NOT the steam64id.
     *
     * @return the player id of the player banned
     */
    public String getPlayerid() {
        return playerid;
    }

    /**
     * Gets the steam64id of the player banned.
     *
     * @return the steam64id of the player banned
     */
    public String getSteamid() {
        return steamid;
    }

    /**
     * Gets the name of the player banned.
     *
     * @return the name of the player banned
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the unix epoch timestamp of when the player is no longer banned.
     *
     * @return the unix epoch timestamp of when the player is no longer banned
     */
    public String getBannedUntil() {
        return bannedUntil;
    }
}
