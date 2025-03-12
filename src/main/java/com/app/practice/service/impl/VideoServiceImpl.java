package com.app.practice.service.impl;

import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.Video;
import com.app.practice.exception.VideoNotFoundException;
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
    public Video publishVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Video editVideo(Long id, Video video) throws VideoNotFoundException {
        Video existing = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        existing.setTitle(video.getTitle());
        existing.setDirector(video.getDirector());
        existing.setGenre(video.getGenre());
        return videoRepository.save(existing);
    }

    @Override
    public void delistVideo(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        video.setDelisted(true);
        videoRepository.save(video);
    }

    @Override
    public Optional<Video> loadVideo(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));
        video.setImpressions(video.getImpressions() + 1);
        videoRepository.save(video);
        return Optional.of(video);
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
    public int getImpressions(Long id) throws VideoNotFoundException {
        return videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"))
                .getImpressions();
    }

    @Override
    public int getViews(Long id) throws VideoNotFoundException {
        return videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"))
                .getViews();
    }
}
