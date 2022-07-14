package listener.rcon;

import event.rcon.UnpossessedAdminCameraEvent;
import listener.GloballyAttachableListener;

public interface UnpossessedAdminCameraListener extends GloballyAttachableListener {
    public void onUnpossessedAdminCamera(UnpossessedAdminCameraEvent unpossessedAdminCameraEvent);
}
