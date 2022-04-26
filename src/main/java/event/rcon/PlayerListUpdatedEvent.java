package event.rcon;

import entity.Player;
import event.Event;
import event.EventType;

import java.util.Date;
import java.util.List;

/**
 * @author Robert Engle
 *
 * Describes an event where a player list is updated from {@link server.RconUpdater}.
 *
 * @see server.RconUpdater
 * @see listener.rcon.PlayerListUpdatedListener
 */
public class PlayerListUpdatedEvent extends Event {
    private final List<Player> playerList;

    /**
     * Constructs a {@link PlayerListUpdatedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param playerList a {@link List<Player>} containing players in the server
     */
    public PlayerListUpdatedEvent(Date date, EventType type, List<Player> playerList) {
        super(date, type);
        this.playerList = playerList;
    }

    /**
     * Gets a {@link List<Player>} representing the players in the server
     *
     * @return a {@link List<Player>} of players in the server
     */
    public List<Player> getPlayerList() {
        return playerList;
    }
}
