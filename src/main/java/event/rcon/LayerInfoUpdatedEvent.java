package event.rcon;

import event.Event;
import event.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where layer information is updated from {@link server.RconUpdater}.
 *
 * @see server.RconUpdater
 * @see listener.rcon.LayerInfoUpdatedListener
 */
@Getter
public class LayerInfoUpdatedEvent extends Event {
    private final String currentMap;
    private final String currentLayer;
    private final String nextMap;
    private final String nextLayer;

    /**
     * Constructs a {@link LayerInfoUpdatedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param currentMap the current map being played
     * @param currentLayer the current layer being played
     * @param nextMap the next map
     * @param nextLayer the next layer
     */
    public LayerInfoUpdatedEvent(Date date, EventType type, String currentMap, String currentLayer, String nextMap, String nextLayer) {
        super(date, type);
        this.currentMap = currentMap;
        this.currentLayer = currentLayer;
        this.nextMap = nextMap;
        this.nextLayer = nextLayer;
    }
}
