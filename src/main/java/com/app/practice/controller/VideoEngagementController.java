package com.app.practice.controller;

import com.app.practice.constants.StatsURIConstants;
import com.app.practice.constants.VideoURIConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.service.EngagementService;
import com.app.practice.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VideoURIConstants.VIDEO_BASE_PATH)
public class VideoEngagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoEngagementController.class);
    private final VideoService videoService;
    private final EngagementService engagementService;

    public VideoEngagementController(VideoService videoService, EngagementService engagementService) {
        this.videoService = videoService;
        this.engagementService = engagementService;
    }

    @GetMapping(VideoURIConstants.PLAY_VIDEO_ENDPOINT)
    public ResponseEntity<String> playVideo(@PathVariable Long id) throws VideoNotFoundException {
        LOGGER.info("Received request to play video with ID: {}", id);
        String content = videoService.playVideo(id);
        LOGGER.info("Playing video with ID: {}", id);
        return ResponseEntity.ok("Playing Video: " + content);
    }

    @GetMapping(VideoURIConstants.SEARCH_BY_DIRECTOR)
    public ResponseEntity<List<VideoDTO>> searchVideosByDirector(@RequestParam String director,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        LOGGER.info("Received request to search videos by director: {}", director);
        List<VideoDTO> videos = videoService.searchVideos(director, page, size);
        LOGGER.info("Found {} videos for director '{}'.", videos.size(), director);
        return ResponseEntity.ok(videos);
    }

    @GetMapping(VideoURIConstants.SEARCH_VIDEO_ENDPOINT)
    public ResponseEntity<List<VideoDTO>> searchVideos(@RequestParam String searchPhrase,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        LOGGER.info("Received request to search videos - Phrase: {}, Page: {}, Size: {}", searchPhrase, page, size);
        List<VideoDTO> results = videoService.searchVideosBasedOnSearchPhrase(searchPhrase, page, size);
        return results.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(results);
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

    // Add other engagement-related endpoints as necessary (e.g., tracking engagement)
}
