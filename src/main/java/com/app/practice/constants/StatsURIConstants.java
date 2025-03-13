package com.app.practice.constants;

public class StatsURIConstants {

    // Base path for engagement-stats related APIs
    public static final String STATS_BASE_PATH = "/api/v1/stats";

    // Endpoints for stats operations
    public static final String STATS_ENGAGEMENT_ENDPOINT = "/{id}/engagement";

    // Private constructor to prevent instantiation
    private StatsURIConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated.");
    }
}
