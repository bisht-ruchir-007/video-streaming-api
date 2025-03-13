package com.app.practice.service.impl;

import com.app.practice.constants.ModuleConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.repository.VideoMetaDataRepository;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.impl.video.VideoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceImplTest {

    @InjectMocks
    private VideoServiceImpl videoService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMetaDataRepository videoMetaDataRepository;

    private VideoRequest videoRequest;
    private Video video;
    private VideoMetaData videoMetaData;

    @BeforeEach
    void setUp() {
        videoRequest = new VideoRequest();
        videoRequest.setTitle("Test Video");
        videoRequest.setDirector("John Doe");
        videoRequest.setGenre("Action");
        videoRequest.setCast("Actor A, Actor B");
        videoRequest.setYearOfRelease(2024);
        videoRequest.setRunningTime(120);

        video = new Video();
        video.setVideoId(1L);
        video.setTitle("Test Video");
        video.setDelisted(false);

        videoMetaData = new VideoMetaData();
        videoMetaData.setVideo(video);
        videoMetaData.setDirector("John Doe");
        videoMetaData.setGenre("Action");

        video.setMetaData(videoMetaData);
        video.setEngagementStatistics(new EngagementStatistics());
    }

    /**
     * Test for successfully publishing a video.
     */
    @Test
    void testPublishVideo_Success() throws VideoAlreadyPresentException {
        when(videoRepository.existsByTitle(videoRequest.getTitle())).thenReturn(false);
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        GenericResponse<VideoResponse> response = videoService.publishVideo(videoRequest);

        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertEquals("Test Video", response.getData().getTitle());

        verify(videoRepository, times(1)).save(any(Video.class));
    }

    /**
     * Test for VideoAlreadyPresentException when trying to publish a duplicate video.
     */
    @Test
    void testPublishVideo_VideoAlreadyExists() {
        when(videoRepository.existsByTitle(videoRequest.getTitle())).thenReturn(true);

        assertThrows(VideoAlreadyPresentException.class, () -> videoService.publishVideo(videoRequest));

        verify(videoRepository, never()).save(any(Video.class));
    }

    /**
     * Test for successfully editing an existing video.
     */
    @Test
    void testEditVideo_Success() throws VideoNotFoundException {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        GenericResponse<VideoResponse> response = videoService.editVideo(1L, videoRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Test Video", response.getData().getTitle());

        verify(videoRepository, times(1)).save(any(Video.class));
    }

    /**
     * Test for VideoNotFoundException when editing a non-existing video.
     */
    @Test
    void testEditVideo_VideoNotFound() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> videoService.editVideo(1L, videoRequest));

        verify(videoRepository, never()).save(any(Video.class));
    }

    /**
     * Test for successfully delisting a video.
     */
    @Test
    void testDelistVideo_Success() throws VideoNotFoundException {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        GenericResponse<String> response = videoService.delistVideo(1L);

        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertEquals(ModuleConstants.VIDEO_DELISTED_SUCCESSFULLY, response.getData());

        verify(videoRepository, times(1)).save(video);
    }

    /**
     * Test for VideoNotFoundException when trying to delist a non-existing video.
     */
    @Test
    void testDelistVideo_VideoNotFound() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> videoService.delistVideo(1L));

        verify(videoRepository, never()).save(any(Video.class));
    }

    /**
     * Test for listing all videos with pagination.
     */
    @Test
    void testListAllVideos() {
        when(videoRepository.findByIsDelistedFalse(any())).thenReturn((Page<Video>) List.of(video));

        GenericResponse<List<VideoDTO>> response = videoService.listAllVideos(0, 5);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertFalse(response.getData().isEmpty());

        verify(videoRepository, times(1)).findByIsDelistedFalse(any());
    }

    /**
     * Test for searching videos by director's name.
     */
    @Test
    void testSearchVideos() {
        when(videoMetaDataRepository.findByDirectorIgnoreCase(eq("John Doe"), any())).thenReturn(List.of(videoMetaData));

        GenericResponse<List<VideoDTO>> response = videoService.searchVideos("John Doe", 0, 5);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getData().isEmpty());

        verify(videoMetaDataRepository, times(1)).findByDirectorIgnoreCase(eq("John Doe"), any());
    }

    /**
     * Test for handling empty search input.
     */
    @Test
    void testSearchVideos_EmptyDirector() {
        GenericResponse<List<VideoDTO>> response = videoService.searchVideos("", 0, 5);

        assertNotNull(response);
        assertEquals("error", response.getStatus());

        verify(videoMetaDataRepository, never()).findByDirectorIgnoreCase(any(), any());
    }
}
