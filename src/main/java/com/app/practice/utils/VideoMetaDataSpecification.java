package com.app.practice.utils;

import com.app.practice.constants.QueryParams;
import com.app.practice.entity.VideoMetaData;
import org.springframework.data.jpa.domain.Specification;


/**
 * Utility class to define custom search specifications for VideoMetaData.
 * This class provides a method to create a Specification that can be used for filtering
 * VideoMetaData entities based on a search phrase. The search is performed across
 * multiple fields like director, genre, and cast.
 * Author : Ruchir Bisht
 */
public class VideoMetaDataSpecification {

    /**
     * Creates a Specification to search for a keyword across director, genre, and cast fields.
     * This method returns a Specification that performs a case-insensitive search with the given search phrase.
     * It constructs a LIKE query using the search phrase for the specified fields.
     *
     * @param searchPhrase the search keyword that will be searched for in the director, genre, and cast fields
     * @return Specification<VideoMetaData> the specification for filtering VideoMetaData entities
     */
    public static Specification<VideoMetaData> searchByKeyword(String searchPhrase) {
        return (root, query, criteriaBuilder) -> {
            // Prepare the search phrase with wildcards for SQL LIKE query and convert to lowercase for case-insensitive search
            String likePattern = "%" + searchPhrase.toLowerCase() + "%";

            // Return an 'OR' condition to search for the keyword in director, genre, or cast fields
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(QueryParams.DIRECTOR.toString())), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(QueryParams.GENRE.toString())), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(QueryParams.CAST.toString())), likePattern)
            );
        };
    }
}

