package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerRevivedEvent extends Event {
    private String reviverName;
    private String victimName;

    public PlayerRevivedEvent(Date date, EventType type, Integer chainID, String reviverName, String victimName){
        super(date, type, chainID);
        this.reviverName = reviverName;
        this.victimName = victimName;
    }

    public String getReviverName() {
        return reviverName;
    }

    public String getVictimName() {
        return victimName;
    }
}
