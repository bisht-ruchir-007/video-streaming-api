package com.app.practice.repository;

import com.app.practice.entity.EngagementStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngagementStatisticsRepository extends JpaRepository<EngagementStatistics, Long> {
}
