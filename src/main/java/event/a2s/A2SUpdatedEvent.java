package event.a2s;

import a2s.response.A2SCombinedResponse;
import event.Event;
import event.EventType;

import java.util.Date;

public class A2SUpdatedEvent extends Event {
    private final A2SCombinedResponse response;

    public A2SUpdatedEvent(Date date, EventType type, A2SCombinedResponse response) {
        super(date, type);
        this.response = response;
    }

    public A2SCombinedResponse getResponse() {
        return response;
    }
}
