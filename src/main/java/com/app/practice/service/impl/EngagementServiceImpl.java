package com.app.practice.service.impl;

import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.repository.EngagementStatisticsRepository;
import com.app.practice.repository.VideoMetaDataRepository;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.EngagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EngagementServiceImpl implements EngagementService {

    private static final Logger logger = LoggerFactory.getLogger(EngagementServiceImpl.class);

    private final VideoRepository videoRepository;

    public EngagementServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public EngagementResponse getEngagementStats(Long id) throws VideoNotFoundException {
        logger.info("Fetching engagement stats for video ID: {}", id);

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Video not found with ID: {}", id);
                    return new VideoNotFoundException("Video not found");
                });

        EngagementStatistics stats = video.getEngagementStatistics();
        VideoMetaData metaData = video.getMetaData();
        if (stats == null) {
            logger.warn("No engagement statistics found for video ID: {}", id);
            stats = new EngagementStatistics();
            stats.setViews(0L);
            stats.setImpressions(0L);
        }

        return new EngagementResponse(video.getTitle(), metaData.getSynopsis(),
                metaData.getDirector(), stats.getImpressions(), stats.getViews());
    }

}
