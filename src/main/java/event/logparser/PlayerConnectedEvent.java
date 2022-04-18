package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerConnectedEvent extends Event {
    String playerName;
    String steamid;

    public PlayerConnectedEvent(Date date, EventType type, Integer chainID, String playerName){
        super(date, type, chainID);
        this.playerName = playerName;
        //TODO: Implement getting steamid from steamid-connected event that occurs before this event

    }

    public String getPlayerName() {
        return playerName;
    }

    public String getSteamid() {
        return steamid;
    }
}
