package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class SteamidConnectedEvent extends Event {
    private String steamid;
    private String name;

    public SteamidConnectedEvent(Date date, EventType type, Integer chainID, String steamid, String name){
        super(date, type, chainID);
        this.steamid = steamid;
        this.name = name;
        //TODO: Cache steamid with playername for connected, remove from cache when disconnected
    }

    public String getSteamid() {
        return steamid;
    }

    public String getName() {
        return name;
    }
}
