package listener.rcon;

import event.rcon.LayerInfoUpdatedEvent;
import listener.GloballyAttachableListener;

public interface LayerInfoUpdatedListener extends GloballyAttachableListener {
    public void onLayerInfoUpdated(LayerInfoUpdatedEvent layerInfoUpdatedEvent);
}
