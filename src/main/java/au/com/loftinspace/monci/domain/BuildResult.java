package au.com.loftinspace.monci.domain;

public enum BuildResult {
    SUCCESS, FAILURE, ABORTED;

    public static BuildResult forName(String result) {
        try {
            return valueOf(result);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
