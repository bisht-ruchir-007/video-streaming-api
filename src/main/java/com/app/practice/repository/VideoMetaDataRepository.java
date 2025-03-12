package com.app.practice.repository;

import com.app.practice.entity.VideoMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData, Long> {

    // search for videos by director (case-insensitive)
    @Query("SELECT v FROM VideoMetaData v WHERE LOWER(v.director) = LOWER(:director)")
    List<VideoMetaData> findByDirectorIgnoreCase(@Param("director") String director);
}
