package com.app.practice.service.impl;

import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.service.EngagementStrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of the EngagementStrategyService interface using Kafka.
 * This service sends engagement stats messages to a Kafka topic and simulates fetching stats from Kafka.
 * <p>
 * Author: Ruchir Bisht
 */
@Service
public class KafkaEngagementStrategyServiceImpl implements EngagementStrategyService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEngagementStrategyServiceImpl.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Constructor to inject KafkaTemplate dependency.
     *
     * @param kafkaTemplate the Kafka template used to send messages to Kafka topic
     */
    public KafkaEngagementStrategyServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Fetches the engagement statistics for a given video ID by sending a message to Kafka.
     * The engagement stats are simulated with mock data.
     *
     * @param id the ID of the video whose engagement statistics are to be fetched
     * @return a GenericResponse containing the simulated EngagementResponse with video details and stats
     */
    @Override
    public GenericResponse<EngagementResponse> getEngagementStats(Long id) {
        logger.info("Fetching engagement stats for video ID: {} from Kafka", id);

        try {

            String message = "Engagement stats for video ID: " + id;
            kafkaTemplate.send("engagement-stats-topic", message); // Send message to Kafka

            EngagementResponse response = new EngagementResponse("Mock Title", "Mock Synopsis", "Mock Director", 100L, 200L);

            return GenericResponse.success(response, HttpStatus.OK);
        } catch (Exception ex) {
            // Handle any errors encountered while interacting with Kafka
            logger.error("Error fetching stats from Kafka: {}", ex.getMessage());
            return GenericResponse.error("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
