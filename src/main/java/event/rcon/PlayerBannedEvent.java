package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerBannedEvent extends Event {
    private String playerid;
    private String steamid;
    private String playerName;
    private String bannedUntil;

    public PlayerBannedEvent(Date date, EventType type, String playerid, String steamid, String playerName, String bannedUntil){
        super(date, type);
        this.playerid = playerid;
        this.steamid = steamid;
        this.playerName = playerName;
        this.bannedUntil = bannedUntil;
    }

    public String getPlayerid() {
        return playerid;
    }

    public String getSteamid() {
        return steamid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getBannedUntil() {
        return bannedUntil;
    }
}
