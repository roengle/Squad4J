package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class NewGameEvent extends Event {
    private String dlc;
    private String mapName;
    private String layerName;
    private Integer maxTickRate;

    public NewGameEvent(Date date, EventType type, Integer chainID, String dlc, String mapName, String layerName, Integer maxTickRate){
        super(date, type, chainID);
        this.dlc = dlc;
        this.mapName = mapName;
        this.layerName = layerName;
        this.maxTickRate = maxTickRate;
    }

    public String getDlc() {
        return dlc;
    }

    public String getMapName() {
        return mapName;
    }

    public String getLayerName() {
        return layerName;
    }

    public Integer getMaxTickRate() {
        return maxTickRate;
    }
}
