package com.app.practice.service;

import com.app.practice.dto.VideoDTO;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.model.response.VideoResponse;

import java.util.List;
import java.util.Optional;

public interface VideoService {

    GenericResponse<VideoResponse> publishVideo(VideoRequest video) throws VideoAlreadyPresentException;

    GenericResponse<VideoResponse> editVideo(Long id, VideoRequest video) throws VideoNotFoundException;

    GenericResponse<String> delistVideo(Long id) throws VideoNotFoundException;

    GenericResponse<VideoDTO> loadVideo(Long id) throws VideoNotFoundException;

    GenericResponse<String> playVideo(Long id) throws VideoNotFoundException;

    GenericResponse<List<VideoDTO>> listAllVideos(int page, int size);  // Pagination added

    GenericResponse<List<VideoDTO>> searchVideos(String director, int page, int size);  // Pagination added

    GenericResponse<List<VideoDTO>> searchVideosBasedOnSearchPhrase(String searchPhrase, int page, int size);  // Pagination added

}
