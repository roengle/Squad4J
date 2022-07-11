package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a player is wounded. This occurs when a player is incapacitated, but is able to still
 * be revived. This is different from a {@link PlayerDiedEvent}, in which a player has given up after being wounded.
 *
 * @see PlayerDiedEvent
 * @see listener.logparser.PlayerWoundedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class PlayerWoundedEvent extends Event {
    private final String victimName;
    private final Double damage;
    private final String attackerController;
    private final String weapon;

    /**
     * Constructs a {@link PlayerWoundedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param victimName the name of the person wounded
     * @param damage the damage dealt to wound the player
     * @param attackerController the name of the attacker controller
     * @param weapon the weapon used by the attacked
     */
    public PlayerWoundedEvent(Date date, EventType type, Integer chainID, String victimName, Double damage,
                              String attackerController, String weapon){
        super(date, type, chainID);
        this.victimName = victimName;
        this.attackerController = attackerController;
        this.damage = damage;
        this.weapon = weapon;
        //TODO: Figure out attacker
    }
}
