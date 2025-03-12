package com.app.practice.dto;

// LOMBOK IS NOT WORKING IN MY LOCAL MACHINE
public class VideoDTO {
    private Long id;
    private String title;
    private String director;
    private String cast;
    private String genre;
    private int runningTime;

    public VideoDTO(Long id, String title, String director, String cast, String genre, int runningTime) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.genre = genre;
        this.runningTime = runningTime;
    }

    public VideoDTO() {
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
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
}

