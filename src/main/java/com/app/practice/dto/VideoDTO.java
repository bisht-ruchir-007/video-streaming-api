package com.app.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Ruchir Bisht
 * VideoDTO is a Data Transfer Object (DTO) that represents a video entity.
 * It contains various properties related to a video such as ID, title, director, cast, genre, and running time.
 * This class is used to transfer video data between layers of the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {
    private Long id;
    private String title;
    private String director;
    private String cast;
    private String genre;
    private int runningTime;

}
