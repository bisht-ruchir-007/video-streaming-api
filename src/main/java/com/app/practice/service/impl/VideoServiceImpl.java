package com.app.practice.service.impl;

import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.repository.VideoMetaDataRepository;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMetaDataRepository videoMetaDataRepository;

    public VideoServiceImpl(VideoRepository videoRepository, VideoMetaDataRepository videoMetaDataRepository) {
        this.videoRepository = videoRepository;
        this.videoMetaDataRepository = videoMetaDataRepository;
    }

    @Override
    public VideoResponse publishVideo(VideoRequest videoRequest) throws VideoAlreadyPresentException {
        // Check if a video with the same title already exists
        if (videoRepository.existsByTitle(videoRequest.getTitle())) {
            throw new VideoAlreadyPresentException("Video already present with title: " + videoRequest.getTitle());
        }

        // Create and save Video entity
        Video video = VideoRequest.toVideo(videoRequest);
        videoRepository.save(video);

        // Create and save VideoMetaData entity
        VideoMetaData videoMetaData = VideoRequest.toVideoMetadata(videoRequest, video);
        videoMetaDataRepository.save(videoMetaData);

        video.setMetaData(videoMetaData);
        videoRepository.save(video);

        return VideoResponse.videoMapper(video, videoMetaData);
    }

    @Override
    public VideoResponse editVideo(Long id, VideoRequest videoRequest) throws VideoNotFoundException {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        existingVideo.setTitle(videoRequest.getTitle());

        // Update VideoMetaData
        VideoMetaData metaData = existingVideo.getMetaData();
        if (metaData == null) {
            metaData = new VideoMetaData();
            metaData.setVideo(existingVideo);
        }

        metaData.setDirector(videoRequest.getDirector());
        metaData.setGenre(videoRequest.getGenre());
        metaData.setCast(videoRequest.getCast());
        metaData.setYearOfRelease(videoRequest.getYearOfRelease());
        metaData.setRunningTime(videoRequest.getRunningTime());

        videoMetaDataRepository.save(metaData);
        existingVideo.setMetaData(metaData);
        videoRepository.save(existingVideo);

        return VideoResponse.videoMapper(existingVideo, metaData);
    }

    @Override
    public void delistVideo(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        if (!video.isDelisted()) {
            video.setDelisted(true);
            videoRepository.save(video);
        }
    }

    @Override
    public Optional<VideoDTO> loadVideo(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        if (video.isDelisted()) {
            throw new VideoNotFoundException("Video is delisted.");
        }

        video.setImpressions(video.getImpressions() + 1);
        videoRepository.save(video);

        VideoMetaData metaData = video.getMetaData();

        VideoDTO videoDTO = new VideoDTO(video.getVideoId(), video.getTitle(),
                metaData.getDirector(), metaData.getCast(), metaData.getGenre(), metaData.getRunningTime());

        return Optional.of(videoDTO);
    }

    @Override
    public String playVideo(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        if (video.isDelisted()) {
            throw new VideoNotFoundException("Video is delisted.");
        }

        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
        return video.getContent();
    }

    @Override
    public List<VideoDTO> listAllVideos() {
        return videoRepository.findByIsDelistedFalse().stream()
                .map(video -> {
                    VideoMetaData metaData = video.getMetaData();
                    return new VideoDTO(video.getVideoId(), video.getTitle(),
                            metaData.getDirector(), metaData.getCast(),
                            metaData.getGenre(), metaData.getRunningTime());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoDTO> searchVideos(String director) {
        return videoMetaDataRepository.findByDirectorIgnoreCase(director).stream()
                .map(metaData -> new VideoDTO(metaData.getVideo().getVideoId(), metaData.getVideo().getTitle(),
                        metaData.getDirector(), metaData.getCast(), metaData.getGenre(), metaData.getRunningTime()))
                .collect(Collectors.toList());
    }

    @Override
    public EngagementResponse getEngagementStats(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        VideoMetaData metaData = video.getMetaData();

        return new EngagementResponse(video.getTitle(), metaData.getSynopsis(),
                metaData.getDirector(), video.getImpressions(), video.getViews());
    }
}
