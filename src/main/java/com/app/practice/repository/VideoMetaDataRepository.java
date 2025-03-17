package com.app.practice.repository;

import com.app.practice.entity.VideoMetaData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on the VideoMetaData entity.
 * This interface extends JpaRepository for basic CRUD functionality and JpaSpecificationExecutor
 * to support dynamic queries with Specifications.
 * <p>
 * Author: Ruchir Bisht
 */
@Repository
public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData, Long>, JpaSpecificationExecutor<VideoMetaData> {

    /**
     * Custom query to find video metadata by director, ignoring case.
     * <p>
     * This method retrieves a list of VideoMetaData entities where the director matches the given
     * director name, ignoring case. The result is paginated using the Pageable parameter.
     *
     * @param director the name of the director to search for.
     * @param pageable the Pageable object to paginate the results.
     * @return a paginated list of VideoMetaData entities matching the director's name.
     */
    @Query("SELECT v FROM VideoMetaData v WHERE LOWER(v.director) = LOWER(:director)")
    List<VideoMetaData> findByDirectorIgnoreCase(@Param("director") String director, Pageable pageable);

    /*
      Scope : Sort By Methods can also implement
     */
}
