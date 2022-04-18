package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class PlayerDiedEvent extends Event {
    private String victimName;
    private Integer damage;
    private String attackerPlayerController;
    private String weapon;

    public PlayerDiedEvent(Date date, EventType type, Integer chainID, String victimName,
                           Integer damage, String playerController, String weapon){
        super(date, type, chainID);
        this.victimName = victimName;
        this.damage = damage;
        this.weapon = weapon;
        this.attackerPlayerController = playerController;
    }

    public String getVictimName() {
        return victimName;
    }

    public Integer getDamage() {
        return damage;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getPlayerController() {
        return attackerPlayerController;
    }
}
