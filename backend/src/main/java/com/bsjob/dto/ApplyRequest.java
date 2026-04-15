package com.bsjob.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ApplyRequest {
    @NotNull
    private Long jobId;
    @NotBlank
    private String content;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
