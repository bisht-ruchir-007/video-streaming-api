package com.app.practice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "videos_meta_data")
public class VideoMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String synopsis;

    private String director;
    private String cast;
    private int yearOfRelease;
    private String genre;
    private int runningTime;

    @OneToOne
    @JoinColumn(name = "video_id", referencedColumnName = "videoId", nullable = false, unique = true)
    private Video video;

    // Constructors
    public VideoMetaData() {
    }

    public VideoMetaData(Long id, String synopsis, String director, String cast, int yearOfRelease, String genre, int runningTime, Video video) {
        this.id = id;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.runningTime = runningTime;
        this.video = video;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
