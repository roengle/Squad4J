package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a player is damaged.
 *
 * @see listener.logparser.PlayerDamagedListener
 */
public class PlayerDamagedEvent extends Event {
    private final String victimName;
    private final Integer damage;
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
                              Integer damage, String attackerName, String weapon, String weaponEntityId){
        super(date, type, chainID);
        this.victimName = victimName;
        this.damage = damage;
        this.attackerName = attackerName;
        this.weapon = weapon;
        this.weaponEntityId = weaponEntityId;
    }

    /**
     * Gets the name of the person who was damaged (referred to as the "victim").
     *
     * @return the name of the victim that was damaged
     */
    public String getVictimName() {
        return victimName;
    }

    /**
     * Gets the amount of damage that was dealt to the victim.
     *
     * @return the amount of damage dealt
     */
    public Integer getDamage() {
        return damage;
    }

    /**
     * Gets the name of the attacker who dealt the damage.
     *
     * @return the name of the attacker
     */
    public String getAttackerName() {
        return attackerName;
    }

    /**
     * Gets the name of the weapon used to damage the victim.
     *
     * @return the name of the weapon that damaged the victim
     */
    public String getWeapon() {
        return weapon;
    }

    /**
     * Gets the entity ID of the weapon that damaged the victim.
     *
     * @return the entity ID of the weapon that damaged the victim
     */
    public String getWeaponEntityId() {
        return weaponEntityId;
    }
}
