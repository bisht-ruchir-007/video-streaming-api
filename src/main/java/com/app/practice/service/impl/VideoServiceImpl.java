package com.app.practice.service.impl;

import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.repository.EngagementStatisticsRepository;
import com.app.practice.repository.VideoMetaDataRepository;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.VideoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMetaDataRepository videoMetaDataRepository;
    private final EngagementStatisticsRepository engagementStatisticsRepository;

    public VideoServiceImpl(VideoRepository videoRepository, VideoMetaDataRepository videoMetaDataRepository, EngagementStatisticsRepository engagementStatisticsRepository) {
        this.videoRepository = videoRepository;
        this.videoMetaDataRepository = videoMetaDataRepository;
        this.engagementStatisticsRepository = engagementStatisticsRepository;
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

        // Create and save VideoMetaData entity
        EngagementStatistics engagementStatistics = VideoRequest.toEngagementStatistics(video);
        engagementStatisticsRepository.save(engagementStatistics);

        video.setMetaData(videoMetaData);
        video.setEngagementStatistics(engagementStatistics);
        videoRepository.save(video);

        return VideoResponse.videoMapper(video);
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

        return VideoResponse.videoMapper(existingVideo);
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
    @Transactional
    public Optional<VideoDTO> loadVideo(Long id) throws VideoNotFoundException {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        if (existingVideo.isDelisted()) {
            throw new VideoNotFoundException("Video is delisted.");
        }

        EngagementStatistics engagementStatistics = existingVideo.getEngagementStatistics();

        // Update EngagementStats

        if (engagementStatistics == null) {
            engagementStatistics = new EngagementStatistics();
            engagementStatistics.setVideo(existingVideo);
        }

        // update impression
        engagementStatistics.setViews(engagementStatistics.getImpressions() + 1);
        engagementStatisticsRepository.save(engagementStatistics);

        existingVideo.setEngagementStatistics(engagementStatistics);


        videoRepository.save(existingVideo);

        VideoMetaData existingVideoMetaData = existingVideo.getMetaData();

        VideoDTO videoDTO = new VideoDTO(existingVideo.getVideoId(), existingVideo.getTitle(),
                existingVideoMetaData.getDirector(), existingVideoMetaData.getCast(), existingVideoMetaData.getGenre(), existingVideoMetaData.getRunningTime());

        return Optional.of(videoDTO);
    }

    @Override
    public String playVideo(Long id) throws VideoNotFoundException {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        if (existingVideo.isDelisted()) {
            throw new VideoNotFoundException("Video is delisted.");
        }

        EngagementStatistics engagementStatistics = existingVideo.getEngagementStatistics();

        // Update EngagementStats

        if (engagementStatistics == null) {
            engagementStatistics = new EngagementStatistics();
            engagementStatistics.setVideo(existingVideo);
        }

        // Update View Count
        engagementStatistics.setViews(engagementStatistics.getViews() + 1);
        engagementStatisticsRepository.save(engagementStatistics);

        existingVideo.setEngagementStatistics(engagementStatistics);
        videoRepository.save(existingVideo);

        return existingVideo.getContent();
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
                metaData.getDirector(), video.getEngagementStatistics().getImpressions(), video.getEngagementStatistics().getViews());
    }
}
