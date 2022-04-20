package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

public class LayerInfoUpdatedEvent extends Event {
    private final String currentLayer;
    private final String nextLayer;

    public LayerInfoUpdatedEvent(Date date, EventType type, String currentLayer, String nextLayer) {
        super(date, type);
        this.currentLayer = currentLayer;
        this.nextLayer = nextLayer;
    }

    public String getCurrentLayer() {
        return currentLayer;
    }

    public String getNextLayer() {
        return nextLayer;
    }
}
