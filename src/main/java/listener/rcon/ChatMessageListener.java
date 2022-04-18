package listener.rcon;

import event.rcon.ChatMessageEvent;
import listener.GloballyAttachbleListener;

public interface ChatMessageListener extends GloballyAttachbleListener {
    public void onChatMessage(ChatMessageEvent chatMessageEvent);
}
