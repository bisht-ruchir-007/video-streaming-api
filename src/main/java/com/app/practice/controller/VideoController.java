package com.app.practice.controller;

import com.app.practice.constants.VideoURIConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.service.VideoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(VideoURIConstants.BASE_API) // Base path set here
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping(VideoURIConstants.PUBLISH)
    public ResponseEntity<VideoResponse> publishVideo(@RequestBody VideoRequest video) throws VideoAlreadyPresentException {
        VideoResponse savedVideo = videoService.publishVideo(video);
        return ResponseEntity.ok(savedVideo);
    }

    @PutMapping(VideoURIConstants.EDIT_METADATA)
    public ResponseEntity<VideoResponse> editVideo(@PathVariable Long id, @RequestBody VideoRequest videoRequest)
            throws VideoNotFoundException {
        VideoResponse updatedVideo = videoService.editVideo(id, videoRequest);
        return ResponseEntity.ok(updatedVideo);
    }

    @DeleteMapping(VideoURIConstants.DELIST)
    public ResponseEntity<String> delistVideo(@PathVariable Long id) throws VideoNotFoundException {
        videoService.delistVideo(id);
        return ResponseEntity.ok("Video has been delisted.");
    }

    @GetMapping(VideoURIConstants.LOAD_VIDEO)
    public ResponseEntity<Optional<VideoDTO>> loadVideo(@PathVariable Long id) throws VideoNotFoundException {
        Optional<VideoDTO> video = videoService.loadVideo(id);
        return ResponseEntity.ok(video);
    }

    @GetMapping(VideoURIConstants.PLAY_VIDEO)
    public ResponseEntity<String> playVideo(@PathVariable Long id) throws VideoNotFoundException {
        String content = videoService.playVideo(id);
        return ResponseEntity.ok("Playing Video: " + content);
    }

    @GetMapping(VideoURIConstants.LIST_VIDEOS)
    public ResponseEntity<List<VideoDTO>> listAllVideos() {
        return ResponseEntity.ok(videoService.listAllVideos());
    }

    @GetMapping(VideoURIConstants.SEARCH)
    public ResponseEntity<List<VideoDTO>> searchVideos(@RequestParam String director) {
        return ResponseEntity.ok(videoService.searchVideos(director));
    }

    @GetMapping(VideoURIConstants.ENGAGEMENT)
    public ResponseEntity<EngagementResponse> getEngagementStats(@PathVariable Long id)
            throws VideoNotFoundException {
        EngagementResponse engagementResponse = videoService.getEngagementStats(id);
        return ResponseEntity.ok(engagementResponse);
    }
}
