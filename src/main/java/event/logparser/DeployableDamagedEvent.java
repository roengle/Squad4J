package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a deployable is damaged.
 *
 * @see listener.logparser.DeployableDamagedListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class DeployableDamagedEvent extends Event {
    private final String deployable;
    private final Double damage;
    private final String weapon;
    private final String playerSuffix;
    private final String damageType;
    private final Double healthRemaining;

    /**
     * Constructs a {@link DeployableDamagedEvent}.
     *
     * @param date a {@link Date} representing when the event occurred.
     * @param type the corresponding {@link EventType} for this event.
     * @param chainID the chain ID of this event
     * @param deployable the name of the deployable that was damaged
     * @param damage the amount of damage done to this deployable
     * @param weapon the weapon used to damage this deployable
     * @param playerSuffix the name of the player damaging the deployable
     * @param damageType the damage type done to the deployable
     * @param healthRemaining the amount of health remaining on the deployable
     */
    public DeployableDamagedEvent(Date date, EventType type, Integer chainID, String deployable, Double damage, String weapon,
                                  String playerSuffix, String damageType, Double healthRemaining){
        super(date, type, chainID);
        this.deployable = deployable;
        this.damage = damage;
        this.weapon = weapon;
        this.playerSuffix = playerSuffix;
        this.damageType = damageType;
        this.healthRemaining = healthRemaining;
    }
}
