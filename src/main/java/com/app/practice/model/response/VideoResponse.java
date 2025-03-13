package com.app.practice.model.response;

import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the response structure for video details.
 * It contains the necessary information about the video, its metadata, and engagement statistics.
 * <p>
 * Author: Ruchir Bisht
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
     * Static Method to Map a Video entity to a VideoResponse.
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
