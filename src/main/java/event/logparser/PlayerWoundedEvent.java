package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerWoundedEvent extends Event {
    private String victimName;
    private Integer damage;
    private String attackerController;
    private String weapon;

    public PlayerWoundedEvent(Date date, EventType type, Integer chainID, String victimName, Integer damage,
                              String attackerController, String weapon){
        super(date, type, chainID);
        this.victimName = victimName;
        this.attackerController = attackerController;
        this.damage = damage;
        this.weapon = weapon;
        //TODO: Figure out attacker
    }

    public String getVictimName() {
        return victimName;
    }

    public String getAttackerController() {
        return attackerController;
    }

    public Integer getDamage() {
        return damage;
    }

    public String getWeapon() {
        return weapon;
    }
}
