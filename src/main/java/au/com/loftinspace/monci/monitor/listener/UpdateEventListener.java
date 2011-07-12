package au.com.loftinspace.monci.monitor.listener;

import au.com.loftinspace.monci.monitor.event.UpdateEventNotificationException;
import au.com.loftinspace.monci.monitor.event.UpdateEvent;

public interface UpdateEventListener {

    void notifyInitialBuild(UpdateEvent event) throws UpdateEventNotificationException;
    void notifyRebuiltStatus(UpdateEvent event) throws UpdateEventNotificationException;
    void notifyUnchangedStatus(UpdateEvent event) throws UpdateEventNotificationException;
    void notifyChangedStatus(UpdateEvent event) throws UpdateEventNotificationException;
    void notifyBuildAborted(UpdateEvent event) throws UpdateEventNotificationException;

}
