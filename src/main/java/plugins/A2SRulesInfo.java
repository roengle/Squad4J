package plugins;

import a2s.response.A2SRulesResponse;
import event.a2s.A2SUpdatedEvent;
import listener.a2s.A2SUpdatedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robert Engle
 *
 * Test class which implements {@link A2SUpdatedListener}. Is used to test functionality of
 * event binding, as well as receive what rule-value pairs are.
 *
 * Will be removed in initial release.
 */
public class A2SRulesInfo implements A2SUpdatedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(A2SRulesInfo.class);

    @Override
    public void onA2SUpdated(A2SUpdatedEvent a2SUpdatedEvent) {
        A2SRulesResponse rules = a2SUpdatedEvent.getResponse().getRules();
        rules.getRuleEntrySet().forEach(entry -> {
            LOGGER.info("{} : {}", entry.getKey(), entry.getValue());
        });
    }
}
