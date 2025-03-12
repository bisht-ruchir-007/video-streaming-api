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

    private final VideoRepository videoRepository;
    private final VideoMetaDataRepository videoMetaDataRepository;
    private final EngagementStatisticsRepository engagementStatsRepo;

    public VideoServiceImpl(VideoRepository videoRepository, VideoMetaDataRepository videoMetaDataRepository, EngagementStatisticsRepository engagmentStatsRepo) {
        this.videoRepository = videoRepository;
        this.videoMetaDataRepository = videoMetaDataRepository;
        this.engagementStatsRepo = engagmentStatsRepo;
    }

    @Override
    @Transactional
    public VideoResponse publishVideo(VideoRequest videoRequest) throws VideoAlreadyPresentException {
        if (videoRepository.existsByTitle(videoRequest.getTitle())) {
            throw new VideoAlreadyPresentException("Video already present with title: " + videoRequest.getTitle());
        }

        Video video = VideoRequest.toVideo(videoRequest);
        VideoMetaData videoMetaData = VideoRequest.toVideoMetadata(videoRequest, video);
        video.setMetaData(videoMetaData);

        EngagementStatistics engagementStatistics = VideoRequest.toEngagementStatistics(video);
        video.setEngagementStatistics(engagementStatistics);

        videoRepository.save(video);

        return VideoResponse.videoMapper(video);
    }

    @Override
    @Transactional
    public VideoResponse editVideo(Long id, VideoRequest videoRequest) throws VideoNotFoundException {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

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

        return VideoResponse.videoMapper(existingVideo);
    }

    @Override
    @Transactional
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
        if (engagementStatistics == null) {
            engagementStatistics = new EngagementStatistics();
            engagementStatistics.setVideo(existingVideo);
            engagementStatistics.setViews(1L);
            engagementStatistics.setImpressions(1L);
        } else {
            engagementStatistics.setImpressions(engagementStatistics.getImpressions() + 1);
        }

        engagementStatsRepo.save(engagementStatistics);
        existingVideo.setEngagementStatistics(engagementStatistics);

        VideoMetaData existingVideoMetaData = existingVideo.getMetaData();
        VideoDTO videoDTO = new VideoDTO(existingVideo.getVideoId(), existingVideo.getTitle(),
                existingVideoMetaData.getDirector(), existingVideoMetaData.getCast(),
                existingVideoMetaData.getGenre(), existingVideoMetaData.getRunningTime());

        return Optional.of(videoDTO);
    }

    @Override
    @Transactional
    public String playVideo(Long id) throws VideoNotFoundException {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException("Video not found"));

        if (existingVideo.isDelisted()) {
            throw new VideoNotFoundException("Video is delisted.");
        }

        EngagementStatistics engagementStatistics = existingVideo.getEngagementStatistics();
        if (engagementStatistics == null) {
            engagementStatistics = new EngagementStatistics();
            engagementStatistics.setVideo(existingVideo);
        }

        engagementStatistics.setViews(engagementStatistics.getViews() + 1);
        engagementStatsRepo.save(engagementStatistics);

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
        EngagementStatistics engagementStatistics = video.getEngagementStatistics();

        return new EngagementResponse(video.getTitle(), metaData.getSynopsis(),
                metaData.getDirector(), engagementStatistics.getImpressions(), engagementStatistics.getViews());
    }

    @Override
    public List<VideoDTO> searchVideosBasedOnSearchPhrase(String searchPhrase) {
        if (StringUtils.isBlank(searchPhrase)) {
            throw new IllegalArgumentException("Invalid search phrase");
        }

        Specification<VideoMetaData> specification = VideoMetaDataSpecification.searchByKeyword(searchPhrase);
        Pageable pageable = PageRequest.of(0, 10); // Fetch first 10 results

        Page<VideoMetaData> videoMetaDataPage = videoMetaDataRepository.findAll(specification, pageable);

        return videoMetaDataPage.getContent().stream()
                .map(metaData -> new VideoDTO(
                        metaData.getVideo().getVideoId(),
                        metaData.getVideo().getTitle(),
                        metaData.getDirector(),
                        metaData.getCast(),
                        metaData.getGenre(),
                        metaData.getRunningTime()
                ))
                .filter(Objects::nonNull)
                .toList();
    }

}
