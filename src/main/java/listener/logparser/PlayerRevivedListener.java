package listener.logparser;

import event.logparser.PlayerRevivedEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface PlayerRevivedListener extends GloballyAttachbleListener {
    public void onPlayerRevived(PlayerRevivedEvent playerRevivedEvent);
}
