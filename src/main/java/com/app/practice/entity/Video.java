package com.app.practice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "videos_content")
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
    private int impressions = 0;
    private int views = 0;

    @OneToOne(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private VideoMetaData metaData;

    // Constructors
    public Video() {
    }

    public Video(int impressions, Long videoId, String title, String content, boolean isDelisted, int views) {
        this.impressions = impressions;
        this.videoId = videoId;
        this.title = title;
        this.content = content;
        this.isDelisted = isDelisted;
        this.views = views;
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

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public VideoMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(VideoMetaData metaData) {
        this.metaData = metaData;
        metaData.setVideo(this);
    }
}
