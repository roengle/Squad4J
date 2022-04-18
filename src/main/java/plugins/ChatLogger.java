package plugins;

import event.rcon.ChatMessageEvent;
import listener.rcon.ChatMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatLogger implements ChatMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatLogger.class);

    @Override
    public void onChatMessage(ChatMessageEvent chatMessageEvent) {
        String chatType = chatMessageEvent.getChatType();
        String name = chatMessageEvent.getPlayerName();
        String message = chatMessageEvent.getMessage();

        LOGGER.info("New Chat: [{}] {}: {}",chatType, name, message);
    }
}
