package com.app.practice.model.request;

import com.app.practice.entity.Video;

// NOTE - Lombok is not working in my local machine
public class VideoRequest {

    private String title;
    private String synopsis;
    private String director;
    private String cast;
    private int yearOfRelease;
    private String genre;
    private int runningTime;
    private String content;

    public VideoRequest(String title, String synopsis, String director, String cast, int yearOfRelease, String genre, int runningTime, String content) {
        this.title = title;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.runningTime = runningTime;
        this.content = content;
    }

    public VideoRequest() {
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

    public static Video toVideo(VideoRequest videoResponse) {
        Video video = new Video();
        video.setTitle(videoResponse.getTitle());
        video.setSynopsis(videoResponse.getSynopsis());
        video.setDirector(videoResponse.getDirector());
        video.setCast(videoResponse.getCast());
        video.setYearOfRelease(videoResponse.getYearOfRelease());
        video.setGenre(videoResponse.getGenre());
        video.setRunningTime(videoResponse.getRunningTime());
        video.setContent(videoResponse.getContent());
//        video.setDelisted(videoResponse.get);
//        video.setImpressions(videoResponse.getImpressions());
//        video.setViews(videoResponse.getViews());

        return video;
    }

}
