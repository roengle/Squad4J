package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

public class PossessedAdminCameraEvent extends Event {
    private String steamid;
    private String name;

    public PossessedAdminCameraEvent(Date date, EventType type, String steamid, String name){
        super(date, type);
        this.steamid = steamid;
        this.name = name;
    }

    public String getSteamid() {
        return steamid;
    }

    public String getName() {
        return name;
    }
}
