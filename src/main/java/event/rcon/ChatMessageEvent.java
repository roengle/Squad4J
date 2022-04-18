package event.rcon;

import event.Event;
import event.EventType;

import java.util.Date;

public class ChatMessageEvent extends Event {
    private String chatType;
    private String steamid;
    private String playerName;
    private String message;

    public ChatMessageEvent(Date date, EventType type, String chatType, String steamid, String playerName, String message){
        super(date, type);
        this.chatType = chatType;
        this.steamid = steamid;
        this.playerName = playerName;
        this.message = message;
    }

    public String getChatType() {
        return chatType;
    }

    public String getSteamid() {
        return steamid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getMessage() {
        return message;
    }
}
