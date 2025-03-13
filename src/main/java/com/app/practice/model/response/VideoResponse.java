package com.app.practice.model.response;

import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;

/**
 * This class represents the response structure for video details.
 * It contains the necessary information about the video, its metadata, and engagement statistics.
 * <p>
 * Author: Ruchir Bisht
 */
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
    private Long impressions = 0L;
    private Long views = 0L;

    /**
     * Default constructor.
     */
    public VideoResponse() {
    }

    /**
     * Constructor to create a VideoResponse with all necessary fields.
     *
     * @param title         the title of the video.
     * @param synopsis      the synopsis of the video.
     * @param director      the director of the video.
     * @param cast          the cast of the video.
     * @param yearOfRelease the year the video was released.
     * @param genre         the genre of the video.
     * @param runningTime   the running time of the video.
     * @param content       the mock content of the video.
     * @param isDelisted    whether the video is delisted.
     * @param impressions   the number of impressions the video has received.
     * @param views         the number of views the video has received.
     */
    public VideoResponse(String title, String synopsis, String director, String cast,
                         int yearOfRelease, String genre, int runningTime, String content,
                         boolean isDelisted, Long impressions, Long views) {
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

    // Getter and Setter methods for each field

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

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    /**
     * Maps a Video entity to a VideoResponse.
     *
     * @param video the Video entity to map.
     * @return a VideoResponse object containing the mapped details.
     */
    public static VideoResponse videoMapper(Video video) {
        VideoMetaData videoMetaData = video.getMetaData();
        EngagementStatistics engagementStatistics = video.getEngagementStatistics();

        return new VideoResponse(
                video.getTitle(),
                videoMetaData.getSynopsis(),
                videoMetaData.getDirector(),
                videoMetaData.getCast(),
                videoMetaData.getYearOfRelease(),
                videoMetaData.getGenre(),
                videoMetaData.getRunningTime(),
                video.getContent(),
                video.isDelisted(),
                engagementStatistics.getImpressions(),
                engagementStatistics.getViews()
        );
    }
}
