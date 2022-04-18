package plugins;

import event.rcon.PossessedAdminCameraEvent;
import event.rcon.UnpossessedAdminCameraEvent;
import listener.rcon.PossessedAdminCameraListener;
import listener.rcon.UnpossessedAdminCameraListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminCamTracker implements PossessedAdminCameraListener, UnpossessedAdminCameraListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminCamTracker.class);

    @Override
    public void onPossessedAdminCamera(PossessedAdminCameraEvent possessedAdminCameraEvent) {
        LOGGER.info("User {} entered admin cam.", possessedAdminCameraEvent.getName());
    }

    @Override
    public void onUnpossessedAdminCamera(UnpossessedAdminCameraEvent unpossessedAdminCameraEvent) {
        LOGGER.info("User {} exited admin cam.", unpossessedAdminCameraEvent.getName());
    }
}
