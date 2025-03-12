package com.app.practice.service;


import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.Video;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.VideoResponse;

import java.util.List;
import java.util.Optional;

public interface VideoService {
    VideoResponse publishVideo(VideoRequest video) throws VideoAlreadyPresentException;

    VideoResponse editVideo(Long id, VideoRequest video) throws VideoNotFoundException;

    void delistVideo(Long id) throws VideoNotFoundException;

    Optional<VideoDTO> loadVideo(Long id) throws VideoNotFoundException;

    String playVideo(Long id) throws VideoNotFoundException;

    List<VideoDTO> listAllVideos();

    List<VideoDTO> searchVideos(String director);

    EngagementResponse getEngagementStats(Long id) throws VideoNotFoundException;
}
