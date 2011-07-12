package au.com.loftinspace.monci.monitor.listener;

import au.com.loftinspace.monci.monitor.event.UpdateEvent;
import au.com.loftinspace.monci.monitor.event.UpdateEventNotificationException;
import au.com.loftinspace.monci.domain.BuildStatus;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintStreamListener implements UpdateEventListener {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    private PrintStream stream;

    public PrintStreamListener(PrintStream stream) {
        this.stream = stream;
    }

    public void notifyInitialBuild(UpdateEvent event) throws UpdateEventNotificationException {
        stream.println(String.format("%1$s - Initial update:", formatter.format(new Date())));
        printStatuses(event);
    }

    public void notifyRebuiltStatus(UpdateEvent event) throws UpdateEventNotificationException {
        stream.println(String.format("%1$s - Rebuilt with no status changes:", formatter.format(new Date())));
        printStatuses(event);
    }

    public void notifyUnchangedStatus(UpdateEvent event) throws UpdateEventNotificationException {
    }

    public void notifyChangedStatus(UpdateEvent event) throws UpdateEventNotificationException {
        stream.println(String.format("%1$s - Rebuilt with at least one status change:", formatter.format(new Date())));
        printStatuses(event);
    }

    public void notifyBuildAborted(UpdateEvent event) throws UpdateEventNotificationException {
        stream.println(String.format("%1$s - Build aborted:", formatter.format(new Date())));
    }

    private void printStatuses(UpdateEvent event) {
        for (BuildStatus status: event.getStatuses()) {
            stream.println("  - " + status.toString());
        }
    }
}
