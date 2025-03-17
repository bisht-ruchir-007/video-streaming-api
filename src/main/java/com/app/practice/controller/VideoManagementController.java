package com.app.practice.controller;

import com.app.practice.constants.VideoURIConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Ruchir Bisht
 * VideoManagementController handles all the CRUD operations related to video management.
 * It allows publishing, updating, delisting, and listing videos.
 */
@RestController
@RequestMapping(VideoURIConstants.VIDEO_BASE_PATH)
@RequiredArgsConstructor
public class VideoManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoManagementController.class);
    private final VideoService videoService;


    /**
     * Publishes a new video.
     *
     * @param videoRequest The request body containing video details.
     * @return ResponseEntity with the published video details.
     * @throws VideoAlreadyPresentException If the video already exists.
     */
    @PostMapping(VideoURIConstants.PUBLISH_VIDEO_ENDPOINT)
    @Operation(summary = "Publishes a new video.", description = "Publishes a new video.", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<VideoResponse>> publishVideo(@RequestBody VideoRequest videoRequest)
            throws VideoAlreadyPresentException {
        LOGGER.info("Received request to publish video: {}", videoRequest.getTitle());
        GenericResponse<VideoResponse> savedVideo = videoService.publishVideo(videoRequest);
        LOGGER.info("Video published successfully: Title : {}", savedVideo.getData().getTitle());
        return ResponseEntity.ok(savedVideo);
    }

    /**
     * Edits the metadata of an existing video.
     *
     * @param id           The ID of the video to be updated.
     * @param videoRequest The request body containing the new video metadata.
     * @return ResponseEntity with the updated video details.
     * @throws VideoNotFoundException If the video with the given ID is not found.
     */
    @PutMapping(VideoURIConstants.EDIT_VIDEO_METADATA_ENDPOINT)
    @Operation(summary = "Edits existing video.", description = "Edits the metadata of an existing video.", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<VideoResponse>> editVideo(@PathVariable Long id, @RequestBody VideoRequest videoRequest)
            throws VideoNotFoundException {
        LOGGER.info("Received request to update video metadata for ID: {}", id);
        GenericResponse<VideoResponse> updatedVideo = videoService.editVideo(id, videoRequest);
        LOGGER.info("Video metadata updated successfully for ID: {}", id);
        return ResponseEntity.ok(updatedVideo);
    }

    /**
     * Delists an existing video.
     *
     * @param id The ID of the video to be delisted.
     * @return ResponseEntity with a confirmation message.
     * @throws VideoNotFoundException If the video with the given ID is not found.
     */
    @DeleteMapping(VideoURIConstants.DELIST_VIDEO_ENDPOINT)
    @Operation(summary = "Delist (soft delete) a video", description = "Delist (soft delete) a video and its associated metadata", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<String>> delistVideo(@PathVariable Long id) throws VideoNotFoundException {
        LOGGER.warn("Received request to delist video with ID: {}", id);
        GenericResponse<String> deListVideo = videoService.delistVideo(id);
        LOGGER.info("Video with ID: {} has been delisted successfully.", id);
        return ResponseEntity.ok(deListVideo);
    }

    /**
     * Lists all videos with pagination.
     *
     * @param page The page number (default is 0).
     * @param size The page size (default is 10).
     * @return ResponseEntity containing a list of videos.
     */
    @GetMapping(VideoURIConstants.LIST_VIDEOS_ENDPOINT)
    @Operation(summary = "List all videos", description = "Lists all videos with pagination.", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<GenericResponse<List<VideoDTO>>> listAllVideos(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        LOGGER.info("Received request to list all videos");
        GenericResponse<List<VideoDTO>> videos = videoService.listAllVideos(page, size);
        LOGGER.info("Returning {} videos.", videos.getData().size());
        return ResponseEntity.ok(videos);
    }
}
