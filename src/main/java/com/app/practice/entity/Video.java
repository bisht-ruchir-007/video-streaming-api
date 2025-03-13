package com.app.practice.entity;

import jakarta.persistence.*;

/**
 * Entity class representing a video in the system.
 * It contains information such as the video's title, content, delisting status,
 * and related metadata and engagement statistics.
 * <p>
 * Author: Ruchir Bisht
 * Note: Lombok is not working on the local machine; hence, getter, setter, and constructors are manually created.
 */
@Entity
@Table(name = "videos_content", indexes = @Index(name = "idx_video_id", columnList = "video_id"))
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;

    @Column(nullable = false, unique = true)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content; // Mock video content as a string

    private boolean isDelisted = false;

    @OneToOne(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private VideoMetaData metaData;

    @OneToOne(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private EngagementStatistics engagementStatistics;

    // Constructors
    public Video() {
    }

    public Video(Long videoId, String title, String content, boolean isDelisted, VideoMetaData metaData, EngagementStatistics engagementStatistics) {
        this.videoId = videoId;
        this.title = title;
        this.content = content;
        this.isDelisted = isDelisted;
        this.metaData = metaData;
        this.engagementStatistics = engagementStatistics;
    }

    // Getters and Setters
    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDelisted() {
        return isDelisted;
    }

    public void setDelisted(boolean delisted) {
        isDelisted = delisted;
    }

    public VideoMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(VideoMetaData metaData) {
        this.metaData = metaData;
        metaData.setVideo(this);
    }

    public EngagementStatistics getEngagementStatistics() {
        return engagementStatistics;
    }

    public void setEngagementStatistics(EngagementStatistics engagementStatistics) {
        this.engagementStatistics = engagementStatistics;
    }
}
