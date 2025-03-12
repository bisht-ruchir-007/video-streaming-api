package com.app.practice.model.response;

public class EngagementResponse {

    private String title;
    private String synopsis;
    private String director;
    private Long impressions = 0L;
    private Long views = 0L;

    public EngagementResponse() {
    }

    public EngagementResponse(String title, String synopsis, String director, Long impressions, Long views) {
        this.title = title;
        this.synopsis = synopsis;
        this.director = director;
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
