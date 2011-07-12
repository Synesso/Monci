package au.com.loftinspace.monci.server;

import au.com.loftinspace.monci.domain.Build;

import java.util.Set;

public interface CIServer {
    Set<Build> getBuilds();

    String getHomepage();

    void addBuild(Build build);

    String getAtomFeedUrl();
}
