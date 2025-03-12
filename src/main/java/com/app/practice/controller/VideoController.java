package com.app.practice.controller;

import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.Video;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // Publish a new video
    @PostMapping("/publish")
    public ResponseEntity<VideoResponse> publishVideo(@RequestBody VideoRequest video) {
        VideoResponse savedVideo = videoService.publishVideo(video);
        return ResponseEntity.ok(savedVideo);
    }

    // Edit video metadata
    @PutMapping("/edit/{id}")
    public ResponseEntity<VideoResponse> editVideo(@PathVariable Long id, @RequestBody VideoRequest videoRequest) throws VideoNotFoundException {
        VideoResponse updatedVideo = videoService.editVideo(id, videoRequest);
        return ResponseEntity.ok(updatedVideo);
    }

    // ✅ 3. Soft delete (Delist) a video
    @DeleteMapping("/delist/{id}")
    public ResponseEntity<String> delistVideo(@PathVariable Long id) throws VideoNotFoundException {
        videoService.delistVideo(id);
        return ResponseEntity.ok("Video has been delisted.");
    }

    // ✅ 4. Load a video (Return metadata + increase impressions)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<VideoDTO>> loadVideo(@PathVariable Long id) throws VideoNotFoundException {
        Optional<VideoDTO> video = videoService.loadVideo(id);
        return ResponseEntity.ok(video);
    }

    // ✅ 5. Play a video (Increase views)
    @GetMapping("/{id}/play")
    public ResponseEntity<String> playVideo(@PathVariable Long id) throws VideoNotFoundException {
        String content = videoService.playVideo(id);
        return ResponseEntity.ok("Playing Video: " + content);
    }

    // ✅ 6. List all available videos (Only selected metadata)
    @GetMapping
    public ResponseEntity<List<VideoDTO>> listAllVideos() {
        return ResponseEntity.ok(videoService.listAllVideos());
    }

    // ✅ 7. Search videos by director
    @GetMapping("/search")
    public ResponseEntity<List<VideoDTO>> searchVideos(@RequestParam String director) {
        return ResponseEntity.ok(videoService.searchVideos(director));
    }

    // ✅ 8. Retrieve engagement statistics (Impressions + Views)
    @GetMapping("/{id}/engagement")
    public ResponseEntity<String> getEngagementStats(@PathVariable Long id) throws VideoNotFoundException {
        int impressions = videoService.getImpressions(id);
        int views = videoService.getViews(id);
        return ResponseEntity.ok("Impressions: " + impressions + ", Views: " + views);
    }
}
