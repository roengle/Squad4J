package event.logparser;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a new game starts.
 *
 * @see listener.logparser.NewGameListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class NewGameEvent extends Event {
    private final String dlc;
    private final String mapName;
    private final String layerName;
    private final Integer maxTickRate;

    /**
     * Constructs a {@link NewGameEvent}
     *
     * @param date a {@link Date} representing when the event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of the event
     * @param dlc the DLC for the map and layer (if applicable)
     * @param mapName the name of the map
     * @param layerName the name of the layer for the map
     * @param maxTickRate the max tick rate for the new game
     */
    public NewGameEvent(Date date, EventType type, Integer chainID, String dlc, String mapName, String layerName, Integer maxTickRate){
        super(date, type, chainID);
        this.dlc = dlc;
        this.mapName = mapName;
        this.layerName = layerName;
        this.maxTickRate = maxTickRate;
    }
}
