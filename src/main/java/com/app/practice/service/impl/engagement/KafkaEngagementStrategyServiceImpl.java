package com.app.practice.service.impl.engagement;

import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.service.EngagementStrategyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.app.practice.constants.KafkaConstants.*;

@Service
@RequiredArgsConstructor
public class KafkaEngagementStrategyServiceImpl implements EngagementStrategyService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEngagementStrategyServiceImpl.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public GenericResponse<EngagementResponse> getEngagementStats(Long id) {
        logger.info(FETCHING_ENGAGEMENT_LOG, id);

        try {
            String message = "Engagement stats for video ID: " + id;
            kafkaTemplate.send(ENGAGEMENT_STATS_TOPIC, message);

            EngagementResponse response = new EngagementResponse(MOCK_VIDEO_TITLE, MOCK_VIDEO_SYNOPSIS, MOCK_VIDEO_DIRECTOR, 100L, 200L);

            return GenericResponse.success(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(ERROR_FETCHING_STATS, ex.getMessage());
            return GenericResponse.error("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
