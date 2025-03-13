package com.app.practice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the metadata for a video.
 * This class holds additional details such as synopsis, director, cast, year of release, genre, and running time.
 * It also establishes a one-to-one relationship with the `Video` entity.
 * <p>
 * Author: Ruchir Bisht
 */
@Entity
@Table(name = "videos_meta_data", indexes = {
        @Index(name = "idx_year_of_release", columnList = "year_of_release"),
        @Index(name = "idx_genre", columnList = "genre")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String synopsis;
    private String director;
    private String cast;
    private Integer yearOfRelease;
    private String genre;
    private Integer runningTime;

    @OneToOne
    @JoinColumn(name = "video_id", referencedColumnName = "videoId", nullable = false, unique = true)
    private Video video;

}
