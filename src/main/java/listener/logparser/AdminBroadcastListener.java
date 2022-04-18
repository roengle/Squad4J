package listener.logparser;

import event.logparser.AdminBroadcastEvent;
import listener.GloballyAttachbleListener;

@FunctionalInterface
public interface AdminBroadcastListener extends GloballyAttachbleListener {
    public void onAdminBroadcast(AdminBroadcastEvent adminBroadcastEvent);
}
