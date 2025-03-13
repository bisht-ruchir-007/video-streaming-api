package com.app.practice.controller;

import com.app.practice.constants.StatsURIConstants;
import com.app.practice.constants.VideoURIConstants;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.service.EngagementService;
import com.app.practice.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StatsURIConstants.STATS_BASE_PATH)
public class EngagementStatsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EngagementStatsController.class);

    private final EngagementService engagementService;

    public EngagementStatsController(EngagementService engagementService) {
        this.engagementService = engagementService;
    }


    /**
     * Fetches engagement statistics for a video.
     *
     * @param id The video ID.
     * @return ResponseEntity containing engagement statistics.
     * @throws VideoNotFoundException If the video is not found.
     */
    @GetMapping(StatsURIConstants.STATS_ENGAGEMENT_ENDPOINT)
    public ResponseEntity<EngagementResponse> getEngagementStats(@PathVariable Long id)
            throws VideoNotFoundException {
        LOGGER.info("Received request for engagement stats of video ID: {}", id);

        EngagementResponse engagementResponse = engagementService.getEngagementStats(id);

        LOGGER.info("Returning engagement stats for video ID: {}", id);
        return ResponseEntity.ok(engagementResponse);
    }


}
