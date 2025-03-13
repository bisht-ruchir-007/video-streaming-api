package com.app.practice.constants;

/*
    Code Author : Ruchir Bisht

    This class defines constants for the URI paths related to engagement statistics APIs.
    Constants are used to avoid hardcoding string literals throughout the codebase, ensuring maintainability and consistency.

    - The STATS_BASE_PATH defines the base URL for all engagement stats-related operations.
    - The STATS_ENGAGEMENT_ENDPOINT defines a specific endpoint to fetch engagement data for a particular resource (e.g., a video or user).
    - A private constructor is used to prevent instantiation of this constants class, enforcing it as a utility class.
 */

public class StatsURIConstants {

    // Base path for engagement-stats related APIs
    public static final String STATS_BASE_PATH = "/api/v1/stats"; // The root path for all engagement stats APIs

    // Endpoints for stats operations
    public static final String STATS_ENGAGEMENT_ENDPOINT = "/{id}/engagement"; // Endpoint for fetching engagement stats for a specific resource by ID

    // Private constructor to prevent instantiation
    private StatsURIConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated.");
    }
}
