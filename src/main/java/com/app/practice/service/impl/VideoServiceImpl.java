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
import com.app.practice.utils.VideoMetaDataSpecification;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    private final VideoRepository videoRepository;
    private final VideoMetaDataRepository videoMetaDataRepository;
    private final EngagementStatisticsRepository engagementStatsRepo;

    public VideoServiceImpl(VideoRepository videoRepository, VideoMetaDataRepository videoMetaDataRepository, EngagementStatisticsRepository engagementStatsRepo) {
        this.videoRepository = videoRepository;
        this.videoMetaDataRepository = videoMetaDataRepository;
        this.engagementStatsRepo = engagementStatsRepo;
    }

    @Override
    @Transactional
    public VideoResponse publishVideo(VideoRequest videoRequest) throws VideoAlreadyPresentException {
        logger.info("Publishing new video: {}", videoRequest.getTitle());

        if (videoRepository.existsByTitle(videoRequest.getTitle())) {
            logger.error("Video already exists with title: {}", videoRequest.getTitle());
            throw new VideoAlreadyPresentException("Video already present with title: " + videoRequest.getTitle());
        }

        Video video = VideoRequest.toVideo(videoRequest);
        VideoMetaData videoMetaData = VideoRequest.toVideoMetadata(videoRequest, video);
        video.setMetaData(videoMetaData);

        EngagementStatistics engagementStatistics = VideoRequest.toEngagementStatistics(video);
        video.setEngagementStatistics(engagementStatistics);

        videoRepository.save(video);
        logger.info("Video published successfully: {}", videoRequest.getTitle());

        return VideoResponse.videoMapper(video);
    }

    @Override
    @Transactional
    public VideoResponse editVideo(Long id, VideoRequest videoRequest) throws VideoNotFoundException {
        logger.info("Editing video with ID: {}", id);

        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with ID: {}", id);
                    return new VideoNotFoundException("Video not found");
                });

        existingVideo.setTitle(videoRequest.getTitle());

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

        logger.info("Video edited successfully: {}", videoRequest.getTitle());
        return VideoResponse.videoMapper(existingVideo);
    }

    @Override
    @Transactional
    public void delistVideo(Long id) throws VideoNotFoundException {
        logger.info("Delisting video with ID: {}", id);

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with ID: {}", id);
                    return new VideoNotFoundException("Video not found");
                });

        if (!video.isDelisted()) {
            video.setDelisted(true);
            videoRepository.save(video);
            logger.info("Video successfully delisted: {}", video.getTitle());
        }
    }

    @Override
    @Transactional
    public Optional<VideoDTO> loadVideo(Long id) throws VideoNotFoundException {
        logger.info("Loading video with ID: {}", id);

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with ID: {}", id);
                    return new VideoNotFoundException("Video not found");
                });

        if (video.isDelisted()) {
            logger.warn("Video is delisted: {}", video.getTitle());
            throw new VideoNotFoundException("Video is delisted.");
        }

        EngagementStatistics engagementStats = video.getEngagementStatistics();
        if (engagementStats == null) {
            engagementStats = new EngagementStatistics();
            engagementStats.setVideo(video);
            engagementStats.setViews(1L);
            engagementStats.setImpressions(1L);
        } else {
            engagementStats.setImpressions(engagementStats.getImpressions() + 1);
        }

        engagementStatsRepo.save(engagementStats);
        video.setEngagementStatistics(engagementStats);

        VideoMetaData metaData = video.getMetaData();
        return Optional.of(new VideoDTO(video.getVideoId(), video.getTitle(),
                metaData.getDirector(), metaData.getCast(),
                metaData.getGenre(), metaData.getRunningTime()));
    }

    @Override
    @Transactional
    public String playVideo(Long id) throws VideoNotFoundException {
        logger.info("Playing video with ID: {}", id);

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with ID: {}", id);
                    return new VideoNotFoundException("Video not found");
                });

        if (video.isDelisted()) {
            logger.warn("Video is delisted: {}", video.getTitle());
            throw new VideoNotFoundException("Video is delisted.");
        }

        EngagementStatistics engagementStats = video.getEngagementStatistics();
        if (engagementStats == null) {
            engagementStats = new EngagementStatistics();
            engagementStats.setVideo(video);
        }

        engagementStats.setViews(engagementStats.getViews() + 1);
        engagementStatsRepo.save(engagementStats);
        video.setEngagementStatistics(engagementStats);
        videoRepository.save(video);

        return video.getContent();
    }

    @Override
    public List<VideoDTO> listAllVideos(int page, int size) {
        logger.info("Listing all videos (Page: {}, Size: {})", page, size);
        Pageable pageable = PageRequest.of(page, size);

        return videoRepository.findByIsDelistedFalse(pageable)
                .map(video -> new VideoDTO(video.getVideoId(), video.getTitle(),
                        video.getMetaData().getDirector(), video.getMetaData().getCast(),
                        video.getMetaData().getGenre(), video.getMetaData().getRunningTime()))
                .getContent();
    }

    @Override
    public List<VideoDTO> searchVideos(String director, int page, int size) {
        logger.info("Searching videos directed by: {} (Page: {}, Size: {})", director, page, size);

        if (StringUtils.isBlank(director)) {
            logger.warn("Invalid director name received for search");
            throw new IllegalArgumentException("Director name cannot be empty.");
        }

        Pageable pageable = PageRequest.of(page, size);

        List<VideoMetaData> videos = videoMetaDataRepository.findByDirectorIgnoreCase(director, pageable);

        return videos.stream()
                .map(metaData -> new VideoDTO(metaData.getVideo().getVideoId(), metaData.getVideo().getTitle(),
                        metaData.getDirector(), metaData.getCast(), metaData.getGenre(), metaData.getRunningTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoDTO> searchVideosBasedOnSearchPhrase(String searchPhrase, int page, int size) {
        if (StringUtils.isBlank(searchPhrase)) {
            logger.warn("Invalid search phrase received");
            throw new IllegalArgumentException("Invalid search phrase");
        }

        logger.info("Searching videos with phrase: {} (Page: {}, Size: {})", searchPhrase, page, size);
        Specification<VideoMetaData> specification = VideoMetaDataSpecification.searchByKeyword(searchPhrase);
        Pageable pageable = PageRequest.of(page, size);

        Page<VideoMetaData> videoMetaDataPage = videoMetaDataRepository.findAll(specification, pageable);

        return videoMetaDataPage.getContent().stream()
                .map(metaData -> new VideoDTO(metaData.getVideo().getVideoId(), metaData.getVideo().getTitle(),
                        metaData.getDirector(), metaData.getCast(), metaData.getGenre(), metaData.getRunningTime()))
                .filter(Objects::nonNull)
                .toList();
    }
}
