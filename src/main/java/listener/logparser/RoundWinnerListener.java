package listener.logparser;

import event.logparser.RoundWinnerEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface RoundWinnerListener extends GloballyAttachableListener {
    public void onRoundWinner(RoundWinnerEvent roundWinnerEvent);
}
