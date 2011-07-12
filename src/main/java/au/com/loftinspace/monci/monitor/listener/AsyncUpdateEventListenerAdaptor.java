package au.com.loftinspace.monci.monitor.listener;

import au.com.loftinspace.monci.monitor.event.UpdateEvent;

import java.util.concurrent.Callable;

public class AsyncUpdateEventListenerAdaptor implements Callable {
    private final UpdateEventListener listener;
    private final UpdateEvent event;
    private final Notification notification;

    public AsyncUpdateEventListenerAdaptor(UpdateEventListener listener, UpdateEvent event, Notification notification) {
        this.listener = listener;
        this.event = event;
        this.notification = notification;
    }

    public Object call() throws Exception {
        switch(notification) {
            case INITIAL:
                listener.notifyInitialBuild(event);
                break;
            case REBUILT_STATUS_CHANGED:
                listener.notifyChangedStatus(event);
                break;
            case REBUILT_STATUS_UNCHANGED:
                listener.notifyRebuiltStatus(event);
                break;
            case NOT_REBUILT:
                listener.notifyUnchangedStatus(event);
                break;
            case ABORTED:
                listener.notifyBuildAborted(event);
                break;

        }
        return null;
    }
}
