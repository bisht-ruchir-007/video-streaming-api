package com.app.practice.repository;

import com.app.practice.entity.EngagementStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EngagementStatisticsRepository extends JpaRepository<EngagementStatistics, Long> {

    // Fetch engagement statistics for a specific video ID using JPQL
    @Query("SELECT e FROM EngagementStatistics e WHERE e.video.videoId = :videoId")
    EngagementStatistics findByVideoId(@Param("videoId") Long videoId);
}
