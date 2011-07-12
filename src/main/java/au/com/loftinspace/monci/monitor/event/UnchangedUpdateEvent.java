package au.com.loftinspace.monci.monitor.event;

import au.com.loftinspace.monci.domain.BuildStatus;

import java.util.Collection;

public class UnchangedUpdateEvent {
    private final Collection<BuildStatus> statuses;

    public UnchangedUpdateEvent(Collection<BuildStatus> statuses) {
        this.statuses = statuses;
    }

    public String toString() {
        return "Unchanged: " + String.valueOf(statuses);
    }
}
