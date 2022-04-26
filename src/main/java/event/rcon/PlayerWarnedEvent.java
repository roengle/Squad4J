package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a player is warned.
 *
 * @see listener.rcon.PlayerWarnedListener
 */
public class PlayerWarnedEvent extends Event {
    private final String playerName;
    private final String message;

    /**
     * Constructs a {@link PlayerWarnedEvent}
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param playerName the name of the player that was warned
     * @param message the message content of the warning
     */
    public PlayerWarnedEvent(Date date, EventType type, String playerName, String message){
        super(date, type);
        this.playerName = playerName;
        this.message = message;
    }

    /**
     * Gets the name of the player warned.
     *
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the message content of the warning.
     *
     * @return the message content of the warning
     */
    public String getMessage() {
        return message;
    }
}
