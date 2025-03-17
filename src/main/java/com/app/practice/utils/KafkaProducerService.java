package com.app.practice.utils;

import com.app.practice.entity.Video;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to a specified Kafka topic.
     *
     * @param topic   the Kafka topic to which the message will be sent
     * @param message the message payload
     */
    public void sendMessage(String topic, String message) {
        logger.info("Sending message to Kafka topic [{}]: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }

    /**
     * Simulates fetching video details from another microservice via Kafka.
     * This is a placeholder for actual inter-service communication.
     *
     * @param videoId the ID of the video to fetch
     * @return a mock Video object (Replace this with actual service call)
     */
    public Video requestVideoDetails(Long videoId) {
        logger.info("Requesting video details for ID: {}", videoId);

        /*
         TODO: Replace with actual microservice communication (e.g., REST call, Kafka request-reply, or DB lookup)
         Video sampleVideo = new Video(videoId, "Sample Video", new VideoMetaData(), false, "video-content-url");
         */

        return new Video();
    }
}
