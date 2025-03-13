package com.app.practice.service.impl;

import com.app.practice.constants.ModuleConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.repository.EngagementStatisticsRepository;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.impl.streaming.DBVideoStreamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DBVideoStreamServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private EngagementStatisticsRepository engagementStatsRepo;

    @InjectMocks
    private DBVideoStreamService videoStreamService;

    private Video video;
    private EngagementStatistics engagementStatistics;
    private VideoMetaData metaData;

    @BeforeEach
    void setUp() {
        engagementStatistics = new EngagementStatistics();
        engagementStatistics.setViews(10L);
        engagementStatistics.setImpressions(5L);

        metaData = new VideoMetaData();
        metaData.setDirector("Test Director");
        metaData.setCast("Actor1, Actor2");
        metaData.setGenre("Action");
        metaData.setRunningTime(120);

        video = new Video();
        video.setVideoId(1L);
        video.setTitle("Test Video");
        video.setEngagementStatistics(engagementStatistics);
        video.setMetaData(metaData);
        video.setContent("Test Content");
        video.setDelisted(false);
    }

    @Test
    void testLoadVideo_Success() throws VideoNotFoundException {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        GenericResponse<VideoDTO> response = videoStreamService.loadVideo(1L);

        verify(videoRepository, times(1)).findById(1L);
        verify(engagementStatsRepo, times(1)).save(any(EngagementStatistics.class));

        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertEquals("Test Video", response.getData().getTitle());
        assertEquals("Test Director", response.getData().getDirector());
    }

    @Test
    void testLoadVideo_VideoNotFound() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        VideoNotFoundException thrown = assertThrows(VideoNotFoundException.class, () -> {
            videoStreamService.loadVideo(1L);
        });

        assertEquals(ModuleConstants.VIDEO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void testLoadVideo_VideoDelisted() {
        video.setDelisted(true);
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        VideoNotFoundException thrown = assertThrows(VideoNotFoundException.class, () -> {
            videoStreamService.loadVideo(1L);
        });

        assertEquals(ModuleConstants.VIDEO_DELISTED, thrown.getMessage());
    }

    @Test
    void testPlayVideo_Success() throws VideoNotFoundException {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        GenericResponse<String> response = videoStreamService.playVideo(1L);

        verify(videoRepository, times(1)).findById(1L);
        verify(engagementStatsRepo, times(1)).save(any(EngagementStatistics.class));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Test Content", response.getData());
    }

    @Test
    void testPlayVideo_VideoNotFound() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        VideoNotFoundException thrown = assertThrows(VideoNotFoundException.class, () -> {
            videoStreamService.playVideo(1L);
        });

        assertEquals(ModuleConstants.VIDEO_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void testPlayVideo_VideoDelisted() {
        video.setDelisted(true);
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            videoStreamService.playVideo(1L);
        });

        assertEquals("com.app.practice.exception.VideoNotFoundException: Video is delisted.", thrown.getMessage());
    }
}
