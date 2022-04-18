package listener.logparser;

import event.logparser.RoundWinnerEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface RoundWinnerListener extends GloballyAttachbleListener {
    public void onRoundWinner(RoundWinnerEvent roundWinnerEvent);
}
