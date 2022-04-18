package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerDamagedEvent extends Event {
    private String victimName;
    private Integer damage;
    private String attackerName;
    private String weapon;
    private String weaponEntityId;

    public PlayerDamagedEvent(Date date, EventType type, Integer chainID, String victimName,
                              Integer damage, String attackerName, String weapon, String weaponEntityId){
        super(date, type, chainID);
        this.victimName = victimName;
        this.damage = damage;
        this.attackerName = attackerName;
        this.weapon = weapon;
    }

    public String getVictimName() {
        return victimName;
    }

    public Integer getDamage() {
        return damage;
    }

    public String getAttackerName() {
        return attackerName;
    }

    public String getWeapon() {
        return weapon;
    }
}
