package com.app.practice.service;

import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.EngagementResponse;

public interface EngagementService {

    EngagementResponse getEngagementStats(Long id) throws VideoNotFoundException;

}
