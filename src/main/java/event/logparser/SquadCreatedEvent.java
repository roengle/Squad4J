package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class SquadCreatedEvent extends Event {
    private String playerName;
    private String steamid;
    private Integer squadid;
    private String squadName;
    private String teamName;

    public SquadCreatedEvent(Date date, EventType type, Integer chainID, String playerName, String steamid,
                             Integer squadid, String squadName, String teamName){
        super(date, type, chainID);
        this.playerName = playerName;
        this.steamid = steamid;
        this.squadid = squadid;
        this.squadName = squadName;
        this.teamName = teamName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getSteamid() {
        return steamid;
    }

    public String getSquadName() {
        return squadName;
    }

    public String getTeamName() {
        return teamName;
    }
}
