package au.com.loftinspace.monci.monitor.event;

import au.com.loftinspace.monci.domain.BuildStatus;
import au.com.loftinspace.monci.domain.BuildResult;
import static au.com.loftinspace.monci.domain.BuildResult.FAILURE;
import static au.com.loftinspace.monci.domain.BuildResult.ABORTED;

import java.util.Collection;

public class InitialUpdateEvent {
    private final Collection<BuildStatus> statuses;
    private BuildResult overallBuildResult;

    public InitialUpdateEvent(Collection<BuildStatus> statuses) {
        this.statuses = statuses;
        for (BuildStatus status: statuses) {
            if (FAILURE.equals(status.getBuildResult())) {
                overallBuildResult = FAILURE;
                break;
            } else if (ABORTED.equals(status.getBuildResult()) && !overallBuildResult.equals(FAILURE)) {
                overallBuildResult = ABORTED;
            }
        }
    }

    public BuildResult getOverallBuildResult() {
        return overallBuildResult;
    }
}
