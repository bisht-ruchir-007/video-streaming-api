package com.app.practice.controller;

import com.app.practice.constants.StatsURIConstants;
import com.app.practice.constants.VideoURIConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.service.EngagementStrategyService;
import com.app.practice.service.VideoService;
import com.app.practice.service.VideoStreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Code Author: Ruchir Bisht
 * VideoEngagementController handles video-related operations such as playing videos, loading content,
 * searching videos by director, and fetching engagement statistics.
 */
@RestController
@RequestMapping(VideoURIConstants.VIDEO_BASE_PATH)
@RequiredArgsConstructor
public class VideoEngagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoEngagementController.class);

    private final VideoService videoService;
    private final EngagementStrategyService engagementService;
    private final VideoStreamService videoStreamService;

    /**
     * Loads video content by ID.
     */
    @GetMapping(VideoURIConstants.LOAD_VIDEO_ENDPOINT)
    @Operation(summary = "Loads video content by ID.", description = "Loads video content by ID.", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<VideoDTO>> loadVideoContent(@PathVariable Long id) throws VideoNotFoundException {
        LOGGER.info("Received request to load video content with ID: {}", id);
        GenericResponse<VideoDTO> videoContent = videoStreamService.loadVideo(id);
        LOGGER.info("Loading video with ID: {}", id);
        return ResponseEntity.ok(videoContent);
    }

    /**
     * Plays video by ID.
     */
    @GetMapping(VideoURIConstants.PLAY_VIDEO_ENDPOINT)
    @Operation(summary = "Plays video by ID.", description = "Plays(streams) video by ID.", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<String>> playVideo(@PathVariable Long id) throws VideoNotFoundException {
        LOGGER.info("Received request to play video with ID: {}", id);
        GenericResponse<String> videoContent = videoStreamService.playVideo(id);
        LOGGER.info("Playing video with ID: {}", id);
        return ResponseEntity.ok(videoContent);
    }

    /**
     * Searches videos by director.
     */
    @GetMapping(VideoURIConstants.SEARCH_BY_DIRECTOR)
    @Operation(summary = "Searches videos by director", description = "Searches videos by director's name.", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<List<VideoDTO>>> searchVideosByDirector(@RequestParam String director,
                                                                                  @RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        LOGGER.info("Received request to search videos by director: {}", director);
        GenericResponse<List<VideoDTO>> videos = videoService.searchVideos(director, page, size);
        LOGGER.info("Found {} videos for director '{}'.", videos.getData().size(), director);
        return ResponseEntity.ok(videos);
    }

    /**
     * Searches videos based on a search phrase.
     */
    @GetMapping(VideoURIConstants.SEARCH_VIDEO_ENDPOINT)
    @Operation(summary = "Searches videos on search phrase.", description = "Searches videos based on a search phrase. (Director/Genre/Cast)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<List<VideoDTO>>> searchVideos(@RequestParam String searchPhrase,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        LOGGER.info("Received request to search videos - Phrase: {}, Page: {}, Size: {}", searchPhrase, page, size);
        GenericResponse<List<VideoDTO>> videosList = videoService.searchVideosBasedOnSearchPhrase(searchPhrase, page, size);
        return videosList.getData().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(videosList);
    }

    /**
     * Fetches engagement statistics for a video.
     *
     * @param id The video ID.
     * @return ResponseEntity containing engagement statistics.
     * @throws VideoNotFoundException If the video is not found.
     */
    @GetMapping(StatsURIConstants.STATS_ENGAGEMENT_ENDPOINT)
    @Operation(summary = "Fetches engagement statistics for a video.", description = " Fetches engagement statistics for a video i.e. impressions &  views", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<EngagementResponse>> getEngagementStats(@PathVariable Long id)
            throws VideoNotFoundException {
        LOGGER.info("Received request for engagement stats of video ID: {}", id);
        GenericResponse<EngagementResponse> engagementStatsResponse = engagementService.getEngagementStats(id);
        LOGGER.info("Returning engagement stats for video ID: {}", id);
        return ResponseEntity.ok(engagementStatsResponse);
    }

    // Add other engagement-related endpoints as necessary (e.g., tracking engagement)
}
