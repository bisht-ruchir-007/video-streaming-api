package com.app.practice.service.impl;


import com.app.practice.constants.ModuleConstants;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.impl.engagement.DBEngagementStrategyServiceImpl;
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
class DBEngagementStrategyServiceImplTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private DBEngagementStrategyServiceImpl engagementService;

    private Video video;
    private EngagementStatistics engagementStatistics;

    @BeforeEach
    void setUp() {
        engagementStatistics = new EngagementStatistics();
        engagementStatistics.setImpressions(100L);
        engagementStatistics.setViews(50L);

        video = new Video();
        video.setVideoId(1L);
        video.setTitle("Test Video");
        video.setEngagementStatistics(engagementStatistics);

        // Assuming Metadata is part of the Video entity
        VideoMetaData metaData = new VideoMetaData();
        metaData.setSynopsis("Test Synopsis");
        metaData.setDirector("Test Director");
        video.setMetaData(metaData);
    }

    @Test
    void testGetEngagementStats_Success() {
        // Mock repository response
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        // Call the method
        GenericResponse<EngagementResponse> response = engagementService.getEngagementStats(1L);

        // Verify interactions
        verify(videoRepository, times(1)).findById(1L);

        // Validate response
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        assertEquals("Test Video", response.getData().getTitle());
        assertEquals(100, response.getData().getImpressions());
        assertEquals(50, response.getData().getViews());
    }

    @Test
    void testGetEngagementStats_VideoNotFound() {
        // Mock repository response
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method
        GenericResponse<EngagementResponse> response = engagementService.getEngagementStats(1L);

        // Verify interactions
        verify(videoRepository, times(1)).findById(1L);

        // Validate response
        assertNotNull(response);
        assertEquals("error", response.getStatus());
        assertEquals(ModuleConstants.VIDEO_NOT_FOUND, response.getError());
    }

    @Test
    void testGetEngagementStats_InternalServerError() {
        // Mock repository to throw an exception
        when(videoRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        // Call the method
        GenericResponse<EngagementResponse> response = engagementService.getEngagementStats(1L);

        // Verify interactions
        verify(videoRepository, times(1)).findById(1L);

        // Validate response
        assertNotNull(response);
        assertEquals("error", response.getStatus());
        assertEquals("Internal Server Error", response.getError());
    }
}
