package event.rcon;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a player is banned. This information is sent from the RCON console, not the squad server log
 * file.
 *
 * @see listener.rcon.PlayerBannedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
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
}
