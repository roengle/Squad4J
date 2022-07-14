package listener.logparser;

import event.logparser.AdminBroadcastEvent;
import listener.GloballyAttachableListener;

@FunctionalInterface
public interface AdminBroadcastListener extends GloballyAttachableListener {
    public void onAdminBroadcast(AdminBroadcastEvent adminBroadcastEvent);
}
