package com.app.practice.service;

import com.app.practice.exception.VideoNotFoundException;
import com.app.practice.model.response.EngagementResponse;
import com.app.practice.model.response.GenericResponse;

public interface EngagementService {

    GenericResponse<EngagementResponse> getEngagementStats(Long id) throws VideoNotFoundException;

}
