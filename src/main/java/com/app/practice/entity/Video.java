package com.app.practice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a video in the system.
 * It contains information such as the video's title, content, delisting status,
 * and related metadata and engagement statistics.
 * <p>
 * Author: Ruchir Bisht
 */
@Entity
@Table(name = "videos_content", indexes = @Index(name = "idx_video_id", columnList = "video_id"))
@Data
@NoArgsConstructor
@AllArgsConstructor
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


}
