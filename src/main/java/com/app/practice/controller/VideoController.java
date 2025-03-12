package com.app.practice.controller;

import com.app.practice.constants.VideoURIConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing video-related operations such as publishing, editing metadata,
 * delisting, loading, playing, listing, searching, and engagement tracking.
 */
@RestController
@RequestMapping(VideoURIConstants.VIDEO_BASE_PATH)
public class VideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * Publishes a new video.
     *
     * @param videoRequest The video details.
     * @return ResponseEntity containing the published video details.
     * @throws VideoAlreadyPresentException If the video already exists.
     */
    @PostMapping(VideoURIConstants.PUBLISH_VIDEO_ENDPOINT)
    public ResponseEntity<VideoResponse> publishVideo(@RequestBody VideoRequest videoRequest)
            throws VideoAlreadyPresentException {
        LOGGER.info("Received request to publish video: {}", videoRequest.getTitle());

        VideoResponse savedVideo = videoService.publishVideo(videoRequest);

        LOGGER.info("Video published successfully: Title : {}", savedVideo.getTitle());
        return ResponseEntity.ok(savedVideo);
    }

    /**
     * Updates video metadata.
     *
     * @param id           The video ID.
     * @param videoRequest Updated video details.
     * @return ResponseEntity with the updated video details.
     * @throws VideoNotFoundException If the video is not found.
     */
    @PutMapping(VideoURIConstants.EDIT_VIDEO_METADATA_ENDPOINT)
    public ResponseEntity<VideoResponse> editVideo(@PathVariable Long id, @RequestBody VideoRequest videoRequest)
            throws VideoNotFoundException {
        LOGGER.info("Received request to update video metadata for ID: {}", id);

        VideoResponse updatedVideo = videoService.editVideo(id, videoRequest);

        LOGGER.info("Video metadata updated successfully for ID: {}", id);
        return ResponseEntity.ok(updatedVideo);
    }

    /**
     * Delists (removes) a video.
     *
     * @param id The video ID.
     * @return ResponseEntity with a success message.
     * @throws VideoNotFoundException If the video is not found.
     */
    @DeleteMapping(VideoURIConstants.DELIST_VIDEO_ENDPOINT)
    public ResponseEntity<String> delistVideo(@PathVariable Long id) throws VideoNotFoundException {
        LOGGER.warn("Received request to delist video with ID: {}", id);

        videoService.delistVideo(id);

        LOGGER.info("Video with ID: {} has been delisted successfully.", id);
        return ResponseEntity.ok("Video has been delisted.");
    }

    /**
     * Loads a video.
     *
     * @param id The video ID.
     * @return ResponseEntity containing video details.
     * @throws VideoNotFoundException If the video is not found.
     */
    @GetMapping(VideoURIConstants.LOAD_VIDEO_ENDPOINT)
    public ResponseEntity<Optional<VideoDTO>> loadVideo(@PathVariable Long id) throws VideoNotFoundException {
        LOGGER.info("Received request to load video with ID: {}", id);

        Optional<VideoDTO> video = videoService.loadVideo(id);

        LOGGER.info("Video loaded successfully for ID: {}", id);
        return ResponseEntity.ok(video);
    }

    /**
     * Streams a video for playback.
     *
     * @param id The video ID.
     * @return ResponseEntity with a message indicating the video is playing.
     * @throws VideoNotFoundException If the video is not found.
     */
    @GetMapping(VideoURIConstants.PLAY_VIDEO_ENDPOINT)
    public ResponseEntity<String> playVideo(@PathVariable Long id) throws VideoNotFoundException {
        LOGGER.info("Received request to play video with ID: {}", id);

        String content = videoService.playVideo(id);

        LOGGER.info("Playing video with ID: {}", id);
        return ResponseEntity.ok("Playing Video: " + content);
    }

    /**
     * Retrieves a list of all videos.
     *
     * @return ResponseEntity containing the list of all videos.
     */
    @GetMapping(VideoURIConstants.LIST_VIDEOS_ENDPOINT)
    public ResponseEntity<List<VideoDTO>> listAllVideos() {
        LOGGER.info("Received request to list all videos");

        List<VideoDTO> videos = videoService.listAllVideos();

        LOGGER.info("Returning {} videos.", videos.size());
        return ResponseEntity.ok(videos);
    }

    /**
     * Searches videos by director.
     *
     * @param director The director's name.
     * @return ResponseEntity containing a list of matching videos.
     */
    @GetMapping(VideoURIConstants.SEARCH_VIDEO_ENDPOINT)
    public ResponseEntity<List<VideoDTO>> searchVideos(@RequestParam String director) {
        LOGGER.info("Received request to search videos by director: {}", director);

        List<VideoDTO> videos = videoService.searchVideos(director);

        LOGGER.info("Found {} videos for director '{}'.", videos.size(), director);
        return ResponseEntity.ok(videos);
    }

    /**
     * Fetches engagement statistics for a video.
     *
     * @param id The video ID.
     * @return ResponseEntity containing engagement statistics.
     * @throws VideoNotFoundException If the video is not found.
     */
    @GetMapping(VideoURIConstants.VIDEO_ENGAGEMENT_ENDPOINT)
    public ResponseEntity<EngagementResponse> getEngagementStats(@PathVariable Long id)
            throws VideoNotFoundException {
        LOGGER.info("Received request for engagement stats of video ID: {}", id);

        EngagementResponse engagementResponse = videoService.getEngagementStats(id);

        LOGGER.info("Returning engagement stats for video ID: {}", id);
        return ResponseEntity.ok(engagementResponse);
    }
}
