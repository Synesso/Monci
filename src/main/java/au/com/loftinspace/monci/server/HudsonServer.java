package au.com.loftinspace.monci.server;

import au.com.loftinspace.monci.domain.Build;

import java.util.Set;
import java.util.HashSet;

public class HudsonServer implements CIServer {
    private String homepage;
    private HashSet<Build> builds = new HashSet<Build>();
    private String atomFeedUrl;

    public HudsonServer(String atomFeedUrl) {
        this.atomFeedUrl = atomFeedUrl;
        this.homepage = homepage;
    }

    public Set<Build> getBuilds() {
        return builds;
    }

    public String getHomepage() {
        return homepage;
    }

    public void addBuild(Build build) {
        builds.add(build);
    }

    public String getAtomFeedUrl() {
        return atomFeedUrl;
    }
}
