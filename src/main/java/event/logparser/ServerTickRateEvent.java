package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

public class ServerTickRateEvent extends Event {
    private Double tickRate;

    public ServerTickRateEvent(Date date, EventType type, Integer chainID, Double tickRate){
        super(date, type, chainID);
        this.tickRate = tickRate;
    }

    public Double getTickRate() {
        return tickRate;
    }
}
