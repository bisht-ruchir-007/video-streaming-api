package com.app.practice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Ruchir Bisht
 * Entity class representing the 'engagement_statistics' table in the database.
 * This entity tracks video engagement metrics such as impressions and views.
 * It is associated with a single Video entity using a one-to-one relationship.* <p>
 */
@Entity
@Table(
        name = "engagement_statistics",
        indexes = @Index(name = "idx_video_id", columnList = "video_id")
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngagementStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long impressions = 0L;
    private Long views = 0L;

    @OneToOne
    @JoinColumn(name = "video_id", referencedColumnName = "videoId", nullable = false, unique = true)
    private Video video;

}
