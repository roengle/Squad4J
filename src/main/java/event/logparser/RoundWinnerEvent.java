package event.logparser;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a game is won by a team. The firing of this event implies that a match has ended.
 *
 * @see listener.logparser.RoundWinnerListener
 */
public class RoundWinnerEvent extends Event {
    private final String winningFaction;
    private final String layerName;

    /**
     * Constructs a {@link RoundWinnerEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chainID the chain ID of this event
     * @param winningFaction the name of the winning faction
     * @param layerName the name of the current layer
     */
    public RoundWinnerEvent(Date date, EventType type, Integer chainID, String winningFaction, String layerName){
        super(date, type, chainID);
        this.winningFaction = winningFaction;
        this.layerName = layerName;
    }

    /**
     * Gets the name of the winning faction.
     *
     * @return the name of the winning faction
     */
    public String getWinningFaction() {
        return winningFaction;
    }

    /**
     * Gets the name of the layer that the winning faction won on.
     *
     * @return the name of the current layer
     */
    public String getLayerName() {
        return layerName;
    }
}
