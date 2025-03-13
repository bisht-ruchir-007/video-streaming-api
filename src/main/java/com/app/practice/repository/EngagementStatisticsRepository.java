package com.app.practice.repository;

import com.app.practice.entity.EngagementStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing EngagementStatistics data.
 * It extends JpaRepository to provide CRUD operations and custom queries for the EngagementStatistics entity.
 * <p>
 * Author: Ruchir Bisht
 */
@Repository
public interface EngagementStatisticsRepository extends JpaRepository<EngagementStatistics, Long> {

    /**
     * Fetches the engagement statistics for a specific video by its video ID.
     *
     * @param videoId the ID of the video whose engagement statistics are to be fetched.
     * @return the EngagementStatistics associated with the given video ID.
     */
    @Query("SELECT e FROM EngagementStatistics e WHERE e.video.videoId = :videoId")
    EngagementStatistics findByVideoId(@Param("videoId") Long videoId);
}
