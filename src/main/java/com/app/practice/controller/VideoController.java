package com.app.practice.controller;

import com.app.practice.constants.VideoURIConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.service.EngagementService;
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
//@Tag(name = "Video Controller", description = "Video endpoints")
public class VideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    private final VideoService videoService;
    private final com.app.practice.service.EngagementService engagementService;

    public VideoController(VideoService videoService, EngagementService engagementService) {
        this.videoService = videoService;
        this.engagementService = engagementService;
    }

    /**
     * Publishes a new video.
     *
     * @param videoRequest The video details.
     * @return ResponseEntity containing the published video details.
     * @throws VideoAlreadyPresentException If the video already exists.
     */
    @PostMapping(VideoURIConstants.PUBLISH_VIDEO_ENDPOINT)
    // @Operation(summary = "API: To Publishes a new video.", description = "", security = {@SecurityRequirement(name = "bearerAuth")})
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
    public ResponseEntity<List<VideoDTO>> listAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        LOGGER.info("Received request to list all videos");

        List<VideoDTO> videos = videoService.listAllVideos(page, size);

        LOGGER.info("Returning {} videos.", videos.size());
        return ResponseEntity.ok(videos);
    }

    /**
     * Searches videos by director.
     *
     * @param director The director's name.
     * @return ResponseEntity containing a list of matching videos.
     */
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
    public ResponseEntity<List<VideoDTO>> searchVideos(
            @RequestParam String searchPhrase,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        LOGGER.info("Received request to search videos - Phrase: {}, Page: {}, Size: {}", searchPhrase, page, size);
        List<VideoDTO> results = videoService.searchVideosBasedOnSearchPhrase(searchPhrase, page, size);

        return results.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(results);
    }



}



