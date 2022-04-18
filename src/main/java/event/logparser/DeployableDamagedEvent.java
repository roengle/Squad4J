package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class DeployableDamagedEvent extends Event {
    private String deployable;
    private Integer damage;
    private String weapon;
    private String playerSuffix;
    private String damageType;
    private Integer healthRemaining;

    public DeployableDamagedEvent(Date date, EventType type, Integer chainID, String deployable, Integer damage, String weapon,
                                  String playerSuffix, String damageType, Integer healthRemaining){
        super(date, type, chainID);
        this.deployable = deployable;
        this.damage = damage;
        this.weapon = weapon;
        this.playerSuffix = playerSuffix;
        this.damageType = damageType;
        this.healthRemaining = healthRemaining;
    }

    public String getDeployable() {
        return deployable;
    }

    public Integer getDamage() {
        return damage;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getPlayerSuffix() {
        return playerSuffix;
    }

    public String getDamageType() {
        return damageType;
    }

    public Integer getHealthRemaining() {
        return healthRemaining;
    }
}
