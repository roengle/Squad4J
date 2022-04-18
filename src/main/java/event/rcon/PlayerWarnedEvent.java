package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerWarnedEvent extends Event {
    private String playerName;
    private String message;

    public PlayerWarnedEvent(Date date, EventType type, String playerName, String message){
        super(date, type);
        this.playerName = playerName;
        this.message = message;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getMessage() {
        return message;
    }
}
