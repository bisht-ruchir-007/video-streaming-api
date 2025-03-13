package com.app.practice.repository;

import com.app.practice.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on the Video entity.
 * This repository provides custom queries to check if a video exists by title
 * and to fetch non-delisted videos with pagination.
 * <p>
 * Author: Ruchir Bisht
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    /**
     * Custom query to check if a video with the given title exists in the database.
     * <p>
     * This method uses a custom JPQL query to count the number of videos with the
     * provided title and returns true if any video exists, otherwise false.
     *
     * @param title the title of the video to check for.
     * @return true if a video with the given title exists, otherwise false.
     */
    @Query("SELECT COUNT(v) > 0 FROM Video v WHERE v.title = :title")
    boolean existsByTitle(@Param("title") String title);

    /**
     * Custom query to fetch all non-delisted videos with pagination.
     * <p>
     * This method retrieves a paginated list of Video entities where the
     * `isDelisted` flag is set to false, indicating the video is not delisted.
     *
     * @param pageable the Pageable object to paginate the results.
     * @return a paginated list of non-delisted videos.
     */
    @Query("SELECT v FROM Video v WHERE v.isDelisted = FALSE")
    Page<Video> findByIsDelistedFalse(Pageable pageable);
}
