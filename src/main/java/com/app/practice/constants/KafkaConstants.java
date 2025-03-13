package com.app.practice.constants;

/**
 * Defines constants used in the Kafka engagement strategy service.
 * <p>
 * Author: Ruchir Bisht
 */
public class KafkaConstants {

    public static final String ENGAGEMENT_STATS_TOPIC = "engagement-stats-topic";
    public static final String FETCHING_ENGAGEMENT_LOG = "Fetching engagement stats for video ID: {} from Kafka";
    public static final String ERROR_FETCHING_STATS = "Error fetching stats from Kafka: {}";
    public static final String MOCK_VIDEO_TITLE = "Mock Title";
    public static final String MOCK_VIDEO_SYNOPSIS = "Mock Synopsis";
    public static final String MOCK_VIDEO_DIRECTOR = "Mock Director";

    private KafkaConstants() {
        // Private constructor to prevent instantiation
    }
}
