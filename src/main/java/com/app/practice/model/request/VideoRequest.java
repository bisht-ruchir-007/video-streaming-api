package com.app.practice.model.request;

import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;

/**
 * Model class for handling video-related requests. This class is used to pass the video details,
 * such as title, synopsis, director, etc., when creating or updating a video.
 * <p>
 * Author: Ruchir Bisht
 * <p>
 * NOTE - Lombok is not working in my local machine, so getter, setter, and constructors are manually created.
 */
public class VideoRequest {

    private String title;
    private String synopsis;
    private String director;
    private String cast;
    private int yearOfRelease;
    private String genre;
    private int runningTime;
    private String content;

    /**
     * Constructor to create a VideoRequest object with the provided video details.
     *
     * @param title         the title of the video.
     * @param synopsis      the synopsis of the video.
     * @param director      the director of the video.
     * @param cast          the cast of the video.
     * @param yearOfRelease the year the video was released.
     * @param genre         the genre of the video.
     * @param runningTime   the running time of the video.
     * @param content       the content of the video.
     */
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

    /**
     * Default constructor.
     */
    public VideoRequest() {
    }

    // Getter and setter methods for each field

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

    /**
     * Converts a VideoRequest object to a Video entity.
     *
     * @param videoRequest the VideoRequest object.
     * @return a Video entity.
     */
    public static Video toVideo(VideoRequest videoRequest) {
        Video video = new Video();
        video.setTitle(videoRequest.getTitle());
        video.setContent(videoRequest.getContent());

        return video;
    }

    /**
     * Converts a VideoRequest object to a VideoMetaData entity.
     *
     * @param videoRequest the VideoRequest object.
     * @param video        the associated Video entity.
     * @return a VideoMetaData entity.
     */
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

    /**
     * Converts a Video entity to an EngagementStatistics entity.
     *
     * @param video the associated Video entity.
     * @return an EngagementStatistics entity.
     */
    public static EngagementStatistics toEngagementStatistics(Video video) {
        EngagementStatistics engagementStatistics = new EngagementStatistics();
        engagementStatistics.setVideo(video);

        return engagementStatistics;
    }
}
