package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerUnPossessEvent extends Event {
    private String playerName;

    public PlayerUnPossessEvent(Date date, EventType type, Integer chainID, String playerName){
        super(date, type, chainID);
        this.playerName = playerName;
        //TODO: Remove stored chain ID from somewhere
    }

    public String getPlayerName() {
        return playerName;
    }
}
