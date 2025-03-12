package com.app.practice.utils;

import com.app.practice.entity.VideoMetaData;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.app.practice.entity.VideoMetaData;
import org.springframework.data.jpa.domain.Specification;

public class VideoMetaDataSpecification {

    public static Specification<VideoMetaData> searchByKeyword(String searchPhrase) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + searchPhrase.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("director")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("genre")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("cast")), likePattern)
            );
        };
    }
}
