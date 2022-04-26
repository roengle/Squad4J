package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where layer information is updated from {@link server.RconUpdater}.
 *
 * @see server.RconUpdater
 * @see listener.rcon.LayerInfoUpdatedListener
 */
public class LayerInfoUpdatedEvent extends Event {
    private final String currentLayer;
    private final String nextLayer;

    /**
     * Constructs a {@link LayerInfoUpdatedEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param currentLayer the current layer being played
     * @param nextLayer the next layer
     */
    public LayerInfoUpdatedEvent(Date date, EventType type, String currentLayer, String nextLayer) {
        super(date, type);
        this.currentLayer = currentLayer;
        this.nextLayer = nextLayer;
    }

    /**
     * Gets the current layer.
     *
     * @return the current layer
     */
    public String getCurrentLayer() {
        return currentLayer;
    }

    /**
     * Gets the next layer.
     *
     * @return the next layer
     */
    public String getNextLayer() {
        return nextLayer;
    }
}
