package com.app.practice.repository;

import com.app.practice.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    // check if a video with the given title exists
    @Query("SELECT COUNT(v) > 0 FROM Video v WHERE v.title = :title")
    boolean existsByTitle(@Param("title") String title);

    // get all non-delisted videos
    @Query("SELECT v FROM Video v WHERE v.isDelisted = FALSE")
    List<Video> findByIsDelistedFalse();
}
