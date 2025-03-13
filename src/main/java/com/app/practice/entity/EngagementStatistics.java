package com.app.practice.entity;

import jakarta.persistence.*;

/**
 * Author: Ruchir Bisht
 * Entity class representing the 'engagement_statistics' table in the database.
 * This entity tracks video engagement metrics such as impressions and views.
 * It is associated with a single Video entity using a one-to-one relationship.* <p>
 * Note: Lombok is not working on the local machine; hence, getter, setter, and constructors are manually created.
 */
@Entity
@Table(name = "engagement_statistics")
public class EngagementStatistics {

    // Primary key of the EngagementStatistics table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Total number of impressions for the video
    private Long impressions = 0L;

    // Total number of views for the video
    private Long views = 0L;

    // One-to-one relationship with the Video entity
    @OneToOne
    @JoinColumn(name = "video_id", referencedColumnName = "videoId", nullable = false, unique = true)
    private Video video;

    // Default constructor
    public EngagementStatistics() {
    }

    // Constructor to initialize EngagementStatistics with specific values
    public EngagementStatistics(Long id, Long impressions, Long views, Video video) {
        this.id = id;
        this.impressions = impressions;
        this.views = views;
        this.video = video;
    }

    // Getter and Setter methods

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
