package com.app.practice.service.impl.engagement;

import com.app.practice.constants.ModuleConstants;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.EngagementStrategyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Implementation of the EngagementStrategyService interface.
 * Provides the logic for fetching engagement statistics for a specific video.
 * <p>
 * Author: Ruchir Bisht
 */
@Service
@RequiredArgsConstructor
public class DBEngagementStrategyServiceImpl implements EngagementStrategyService {

    private static final Logger logger = LoggerFactory.getLogger(DBEngagementStrategyServiceImpl.class);

    private final VideoRepository videoRepository;

    /**
     * Fetches the engagement statistics for a given video ID.
     * If the video is found, it returns the engagement details like impressions and views.
     *
     * @param id the ID of the video whose engagement statistics are to be fetched
     * @return a GenericResponse containing EngagementResponse with video details and engagement stats
     */
    @Override
    public GenericResponse<EngagementResponse> getEngagementStats(Long id) {
        logger.info("Fetching engagement stats for video ID: {}", id);
        try {

            Video video = videoRepository.findById(id)
                    .orElseThrow(() -> new VideoNotFoundException(ModuleConstants.VIDEO_NOT_FOUND));

            EngagementStatistics stats = video.getEngagementStatistics();

            EngagementResponse response = new EngagementResponse(
                    video.getTitle(),
                    video.getMetaData().getSynopsis(),
                    video.getMetaData().getDirector(),
                    stats.getImpressions(),
                    stats.getViews()
            );

            return GenericResponse.success(response, HttpStatus.OK);
        } catch (VideoNotFoundException ex) {
            return GenericResponse.error(ModuleConstants.VIDEO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Error fetching stats: {}", ex.getMessage());
            return GenericResponse.error("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
