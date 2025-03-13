package com.app.practice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response model for video engagement details.
 * This class is used to structure the response containing video engagement information,
 * such as title, synopsis, director, impressions, and views.
 * <p>
 * Author: Ruchir Bisht
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngagementResponse {

    private String title;
    private String synopsis;
    private String director;
    private Long impressions = 0L;
    private Long views = 0L;

}
