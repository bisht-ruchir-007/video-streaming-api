package com.app.practice.service.impl;

import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.Video;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public VideoResponse publishVideo(VideoRequest videoRequest) {

        Video video = VideoRequest.toVideo(videoRequest);
        videoRepository.save(video);
        return VideoResponse.videoMapper(video);
    }

    @Override
    public VideoResponse editVideo(Long id, VideoRequest video) throws VideoNotFoundException {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        existingVideo.setTitle(video.getTitle());
        existingVideo.setDirector(video.getDirector());
        existingVideo.setGenre(video.getGenre());

        videoRepository.save(existingVideo);

        return VideoResponse.videoMapper(existingVideo);
    }

    @Override
    public void delistVideo(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        video.setDelisted(true);
        videoRepository.save(video);
    }

    @Override
    public Optional<VideoDTO> loadVideo(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        video.setImpressions(video.getImpressions() + 1);
        videoRepository.save(video);

        VideoDTO videoDTO = new VideoDTO(video.getId(), video.getTitle(), video.getDirector(),
                video.getCast(), video.getGenre(), video.getRunningTime());

        return Optional.of(videoDTO);
    }

    @Override
    public String playVideo(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
        return video.getContent();
    }

    @Override
    public List<VideoDTO> listAllVideos() {
        return videoRepository.findByIsDelistedFalse().stream()
                .map(video -> new VideoDTO(video.getId(), video.getTitle(), video.getDirector(),
                        video.getCast(), video.getGenre(), video.getRunningTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoDTO> searchVideos(String director) {
        return videoRepository.findByDirectorContainingIgnoreCase(director).stream()
                .map(video -> new VideoDTO(video.getId(), video.getTitle(), video.getDirector(),
                        video.getCast(), video.getGenre(), video.getRunningTime()))
                .collect(Collectors.toList());
    }

    @Override
    public EngagementResponse getEngagementStats(Long id) throws VideoNotFoundException {
        return videoRepository.findById(id).map(video -> new EngagementResponse(video.getTitle(), video.getSynopsis(),
                        video.getDirector(), video.getImpressions(), video.getViews()))
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

    }
}
