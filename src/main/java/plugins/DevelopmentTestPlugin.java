package plugins;

import a2s.response.A2SRulesResponse;
import event.a2s.A2SUpdatedEvent;
import event.rcon.LayerInfoUpdatedEvent;
import listener.a2s.A2SUpdatedListener;
import listener.rcon.LayerInfoUpdatedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robert Engle
 *
 * Test class which implements various listeners to test their functionality. Also tests the event binding
 * with these listeners.
 *
 * Will be removed in initial release.
 */
public class DevelopmentTestPlugin implements A2SUpdatedListener, LayerInfoUpdatedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopmentTestPlugin.class);

    @Override
    public void onA2SUpdated(A2SUpdatedEvent a2SUpdatedEvent) {
        A2SRulesResponse rules = a2SUpdatedEvent.getResponse().getRules();
        rules.getRuleEntrySet().forEach(entry -> {
            LOGGER.debug("{} : {}", entry.getKey(), entry.getValue());
        });
    }

    @Override
    public void onLayerInfoUpdated(LayerInfoUpdatedEvent layerInfoUpdatedEvent) {
        String currentLayer = layerInfoUpdatedEvent.getCurrentLayer();
        String nextLayer = layerInfoUpdatedEvent.getNextLayer();
        LOGGER.debug("Current Layer - {}", currentLayer);
        LOGGER.debug("Next Layer - {}", nextLayer);
    }
}
