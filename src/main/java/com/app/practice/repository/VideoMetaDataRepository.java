package com.app.practice.repository;

import com.app.practice.entity.VideoMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData, Long> {

    // Search for videos by director (case-insensitive)
    List<VideoMetaData> findByDirectorIgnoreCase(String director);
}
