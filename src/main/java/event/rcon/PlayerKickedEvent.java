package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerKickedEvent extends Event {
    private String playerid;
    private String steamid;
    private String name;

    public PlayerKickedEvent(Date date, EventType type, String playerid, String steamid, String name){
        super(date, type);
        this.playerid = playerid;
        this.steamid = steamid;
        this.name = name;
    }

    public String getPlayerid() {
        return playerid;
    }

    public String getSteamid() {
        return steamid;
    }

    public String getName() {
        return name;
    }
}
