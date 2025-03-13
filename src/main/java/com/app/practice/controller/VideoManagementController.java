package com.app.practice.controller;

import com.app.practice.constants.VideoURIConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(VideoURIConstants.VIDEO_BASE_PATH)
public class VideoManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoManagementController.class);
    private final VideoService videoService;

    public VideoManagementController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping(VideoURIConstants.PUBLISH_VIDEO_ENDPOINT)
    public ResponseEntity<GenericResponse<VideoResponse>> publishVideo(@RequestBody VideoRequest videoRequest)
            throws VideoAlreadyPresentException {
        LOGGER.info("Received request to publish video: {}", videoRequest.getTitle());
        GenericResponse<VideoResponse> savedVideo = videoService.publishVideo(videoRequest);
        LOGGER.info("Video published successfully: Title : {}", savedVideo.getData().getTitle());
        return ResponseEntity.ok(savedVideo);
    }

    @PutMapping(VideoURIConstants.EDIT_VIDEO_METADATA_ENDPOINT)
    public ResponseEntity<GenericResponse<VideoResponse>> editVideo(@PathVariable Long id, @RequestBody VideoRequest videoRequest)
            throws VideoNotFoundException {
        LOGGER.info("Received request to update video metadata for ID: {}", id);
        GenericResponse<VideoResponse> updatedVideo = videoService.editVideo(id, videoRequest);
        LOGGER.info("Video metadata updated successfully for ID: {}", id);
        return ResponseEntity.ok(updatedVideo);
    }

    @DeleteMapping(VideoURIConstants.DELIST_VIDEO_ENDPOINT)
    public ResponseEntity<GenericResponse<String>> delistVideo(@PathVariable Long id) throws VideoNotFoundException {
        LOGGER.warn("Received request to delist video with ID: {}", id);
        GenericResponse<String> deListVideo = videoService.delistVideo(id);
        LOGGER.info("Video with ID: {} has been delisted successfully.", id);
        return ResponseEntity.ok(deListVideo);
    }

    @GetMapping(VideoURIConstants.LIST_VIDEOS_ENDPOINT)
    public ResponseEntity<GenericResponse<List<VideoDTO>>> listAllVideos(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        LOGGER.info("Received request to list all videos");
        GenericResponse<List<VideoDTO>> videos = videoService.listAllVideos(page, size);
        LOGGER.info("Returning {} videos.", videos.getData().size());
        return ResponseEntity.ok(videos);
    }
}
