package com.app.practice.service;

import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.model.response.VideoResponse;

import java.util.List;

/**
 * Service interface for managing video operations including publishing, editing,
 * deleting, and listing videos. It also supports video search with pagination.
 * <p>
 * Author: Ruchir Bisht
 */
public interface VideoService {

    /**
     * Publishes a new video after validating the provided video details.
     * Throws an exception if the video is already present.
     *
     * @param video the video details to be published
     * @return a GenericResponse containing the response of video publication
     * @throws VideoAlreadyPresentException if the video is already present in the database
     */
    GenericResponse<VideoResponse> publishVideo(VideoRequest video) throws VideoAlreadyPresentException;

    /**
     * Edits an existing video based on the provided video ID and updated details.
     * Throws an exception if the video is not found.
     *
     * @param id    the ID of the video to be edited
     * @param video the updated video details
     * @return a GenericResponse containing the updated video response
     * @throws VideoNotFoundException if the video with the given ID is not found
     */
    GenericResponse<VideoResponse> editVideo(Long id, VideoRequest video) throws VideoNotFoundException;

    /**
     * Delists a video, marking it as unavailable. Throws an exception if the video is not found.
     *
     * @param id the ID of the video to be delisted
     * @return a GenericResponse containing a message indicating the success of delisting
     * @throws VideoNotFoundException if the video with the given ID is not found
     */
    GenericResponse<String> delistVideo(Long id) throws VideoNotFoundException;

    /**
     * Lists all videos with pagination support.
     *
     * @param page the page number to retrieve
     * @param size the number of videos per page
     * @return a GenericResponse containing the list of videos in DTO form
     */
    GenericResponse<List<VideoDTO>> listAllVideos(int page, int size);  // Pagination added

    /**
     * Searches for videos based on the director's name with pagination support.
     *
     * @param director the name of the director to search for
     * @param page     the page number to retrieve
     * @param size     the number of videos per page
     * @return a GenericResponse containing the list of videos filtered by director
     */
    GenericResponse<List<VideoDTO>> searchVideos(String director, int page, int size);  // Pagination added

    /**
     * Searches for videos based on a search phrase (can be title, director, etc.) with pagination support.
     *
     * @param searchPhrase the phrase to search for in video details
     * @param page         the page number to retrieve
     * @param size         the number of videos per page
     * @return a GenericResponse containing the list of videos filtered by search phrase
     */
    GenericResponse<List<VideoDTO>> searchVideosBasedOnSearchPhrase(String searchPhrase, int page, int size);  // Pagination added
}
