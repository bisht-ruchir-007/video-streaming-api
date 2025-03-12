package com.app.practice.model.response;

import com.app.practice.entity.Video;

public class VideoResponse {

    private String title;
    private String synopsis;
    private String director;
    private String cast;
    private int yearOfRelease;
    private String genre;
    private int runningTime;
    private String content; // Mock video content as a string
    private boolean isDelisted = false;
    private int impressions = 0;
    private int views = 0;

    public VideoResponse() {

    }

    public VideoResponse(String title, String synopsis, String director, String cast,
                         int yearOfRelease, String genre, int runningTime, String content,
                         boolean isDelisted, int impressions, int views) {
        this.title = title;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.runningTime = runningTime;
        this.content = content;
        this.isDelisted = isDelisted;
        this.impressions = impressions;
        this.views = views;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static VideoResponse videoMapper(Video video) {
        return new VideoResponse(
                video.getTitle(),
                video.getSynopsis(),
                video.getDirector(),
                video.getCast(),
                video.getYearOfRelease(),
                video.getGenre(),
                video.getRunningTime(),
                video.getContent(),
                video.isDelisted(),
                video.getImpressions(),
                video.getViews()
        );
    }
}
