package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a player is wounded. This occurs when a player is incapacitated, but is able to still
 * be revived. This is different from a {@link PlayerDiedEvent}, in which a player has given up after being wounded.
 *
 * @see PlayerDiedEvent
 * @see listener.logparser.PlayerWoundedListener
 */
public class PlayerWoundedEvent extends Event {
    private final String victimName;
    private final Integer damage;
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
    public PlayerWoundedEvent(Date date, EventType type, Integer chainID, String victimName, Integer damage,
                              String attackerController, String weapon){
        super(date, type, chainID);
        this.victimName = victimName;
        this.attackerController = attackerController;
        this.damage = damage;
        this.weapon = weapon;
        //TODO: Figure out attacker
    }

    /**
     * Gets the name of the person who was wounded--the victim.
     *
     * @return the name of the victim
     */
    public String getVictimName() {
        return victimName;
    }

    /**
     * Gets the name of the attacker controller used to wound the victim.
     *
     * @return the name of the attacker controller
     */
    public String getAttackerController() {
        return attackerController;
    }

    /**
     * Gets the damage dealt to wound the player
     *
     * @return the damage dealt
     */
    public Integer getDamage() {
        return damage;
    }

    /**
     * Gets the name of the weapon used to wound the player.
     *
     * @return the name of the weapon used
     */
    public String getWeapon() {
        return weapon;
    }
}
