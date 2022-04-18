package event.rcon;

import entity.Player;
import event.Event;
import event.EventType;

import java.util.Date;
import java.util.List;

public class PlayerListUpdatedEvent extends Event {
    private final List<Player> playerList;

    public PlayerListUpdatedEvent(Date date, EventType type, List<Player> playerList) {
        super(date, type);
        this.playerList = playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}
