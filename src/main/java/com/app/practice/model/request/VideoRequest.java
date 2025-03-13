package com.app.practice.model.request;

import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for handling video-related requests. This class is used to pass the video details,
 * such as title, synopsis, director, etc., when creating or updating a video.
 * <p>
 * Author: Ruchir Bisht
 * <p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
