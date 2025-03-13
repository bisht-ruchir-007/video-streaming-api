package com.app.practice.service.impl;

import com.app.practice.constants.ModuleConstants;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.EngagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EngagementServiceImpl implements EngagementService {

    private static final Logger logger = LoggerFactory.getLogger(EngagementServiceImpl.class);

    private final VideoRepository videoRepository;

    public EngagementServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public GenericResponse<EngagementResponse> getEngagementStats(Long id) {
        logger.info("Fetching engagement stats for video ID: {}", id);

        try {
            // Fetch video details using the provided video ID
            Video video = videoRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Video not found with ID: {}", id);
                        return new VideoNotFoundException(ModuleConstants.VIDEO_NOT_FOUND);
                    });

            // Extract engagement stats and metadata
            EngagementStatistics stats = video.getEngagementStatistics();
            VideoMetaData metaData = video.getMetaData();

            // Handle null stats case by creating default values
            if (stats == null) {
                logger.warn("No engagement statistics found for video ID: {}", id);
                stats = new EngagementStatistics();
                stats.setViews(0L);
                stats.setImpressions(0L);
            }

            // Build and return the engagement response
            EngagementResponse engagementResponse = new EngagementResponse(
                    video.getTitle(), metaData.getSynopsis(), metaData.getDirector(),
                    stats.getImpressions(), stats.getViews());

            // Return successful response
            return GenericResponse.success(engagementResponse, HttpStatus.OK);

        } catch (VideoNotFoundException ex) {
            // Return error response if video is not found
            return GenericResponse.error(ModuleConstants.VIDEO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Error while fetching engagement stats for video ID {}: {}", id, ex.getMessage());
            // Return error response for any other unexpected errors
            return GenericResponse.error(ModuleConstants.GENERIC_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
