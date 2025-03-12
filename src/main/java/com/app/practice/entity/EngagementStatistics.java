package com.app.practice.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "engagement_statistics")
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


    public EngagementStatistics() {
    }

    public EngagementStatistics(Long id, Long impressions, Long views, Video video) {
        this.id = id;
        this.impressions = impressions;
        this.views = views;
        this.video = video;
    }

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
