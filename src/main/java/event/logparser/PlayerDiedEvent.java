package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a player is killed AND gives up. This is different to a {@link PlayerWoundedEvent}, in which
 * occurs when a player is incapacitated, but has not given up.
 *
 * @see PlayerWoundedEvent
 * @see listener.logparser.PlayerDiedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class PlayerDiedEvent extends Event {
    private final String victimName;
    private final Double damage;
    private final String attackerPlayerController;
    private final String weapon;

    /**
     * Constructs a {@link PlayerDiedEvent}.
     *
     * @param date a {@link Date} representing when the event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID for this event
     * @param victimName the name of the player who died and gave up
     * @param damage the amount of damage used to kill the player
     * @param playerController the name of the player controller used to kill the victim
     * @param weapon the weapon used to kill the victim
     */
    public PlayerDiedEvent(Date date, EventType type, Integer chainID, String victimName,
                           Double damage, String playerController, String weapon){
        super(date, type, chainID);
        this.victimName = victimName;
        this.damage = damage;
        this.weapon = weapon;
        this.attackerPlayerController = playerController;
    }
}
