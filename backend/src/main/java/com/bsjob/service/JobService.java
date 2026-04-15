package com.bsjob.service;

import com.bsjob.dto.PublishJobRequest;
import com.bsjob.entity.Job;

import java.util.List;

public interface JobService {
    Job publish(Long companyId, PublishJobRequest request);

    List<Job> list(String keyword);
}
