package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a player is damaged.
 *
 * @see listener.logparser.PlayerDamagedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class PlayerDamagedEvent extends Event {
    private final String victimName;
    private final Double damage;
    private final String attackerName;
    private final String weapon;
    private final String weaponEntityId;

    /**
     * Constructrs a {@link PlayerDamagedEvent}.
     *
     * @param date a {@link Date} representing when the event occurred
     * @param type the corrsponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param victimName the name of the player damaged
     * @param damage the amount of damage that {@code victimName} received
     * @param attackerName the name of the attack who dealt damage
     * @param weapon the weapon used by {@code attackerName}
     * @param weaponEntityId the entity id of the weapon used by {@code attackerName}
     */
    public PlayerDamagedEvent(Date date, EventType type, Integer chainID, String victimName,
                              Double damage, String attackerName, String weapon, String weaponEntityId){
        super(date, type, chainID);
        this.victimName = victimName;
        this.damage = damage;
        this.attackerName = attackerName;
        this.weapon = weapon;
        this.weaponEntityId = weaponEntityId;
    }
}
