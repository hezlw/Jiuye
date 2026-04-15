package com.bsjob.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ApplyRequest {
    @NotNull
    private Long jobId;
    @NotBlank
    private String content;
}
