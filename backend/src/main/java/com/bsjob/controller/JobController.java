package com.bsjob.controller;

import com.bsjob.common.ApiResponse;
import com.bsjob.config.AuthContext;
import com.bsjob.dto.PublishJobRequest;
import com.bsjob.entity.Job;
import com.bsjob.service.JobService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ApiResponse<Job> publish(@Validated @RequestBody PublishJobRequest request) {
        if (!"company".equals(AuthContext.getRole())) {
            return ApiResponse.fail("只有企业用户可以发布职位");
        }
        return ApiResponse.success(jobService.publish(AuthContext.getUserId(), request));
    }

    @GetMapping
    public ApiResponse<List<Job>> list(@RequestParam(value = "keyword", required = false) String keyword) {
        return ApiResponse.success(jobService.list(keyword));
    }
}
