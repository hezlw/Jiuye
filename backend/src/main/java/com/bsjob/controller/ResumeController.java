package com.bsjob.controller;

import com.bsjob.common.ApiResponse;
import com.bsjob.config.AuthContext;
import com.bsjob.dto.ApplyRequest;
import com.bsjob.entity.Resume;
import com.bsjob.service.ResumeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ApiResponse<Resume> apply(@Validated @RequestBody ApplyRequest request) {
        if (!"user".equals(AuthContext.getRole())) {
            return ApiResponse.fail("只有求职者可以投递简历");
        }
        return ApiResponse.success(resumeService.apply(AuthContext.getUserId(), request));
    }

    @GetMapping("/mine")
    public ApiResponse<List<Resume>> mine() {
        if (!"user".equals(AuthContext.getRole())) {
            return ApiResponse.fail("只有求职者可以查看投递记录");
        }
        return ApiResponse.success(resumeService.listByUser(AuthContext.getUserId()));
    }
}
