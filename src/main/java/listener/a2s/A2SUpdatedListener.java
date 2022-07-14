package listener.a2s;

import event.a2s.A2SUpdatedEvent;
import listener.GloballyAttachableListener;

/**
 * Corresponding listener for {@link A2SUpdatedEvent}.
 *
 * @see A2SUpdatedEvent
 */
@FunctionalInterface
public interface A2SUpdatedListener extends GloballyAttachableListener {
    public void onA2SUpdated(A2SUpdatedEvent a2SUpdatedEvent);
}
