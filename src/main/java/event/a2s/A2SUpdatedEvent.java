package event.a2s;

import a2s.response.A2SCombinedResponse;
import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where A2S information is retrieved and updated. Contains a {@link A2SCombinedResponse},
 * which contains the responses for both A2S_INFO and A2S_RULES queries.
 *
 * @see listener.a2s.A2SUpdatedListener
 */
public class A2SUpdatedEvent extends Event {
    private final A2SCombinedResponse response;

    /**
     * Constructs a {@link A2SUpdatedEvent}
     *
     * @param date a {@link Date} representing when the event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param response the {@link A2SCombinedResponse} containing A2S information
     */
    public A2SUpdatedEvent(Date date, EventType type, A2SCombinedResponse response) {
        super(date, type);
        this.response = response;
    }

    /**
     * Gets the {@link A2SCombinedResponse} for this event.
     *
     * @return an {@link A2SCombinedResponse} containing A2S information.
     */
    public A2SCombinedResponse getResponse() {
        return response;
    }
}
