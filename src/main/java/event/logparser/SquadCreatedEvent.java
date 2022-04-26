package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a squad is created by a player.
 *
 * @see listener.logparser.SquadCreatedListener
 */
public class SquadCreatedEvent extends Event {
    private final String playerName;
    private final String steamid;
    private final Integer squadid;
    private final String squadName;
    private final String teamName;

    /**
     * Constructs a {@link SquadCreatedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param playerName the name of the player who created the squad
     * @param steamid the steam64id of the player who created the squad
     * @param squadid the id of the squad created
     * @param squadName the name of the squad
     * @param teamName the name of the team the squad was created on
     */
    public SquadCreatedEvent(Date date, EventType type, Integer chainID, String playerName, String steamid,
                             Integer squadid, String squadName, String teamName){
        super(date, type, chainID);
        this.playerName = playerName;
        this.steamid = steamid;
        this.squadid = squadid;
        this.squadName = squadName;
        this.teamName = teamName;
    }

    /**
     * Gets the name of the player that created the squad.
     *
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the steam64id of the player that created the squad.
     *
     * @return the steam64id of the player
     */
    public String getSteamid() {
        return steamid;
    }

    /**
     * Gets the name of the squad created.
     *
     * @return the name of the squad
     */
    public String getSquadName() {
        return squadName;
    }

    /**
     * Gets the name of the team the squad was created on.
     *
     * @return the name of the team
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Gets the id of the squad created.
     *
     * @return the id of the squad
     */
    public Integer getSquadid() {
        return squadid;
    }
}
