package au.com.loftinspace.monci.monitor.listener;

import au.com.loftinspace.monci.monitor.event.UpdateEventNotificationException;
import au.com.loftinspace.monci.monitor.event.UpdateEvent;
import au.com.loftinspace.monci.monitor.event.InitialUpdateEvent;
import au.com.loftinspace.monci.domain.BuildResult;
import au.com.loftinspace.usblight.delcom.*;

import javax.usb.UsbException;

public class DelcomLightListener implements UpdateEventListener {
    private LightController controller;

    public DelcomLightListener() throws UsbLightException {
        Light light = new LightImpl();
        controller = new LightController(light);
    }

    public void notifyInitialBuild(UpdateEvent event) throws UpdateEventNotificationException {
        changeLight(event);
    }

    public void notifyRebuiltStatus(UpdateEvent event) throws UpdateEventNotificationException {
        changeLight(event);
    }

    public void notifyUnchangedStatus(UpdateEvent event) throws UpdateEventNotificationException {
    }

    public void notifyChangedStatus(UpdateEvent event) throws UpdateEventNotificationException {
        changeLight(event);
    }

    public void notifyBuildAborted(UpdateEvent event) throws UpdateEventNotificationException {
        changeLight(event);
    }

    private void changeLight(UpdateEvent event) throws UpdateEventNotificationException {
        BuildResult result = ((UpdateEvent) event).getOverallBuildResult();
        try {
            switch (result) {
                case SUCCESS:
                    controller.changeColour(LEDColour.GREEN);
                    break;
                case ABORTED:
                    controller.changeColour(LEDColour.BLUE);
                    break;
                default:
                    controller.changeColour(LEDColour.RED);
                    break;
            }
        } catch (UsbException e) {
            throw new UpdateEventNotificationException(e);
        }
    }

}
