package com.app.practice.model.response;

public class EngagementResponse {

    private String title;
    private String synopsis;
    private String director;
    private int impressions = 0;
    private int views = 0;

    public EngagementResponse() {
    }

    public EngagementResponse(String title, String synopsis, String director, int impressions, int views) {
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
}
