package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerPossessEvent extends Event {
    private String playerName;
    private String entityPossessName;

    public PlayerPossessEvent(Date date, EventType type, Integer chainID, String playerName, String possessName){
        super(date, type, chainID);
        this.playerName = playerName;
        this.entityPossessName = possessName;
        //TODO: Store chain ID somewhere
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getEntityPossessName() {
        return entityPossessName;
    }
}
