package com.app.practice.service.impl;

import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.service.EngagementStrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEngagementStrategyServiceImpl implements EngagementStrategyService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEngagementStrategyServiceImpl.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEngagementStrategyServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public GenericResponse<EngagementResponse> getEngagementStats(Long id) {
        logger.info("Fetching engagement stats for video ID: {} from Kafka", id);
        // This logic can be a simulated Kafka interaction for engagement stats.
        // For the sake of simplicity, let's assume it returns mock data.

        try {
            String message = "Engagement stats for video ID: " + id;
            kafkaTemplate.send("engagement-stats-topic", message); // Send the message to Kafka topic.

            // Simulating a mock response.
            EngagementResponse response = new EngagementResponse("Mock Title", "Mock Synopsis", "Mock Director", 100L, 200L);
            return GenericResponse.success(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error fetching stats from Kafka: {}", ex.getMessage());
            return GenericResponse.error("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
