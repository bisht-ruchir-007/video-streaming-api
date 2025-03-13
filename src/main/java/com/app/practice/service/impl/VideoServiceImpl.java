package com.app.practice.service.impl;

import com.app.practice.constants.ModuleConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.repository.EngagementStatisticsRepository;
import com.app.practice.repository.VideoMetaDataRepository;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.VideoService;
import com.app.practice.utils.VideoMetaDataSpecification;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
    public GenericResponse<VideoResponse> publishVideo(VideoRequest videoRequest) throws VideoAlreadyPresentException {
        logger.info(ModuleConstants.PUBLISHING_VIDEO + videoRequest.getTitle());

        if (videoRepository.existsByTitle(videoRequest.getTitle())) {
            logger.error(ModuleConstants.VIDEO_ALREADY_PRESENT + videoRequest.getTitle());
            throw new VideoAlreadyPresentException(ModuleConstants.VIDEO_ALREADY_PRESENT + videoRequest.getTitle());
        }

        Video video = VideoRequest.toVideo(videoRequest);
        VideoMetaData videoMetaData = VideoRequest.toVideoMetadata(videoRequest, video);
        video.setMetaData(videoMetaData);

        EngagementStatistics engagementStatistics = VideoRequest.toEngagementStatistics(video);
        video.setEngagementStatistics(engagementStatistics);

        videoRepository.save(video);
        logger.info(ModuleConstants.VIDEO_PUBLISHED_SUCCESSFULLY + videoRequest.getTitle());

        VideoResponse videoDTO = VideoResponse.videoMapper(video);
        return GenericResponse.success(videoDTO, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public GenericResponse<VideoResponse> editVideo(Long id, VideoRequest videoRequest) throws VideoNotFoundException {
        logger.info(ModuleConstants.EDITING_VIDEO + id);

        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error(ModuleConstants.VIDEO_NOT_FOUND + id);
                    return new VideoNotFoundException(ModuleConstants.VIDEO_NOT_FOUND);
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

        logger.info(ModuleConstants.VIDEO_EDITED_SUCCESSFULLY + videoRequest.getTitle());
        VideoResponse videoDTO = VideoResponse.videoMapper(existingVideo);
        return GenericResponse.success(videoDTO, HttpStatus.OK);  // Changed to HttpStatus.OK
    }

    @Override
    @Transactional
    public GenericResponse<String> delistVideo(Long id) throws VideoNotFoundException {
        logger.info(ModuleConstants.DELISTING_VIDEO + id);

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error(ModuleConstants.VIDEO_NOT_FOUND + id);
                    return new VideoNotFoundException(ModuleConstants.VIDEO_NOT_FOUND);
                });

        if (!video.isDelisted()) {
            video.setDelisted(true);
            videoRepository.save(video);
            logger.info(ModuleConstants.VIDEO_DELISTED_SUCCESSFULLY + video.getTitle());
        }

        return GenericResponse.success(ModuleConstants.VIDEO_DELISTED_SUCCESSFULLY, HttpStatus.OK);  // Changed to HttpStatus.OK
    }

    @Override
    @Transactional
    public GenericResponse<VideoDTO> loadVideo(Long id) throws VideoNotFoundException {
        logger.info(ModuleConstants.LOADING_VIDEO + id);

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error(ModuleConstants.VIDEO_NOT_FOUND + id);
                    return new VideoNotFoundException(ModuleConstants.VIDEO_NOT_FOUND);
                });

        if (video.isDelisted()) {
            logger.warn(ModuleConstants.VIDEO_DELISTED + video.getTitle());
            throw new VideoNotFoundException(ModuleConstants.VIDEO_DELISTED);
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
        VideoDTO videoDTO = new VideoDTO(video.getVideoId(), video.getTitle(),
                metaData.getDirector(), metaData.getCast(),
                metaData.getGenre(), metaData.getRunningTime());
        return GenericResponse.success(videoDTO, HttpStatus.OK);
    }

    @Override
    @Transactional
    public GenericResponse<String> playVideo(Long id) throws VideoNotFoundException {
        logger.info(ModuleConstants.PLAYING_VIDEO + id);

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error(ModuleConstants.VIDEO_NOT_FOUND + id);
                    return new VideoNotFoundException(ModuleConstants.VIDEO_NOT_FOUND);
                });

        if (video.isDelisted()) {
            logger.warn(ModuleConstants.VIDEO_DELISTED + video.getTitle());
            throw new VideoNotFoundException(ModuleConstants.VIDEO_DELISTED);
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

        return GenericResponse.success(video.getContent(), HttpStatus.OK);  // HttpStatus.OK remains correct
    }

    @Override
    public GenericResponse<List<VideoDTO>> listAllVideos(int page, int size) {
        logger.info(ModuleConstants.LISTING_ALL_VIDEOS, page, size);
        Pageable pageable = PageRequest.of(page, size);

        List<VideoDTO> videoDTOList = videoRepository.findByIsDelistedFalse(pageable)
                .map(video -> new VideoDTO(video.getVideoId(), video.getTitle(),
                        video.getMetaData().getDirector(), video.getMetaData().getCast(),
                        video.getMetaData().getGenre(), video.getMetaData().getRunningTime()))
                .getContent();
        return GenericResponse.success(videoDTOList, HttpStatus.OK);  // HttpStatus.OK remains correct
    }

    @Override
    public GenericResponse<List<VideoDTO>> searchVideos(String director, int page, int size) {
        logger.info(ModuleConstants.SEARCHING_VIDEOS, director, page, size);

        if (StringUtils.isBlank(director)) {
            logger.warn(ModuleConstants.INVALID_DIRECTOR_NAME);
            return GenericResponse.error(ModuleConstants.INVALID_DIRECTOR_NAME, HttpStatus.BAD_REQUEST);  // HttpStatus.BAD_REQUEST remains correct
        }

        Pageable pageable = PageRequest.of(page, size);

        List<VideoMetaData> videos = videoMetaDataRepository.findByDirectorIgnoreCase(director, pageable);

        List<VideoDTO> videoDTOList = videos.stream()
                .map(metaData -> new VideoDTO(metaData.getVideo().getVideoId(), metaData.getVideo().getTitle(),
                        metaData.getDirector(), metaData.getCast(), metaData.getGenre(), metaData.getRunningTime()))
                .collect(Collectors.toList());

        return GenericResponse.success(videoDTOList, HttpStatus.OK);  // HttpStatus.OK remains correct
    }

    @Override
    public GenericResponse<List<VideoDTO>> searchVideosBasedOnSearchPhrase(String searchPhrase, int page, int size) {
        if (StringUtils.isBlank(searchPhrase)) {
            logger.warn(ModuleConstants.INVALID_SEARCH_PHRASE);
            return GenericResponse.error(ModuleConstants.INVALID_SEARCH_PHRASE, HttpStatus.BAD_REQUEST);  // HttpStatus.BAD_REQUEST remains correct
        }

        logger.info("Searching videos with phrase: {} (Page: {}, Size: {})", searchPhrase, page, size);
        Specification<VideoMetaData> specification = VideoMetaDataSpecification.searchByKeyword(searchPhrase);
        Pageable pageable = PageRequest.of(page, size);

        Page<VideoMetaData> videoMetaDataPage = videoMetaDataRepository.findAll(specification, pageable);

        List<VideoDTO> videoDTOList = videoMetaDataPage.getContent().stream()
                .map(metaData -> new VideoDTO(metaData.getVideo().getVideoId(), metaData.getVideo().getTitle(),
                        metaData.getDirector(), metaData.getCast(), metaData.getGenre(), metaData.getRunningTime()))
                .filter(Objects::nonNull)
                .toList();

        return GenericResponse.success(videoDTOList, HttpStatus.OK);  // HttpStatus.OK remains correct
    }

}
