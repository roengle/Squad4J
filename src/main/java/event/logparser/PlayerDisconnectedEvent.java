package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerDisconnectedEvent extends Event {
    private String steamid;

    public PlayerDisconnectedEvent(Date date, EventType type, Integer chainID, String steamid){
        super(date, type, chainID);
        this.steamid = steamid;
    }

    public String getSteamid() {
        return steamid;
    }
}
