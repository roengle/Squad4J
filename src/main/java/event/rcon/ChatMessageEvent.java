package event.rcon;

import event.Event;
import event.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Describes an event where a chat message is sent by a player.
 *
 * @see listener.rcon.ChatMessageListener
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class ChatMessageEvent extends Event {
    private final String chatType;
    private final String steamid;
    private final String playerName;
    private final String message;

    /**
     * Constructs a {@link ChatMessageEvent}.
     *
     * @param date a {@link Date} corresponding to when this event occurred
     * @param type the corresponding {@link EventType} for this event
     * @param chatType the chat type of the event. Will be one of: <code>ChatAll</code>, <code>ChatTeam</code>,
     *                 <code>ChatSquad</code>, or <code>ChatAdmin</code>
     * @param steamid the steam64id of the player sending the chat message
     * @param playerName the name of the player sending the chat message
     * @param message the content of the message sent
     */
    public ChatMessageEvent(Date date, EventType type, String chatType, String steamid, String playerName, String message){
        super(date, type);
        this.chatType = chatType;
        this.steamid = steamid;
        this.playerName = playerName;
        this.message = message;
    }
}
