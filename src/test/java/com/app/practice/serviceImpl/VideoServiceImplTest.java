package com.app.practice.serviceImpl;

import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.EngagementStatistics;
import com.app.practice.entity.Video;
import com.app.practice.entity.VideoMetaData;
import com.app.practice.exception.VideoAlreadyPresentException;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.request.VideoRequest;
import com.app.practice.model.response.VideoResponse;
import com.app.practice.repository.EngagementStatisticsRepository;
import com.app.practice.repository.VideoMetaDataRepository;
import com.app.practice.repository.VideoRepository;
import com.app.practice.service.impl.VideoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceImplTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMetaDataRepository videoMetaDataRepository;

    @Mock
    private EngagementStatisticsRepository engagementStatsRepo;

    @InjectMocks
    private VideoServiceImpl videoService;

    private Video video;
    private VideoRequest videoRequest;
    private VideoMetaData metaData;
    private EngagementStatistics engagementStats;

    @BeforeEach
    void setUp() {
        videoRequest = new VideoRequest();
        videoRequest.setTitle("Test Video");
        videoRequest.setDirector("John Doe");
        videoRequest.setGenre("Action");

        video = new Video();
        video.setVideoId(1L);
        video.setTitle("Test Video");

        metaData = new VideoMetaData();
        metaData.setDirector("John Doe");
        metaData.setGenre("Action");
        metaData.setVideo(video);

        engagementStats = new EngagementStatistics();
        engagementStats.setViews(100L);
        engagementStats.setVideo(video);

        video.setMetaData(metaData);
        video.setEngagementStatistics(engagementStats);
    }

    @Test
    void shouldPublishVideoSuccessfully() throws VideoAlreadyPresentException {
        when(videoRepository.existsByTitle(videoRequest.getTitle())).thenReturn(false);
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        VideoResponse response = videoService.publishVideo(videoRequest);

        assertNotNull(response);
        assertEquals("Test Video", response.getTitle());
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    @Test
    void shouldThrowExceptionWhenVideoAlreadyExists() {
        when(videoRepository.existsByTitle(videoRequest.getTitle())).thenReturn(true);

        assertThrows(VideoAlreadyPresentException.class, () -> videoService.publishVideo(videoRequest));
        verify(videoRepository, never()).save(any(Video.class));
    }

    @Test
    void shouldEditVideoSuccessfully() throws VideoNotFoundException {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoMetaDataRepository.save(any(VideoMetaData.class))).thenReturn(metaData);
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        VideoResponse response = videoService.editVideo(1L, videoRequest);

        assertNotNull(response);
        assertEquals("Test Video", response.getTitle());
        assertEquals("John Doe", response.getDirector());
    }

    @Test
    void shouldThrowExceptionWhenEditingNonExistentVideo() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> videoService.editVideo(1L, videoRequest));
    }

    @Test
    void shouldDelistVideoSuccessfully() throws VideoNotFoundException {
        video.setDelisted(false);
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        videoService.delistVideo(1L);

        assertTrue(video.isDelisted());
        verify(videoRepository, times(1)).save(video);
    }

    @Test
    void shouldThrowExceptionWhenDelistingNonExistentVideo() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> videoService.delistVideo(1L));
    }

    @Test
    void shouldLoadVideoSuccessfully() throws VideoNotFoundException {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(engagementStatsRepo.save(any(EngagementStatistics.class))).thenReturn(engagementStats);

        Optional<VideoDTO> result = videoService.loadVideo(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Video", result.get().getTitle());
    }

    @Test
    void shouldThrowExceptionWhenLoadingNonExistentVideo() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> videoService.loadVideo(1L));
    }

    @Test
    void shouldPlayVideoSuccessfully() throws VideoNotFoundException {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(engagementStatsRepo.save(any(EngagementStatistics.class))).thenReturn(engagementStats);

        String result = videoService.playVideo(1L);

        assertEquals(video.getContent(), result);
    }

    @Test
    void shouldThrowExceptionWhenPlayingNonExistentVideo() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> videoService.playVideo(1L));
    }

    @Test
    void shouldListAllVideos() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Video> videoPage = new PageImpl<>(List.of(video));
        when(videoRepository.findByIsDelistedFalse(pageable)).thenReturn(videoPage);

        List<VideoDTO> result = videoService.listAllVideos(0, 5);

        assertEquals(1, result.size());
        assertEquals("Test Video", result.get(0).getTitle());
    }

    @Test
    void shouldSearchVideosByDirector() {
        Pageable pageable = PageRequest.of(0, 5);
        when(videoMetaDataRepository.findByDirectorIgnoreCase("John Doe", pageable)).thenReturn(List.of(metaData));

        List<VideoDTO> result = videoService.searchVideos("John Doe", 0, 5);

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getDirector());
    }
}
