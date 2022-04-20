package listener.rcon;

import event.rcon.LayerInfoUpdatedEvent;
import listener.GloballyAttachbleListener;

public interface LayerInfoUpdatedListener extends GloballyAttachbleListener {
    public void onLayerInfoUpdated(LayerInfoUpdatedEvent layerInfoUpdatedEvent);
}
