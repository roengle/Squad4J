package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class RoundWinnerEvent extends Event {
    private String winningFaction;
    private String layerName;

    public RoundWinnerEvent(Date date, EventType type, Integer chainID, String winningFaction, String layerName){
        super(date, type, chainID);
        this.winningFaction = winningFaction;
        this.layerName = layerName;
    }

    public String getWinningFaction() {
        return winningFaction;
    }

    public String getLayerName() {
        return layerName;
    }
}
