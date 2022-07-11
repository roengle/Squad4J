package event.rcon;

import entity.Player;
import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Describes an event where a player list is updated from {@link server.RconUpdater}.
 *
 * @see server.RconUpdater
 * @see listener.rcon.PlayerListUpdatedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class PlayerListUpdatedEvent extends Event {
    private final List<Player> playerList;

    /**
     * Constructs a {@link PlayerListUpdatedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param playerList a {@link List} containing players in the server
     */
    public PlayerListUpdatedEvent(Date date, EventType type, List<Player> playerList) {
        super(date, type);
        this.playerList = playerList;
    }

    /**
     * Returns an unmodifiable {@link List} of the players.
     *
     * @return an unmodifiable list of the players
     */
    public List<Player> getPlayerList(){
        return Collections.unmodifiableList(playerList);
    }
}
