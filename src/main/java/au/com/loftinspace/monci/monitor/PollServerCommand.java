package au.com.loftinspace.monci.monitor;

import au.com.loftinspace.monci.domain.BuildStatus;
import au.com.loftinspace.monci.domain.BuildStatusFactory;
import au.com.loftinspace.monci.monitor.event.*;
import au.com.loftinspace.monci.monitor.listener.Notification;
import static au.com.loftinspace.monci.monitor.listener.Notification.*;
import org.apache.abdera.Abdera;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.Response;

import java.util.*;

public class PollServerCommand implements Runnable {
    private ServerMonitor serverMonitor;
    private Map<String, BuildStatus> previousStatuses;

    public PollServerCommand(ServerMonitor serverMonitor) {
        this.serverMonitor = serverMonitor;
    }

    public void run() {
        String feedUrl = serverMonitor.getServer().getAtomFeedUrl();
        AbderaClient client = new AbderaClient(new Abdera());
        client.getDefaultRequestOptions().setUseLocalCache(false);
        ClientResponse clientResponse = client.get(feedUrl, client.getDefaultRequestOptions());
        if (clientResponse.getType() != Response.ResponseType.SUCCESS) {
            serverMonitor.notify(new UpdateEvent(), ABORTED);
        } else {
            Set<BuildStatus> allStatuses = BuildStatusFactory.convert(clientResponse);
            Map<String, BuildStatus> trimmedStatuses = new HashMap<String, BuildStatus>();
            for (BuildStatus status : allStatuses) {
                if (trimmedStatuses.containsKey(status.getBuildName())) {
                    if (status.getBuildNumber() > trimmedStatuses.get(status.getBuildName()).getBuildNumber()) {
                        trimmedStatuses.put(status.getBuildName(), status);
                    }
                } else {
                    trimmedStatuses.put(status.getBuildName(), status);
                }
            }

            Collection<BuildStatus> statuses = trimmedStatuses.values();
            BuildStatus[] statusesArray = statuses.toArray(new BuildStatus[statuses.size()]);
            UpdateEvent event = new UpdateEvent(statusesArray);
            Notification notification;
            if (previousStatuses == null) {
                notification = Notification.INITIAL;
            } else if (statusBuildNumbersDiffer(previousStatuses, trimmedStatuses)) {
                notification = (statusResultsDiffer(previousStatuses, trimmedStatuses)) ?
                        Notification.REBUILT_STATUS_CHANGED : Notification.REBUILT_STATUS_UNCHANGED;
            } else {
                notification = Notification.NOT_REBUILT;
            }
            serverMonitor.notify(event, notification);
            previousStatuses = trimmedStatuses;
        }
    }

    // todo: cater for changing of map keyset.
    private boolean statusResultsDiffer(Map<String, BuildStatus> statuses1, Map<String, BuildStatus> statuses2) {
        for (String buildName : statuses1.keySet()) {
            if (!statuses1.get(buildName).getBuildResult().equals(statuses2.get(buildName).getBuildResult())) {
                return true;
            }
        }
        return false;
    }

    // todo: cater for changing of map keyset.
    private boolean statusBuildNumbersDiffer(Map<String, BuildStatus> statuses1, Map<String, BuildStatus> statuses2) {
        for (String buildName : statuses1.keySet()) {
            if (!statuses1.get(buildName).getBuildNumber().equals(statuses2.get(buildName).getBuildNumber())) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PollServerCommand that = (PollServerCommand) o;

        if (serverMonitor != null ? !serverMonitor.equals(that.serverMonitor) : that.serverMonitor != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (serverMonitor != null ? serverMonitor.hashCode() : 0);
    }
}
