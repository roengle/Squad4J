package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class AdminBroadcastEvent extends Event {
    private String message;
    private String from;

    public AdminBroadcastEvent(Date date, EventType type, Integer chainID, String message, String from){
        super(date, type, chainID);
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }
}
