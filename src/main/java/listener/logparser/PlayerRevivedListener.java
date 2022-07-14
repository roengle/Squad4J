package listener.logparser;

import event.logparser.PlayerRevivedEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface PlayerRevivedListener extends GloballyAttachableListener {
    public void onPlayerRevived(PlayerRevivedEvent playerRevivedEvent);
}
