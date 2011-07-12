package au.com.loftinspace.monci.domain;

import org.apache.abdera.model.Entry;

public class BuildStatus {
    private BuildResult buildResult;
    private Integer buildNumber;
    private String buildName;

    public BuildStatus(Entry entry) {
        String title = entry.getTitle();
        String result = title.substring(title.lastIndexOf('(') + 1, title.lastIndexOf(')'));
        buildResult = BuildResult.forName(result);
        buildNumber = Integer.parseInt(title.substring(title.indexOf('#') + 1, title.lastIndexOf(" (")));
        buildName = title.substring(0, title.indexOf('#')).trim();
    }

    public String getBuildName() {
        return buildName;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public BuildResult getBuildResult() {
        return buildResult;
    }

    public String toString() {
        return buildName + " #" + buildNumber + " (" + buildResult +")"; 
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildStatus status = (BuildStatus) o;

        if (buildName != null ? !buildName.equals(status.buildName) : status.buildName != null) return false;
        if (buildNumber != null ? !buildNumber.equals(status.buildNumber) : status.buildNumber != null) return false;
        if (buildResult != status.buildResult) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (buildResult != null ? buildResult.hashCode() : 0);
        result = 31 * result + (buildNumber != null ? buildNumber.hashCode() : 0);
        result = 31 * result + (buildName != null ? buildName.hashCode() : 0);
        return result;
    }
}
