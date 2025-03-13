package com.app.practice.service;

import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.GenericResponse;

public interface VideoStreamService {

    /**
     * Loads the video details based on the provided video ID.
     * Throws an exception if the video is not found.
     *
     * @param id the ID of the video to be loaded
     * @return a GenericResponse containing the video details
     * @throws VideoNotFoundException if the video with the given ID is not found
     */
    GenericResponse<VideoDTO> loadVideo(Long id) throws VideoNotFoundException;

    /**
     * Plays the video based on the provided video ID.
     * Throws an exception if the video is not found.
     *
     * @param id the ID of the video to be played
     * @return a GenericResponse containing a message indicating the video is being played
     * @throws VideoNotFoundException if the video with the given ID is not found
     */
    GenericResponse<String> playVideo(Long id) throws VideoNotFoundException;

}
