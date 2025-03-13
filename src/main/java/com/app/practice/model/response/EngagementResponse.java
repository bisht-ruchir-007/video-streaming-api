package com.app.practice.model.response;

/**
 * Response model for video engagement details.
 * This class is used to structure the response containing video engagement information,
 * such as title, synopsis, director, impressions, and views.
 * <p>
 * Author: Ruchir Bisht
 */
public class EngagementResponse {

    private String title;
    private String synopsis;
    private String director;
    private Long impressions = 0L;
    private Long views = 0L;

    /**
     * Default constructor.
     */
    public EngagementResponse() {
    }

    /**
     * Constructor to create an EngagementResponse object with all fields.
     *
     * @param title       the title of the video.
     * @param synopsis    the synopsis of the video.
     * @param director    the director of the video.
     * @param impressions the number of impressions for the video.
     * @param views       the number of views for the video.
     */
    public EngagementResponse(String title, String synopsis, String director, Long impressions, Long views) {
        this.title = title;
        this.synopsis = synopsis;
        this.director = director;
        this.impressions = impressions;
        this.views = views;
    }

    /**
     * Constructor to create an EngagementResponse object with only views and impressions.
     *
     * @param views       the number of views for the video.
     * @param impressions the number of impressions for the video.
     */
    public EngagementResponse(Long views, Long impressions) {
        this.views = views;
        this.impressions = impressions;
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
}
