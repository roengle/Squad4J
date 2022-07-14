package listener.rcon;

import event.rcon.PossessedAdminCameraEvent;
import listener.GloballyAttachableListener;

public interface PossessedAdminCameraListener extends GloballyAttachableListener {
    public void onPossessedAdminCamera(PossessedAdminCameraEvent possessedAdminCameraEvent);
}
