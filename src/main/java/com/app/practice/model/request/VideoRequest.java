package com.app.practice.model.request;

import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;

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
        video.setContent(videoResponse.getContent());

        return video;
    }

    public static VideoMetaData toVideoMetadata(VideoRequest videoRequest, Video video) {
        return new VideoMetaData(
                null, // ID is auto-generated
                videoRequest.getSynopsis(),
                videoRequest.getDirector(),
                videoRequest.getCast(),
                videoRequest.getYearOfRelease(),
                videoRequest.getGenre(),
                videoRequest.getRunningTime(),
                video
        );
    }

    public static EngagementStatistics toEngagementStatistics(Video video) {

        EngagementStatistics engagementStatistics = new EngagementStatistics();
        engagementStatistics.setVideo(video);

        return engagementStatistics;

    }

}
