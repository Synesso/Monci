package au.com.loftinspace.monci.monitor.event;

import static au.com.loftinspace.monci.domain.BuildResult.*;
import au.com.loftinspace.monci.domain.BuildStatus;
import au.com.loftinspace.monci.domain.BuildResult;

import java.util.Collection;

public class UpdateEvent {
    private BuildResult overallBuildResult = SUCCESS;
    private final BuildStatus[] statuses;

    public UpdateEvent(BuildStatus... statuses) {
        this.statuses = statuses;
        for (BuildStatus status : statuses) {
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

    public BuildStatus[] getStatuses() {
        return statuses;
    }
}
