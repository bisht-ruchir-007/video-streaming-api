package com.app.practice.repository;

import com.app.practice.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    // Check if a video with a given title already exists
    boolean existsByTitle(String title);

    // Get all available (non-delisted) videos
    List<Video> findByIsDelistedFalse();
}
