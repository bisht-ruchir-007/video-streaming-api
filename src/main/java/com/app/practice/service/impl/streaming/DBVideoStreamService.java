package com.app.practice.service.impl.streaming;

import com.app.practice.constants.ModuleConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.repository.EngagementStatisticsRepository;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.VideoStreamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DBVideoStreamService implements VideoStreamService {

    private static final Logger logger = LoggerFactory.getLogger(DBVideoStreamService.class);

    @Value("${video.stream.loadAndPlayLinked:false}")
    private Boolean isLoadAndPlayLinked;

    private final VideoRepository videoRepository;
    private final EngagementStatisticsRepository engagementStatsRepo;

    /**
     * Fetches a video by its ID and validates its availability.
     *
     * @param id the ID of the video
     * @return the Video entity
     * @throws VideoNotFoundException if the video is not found or delisted
     */
    private Video fetchVideoById(Long id) throws VideoNotFoundException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("{} {}", ModuleConstants.VIDEO_NOT_FOUND, id);
                    return new VideoNotFoundException(ModuleConstants.VIDEO_NOT_FOUND);
                });

        if (video.isDelisted()) {
            logger.warn("{} {}", ModuleConstants.VIDEO_DELISTED, video.getTitle());
            throw new VideoNotFoundException(ModuleConstants.VIDEO_DELISTED);
        }

        return video;
    }


    /**
     * Updates engagement statistics for a video.
     *
     * @param video               the video entity
     * @param incrementImpression flag to indicate if views should be incremented
     */
    private void updateEngagementStatistics(Video video, boolean incrementImpression) {
        EngagementStatistics stats = video.getEngagementStatistics();
        if (stats == null) {
            stats = new EngagementStatistics();
            stats.setVideo(video);
            stats.setViews(0L);
            stats.setImpressions(0L);
        }

        stats.setViews(stats.getViews() + 1);

        if (incrementImpression) {
            stats.setImpressions(stats.getImpressions() + 1);
        }

        engagementStatsRepo.save(stats);
        video.setEngagementStatistics(stats);
    }

    private void updateImpressionEngagementStatistics(Video video) {
        EngagementStatistics stats = video.getEngagementStatistics();
        if (stats == null) {
            stats = new EngagementStatistics();
            stats.setVideo(video);
            stats.setViews(0L);
            stats.setImpressions(0L);
        }

        stats.setImpressions(stats.getImpressions() + 1);

        engagementStatsRepo.save(stats);
        video.setEngagementStatistics(stats);
    }

    @Override
    @Transactional
    public GenericResponse<VideoDTO> loadVideo(Long id) throws VideoNotFoundException {
        logger.info(ModuleConstants.LOADING_VIDEO + "{}", id);
        Video video = fetchVideoById(id);

        updateImpressionEngagementStatistics(video);

        VideoMetaData metaData = video.getMetaData();
        VideoDTO videoDTO = new VideoDTO(video.getVideoId(), video.getTitle(),
                metaData.getDirector(), metaData.getCast(),
                metaData.getGenre(), metaData.getRunningTime());

        return GenericResponse.success(videoDTO, HttpStatus.OK);
    }

    @Override
    @Transactional
    public GenericResponse<String> playVideo(Long id) throws VideoNotFoundException {
        logger.info(ModuleConstants.PLAYING_VIDEO + "{}", id);
        Video video = fetchVideoById(id);

        // NOTE : Assumed that video loading and playing are independent,
        // and engagement stats will be recorded accordingly.
        updateEngagementStatistics(video, isLoadAndPlayLinked);

        return GenericResponse.success(video.getContent(), HttpStatus.OK);
    }
}