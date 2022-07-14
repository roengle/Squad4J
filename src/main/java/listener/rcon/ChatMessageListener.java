package listener.rcon;

import event.rcon.ChatMessageEvent;
import listener.GloballyAttachableListener;

public interface ChatMessageListener extends GloballyAttachableListener {
    public void onChatMessage(ChatMessageEvent chatMessageEvent);
}
