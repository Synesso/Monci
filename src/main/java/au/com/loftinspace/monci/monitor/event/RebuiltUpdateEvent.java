package au.com.loftinspace.monci.monitor.event;

import au.com.loftinspace.monci.domain.BuildStatus;

import java.util.Collection;

public class RebuiltUpdateEvent {
    public RebuiltUpdateEvent(Collection<BuildStatus> statuses) {
    }
}
