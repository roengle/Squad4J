package plugins;

import event.a2s.A2SUpdatedEvent;
import event.rcon.ChatMessageEvent;
import listener.a2s.A2SUpdatedListener;
import listener.rcon.ChatMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatCommands implements ChatMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatCommands.class);

    @Override
    public void onChatMessage(ChatMessageEvent chatMessageEvent) {
        LOGGER.info("I need to implement chat commands lol");
    }
}
