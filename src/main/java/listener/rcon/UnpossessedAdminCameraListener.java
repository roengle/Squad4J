package listener.rcon;

import event.rcon.UnpossessedAdminCameraEvent;
import listener.GloballyAttachbleListener;

public interface UnpossessedAdminCameraListener extends GloballyAttachbleListener {
    public void onUnpossessedAdminCamera(UnpossessedAdminCameraEvent unpossessedAdminCameraEvent);
}
