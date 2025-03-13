package com.app.practice.service;

import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;

public interface EngagementStrategyService {

    GenericResponse<EngagementResponse> getEngagementStats(Long id);
}

