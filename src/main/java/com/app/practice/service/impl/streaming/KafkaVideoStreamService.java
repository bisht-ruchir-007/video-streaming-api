package com.app.practice.service.impl.streaming;

import com.app.practice.constants.ModuleConstants;
import com.app.practice.constants.VideoStreamConstants;
import com.app.practice.dto.VideoDTO;
import com.app.practice.entity.Video;
import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.service.VideoStreamService;
import com.app.practice.utils.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaVideoStreamService implements VideoStreamService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaVideoStreamService.class);

    private final KafkaProducerService kafkaProducerService;

    /**
     * Fetches a video from an external microservice (mocked here).
     *
     * @param id the video ID
     * @return the Video entity
     * @throws VideoNotFoundException if not found or delisted
     */
    private Video fetchVideoById(Long id) throws VideoNotFoundException {
        // Simulating fetching from an external microservice
        Video video = kafkaProducerService.requestVideoDetails(id);
        if (video == null) {
            logger.error(ModuleConstants.VIDEO_NOT_FOUND + "{}", id);
            throw new VideoNotFoundException(ModuleConstants.VIDEO_NOT_FOUND);
        }

        if (video.isDelisted()) {
            logger.warn(ModuleConstants.VIDEO_DELISTED + "{}", video.getTitle());
            throw new VideoNotFoundException(ModuleConstants.VIDEO_DELISTED);
        }

        return video;
    }

    @Override
    public GenericResponse<VideoDTO> loadVideo(Long id) throws VideoNotFoundException {
        logger.info(ModuleConstants.LOADING_VIDEO + "{}", id);
        Video video = fetchVideoById(id);

        // Send a Kafka message for video engagement tracking
        kafkaProducerService.sendMessage(VideoStreamConstants.VIDEO_PLAY_TOPIC, video.getVideoId().toString());

        VideoDTO videoDTO = new VideoDTO(video.getVideoId(), video.getTitle(),
                video.getMetaData().getDirector(), video.getMetaData().getCast(),
                video.getMetaData().getGenre(), video.getMetaData().getRunningTime());

        return GenericResponse.success(videoDTO, HttpStatus.OK);
    }

    @Override
    public GenericResponse<String> playVideo(Long id) throws VideoNotFoundException {
        logger.info(ModuleConstants.PLAYING_VIDEO + "{}", id);
        Video video = fetchVideoById(id);

        /*
          Send a Kafka message to track play event.
         */
        kafkaProducerService.sendMessage(VideoStreamConstants.VIDEO_PLAY_TOPIC, video.getVideoId().toString());

        return GenericResponse.success(video.getContent(), HttpStatus.OK);
    }
}

