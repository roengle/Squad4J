package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

/**
 * @author Robert Engle
 *
 * Describes an event where a chat message is sent by a player.
 *
 * @see listener.rcon.ChatMessageListener
 */
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

    /**
     * Gets the chat type of the message.
     *
     * @return the chat type
     */
    public String getChatType() {
        return chatType;
    }

    /**
     * Gets the steam64id of the user who sent the message.
     *
     * @return the steam64id of the message author
     */
    public String getSteamid() {
        return steamid;
    }

    /**
     * Gets the name of the player who sent the message.
     *
     * @return the name of the message author
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the content of the message.
     *
     * @return the content of the message
     */
    public String getMessage() {
        return message;
    }
}
