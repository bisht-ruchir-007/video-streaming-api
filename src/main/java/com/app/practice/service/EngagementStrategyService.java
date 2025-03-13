package com.app.practice.service;

import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;

/**
 * Interface for defining engagement strategy related services.
 * Provides method to fetch engagement statistics based on the user or entity ID.
 * <p>
 * Author: Ruchir Bisht
 */
public interface EngagementStrategyService {

    /**
     * Fetches the engagement statistics for a given ID.
     *
     * @param id the ID of the user or entity for which engagement stats are to be fetched
     * @return a GenericResponse containing the engagement statistics in the form of EngagementResponse
     */
    GenericResponse<EngagementResponse> getEngagementStats(Long id);
}
