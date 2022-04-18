package listener.rcon;

import event.rcon.PossessedAdminCameraEvent;
import listener.GloballyAttachbleListener;

public interface PossessedAdminCameraListener extends GloballyAttachbleListener {
    public void onPossessedAdminCamera(PossessedAdminCameraEvent possessedAdminCameraEvent);
}
