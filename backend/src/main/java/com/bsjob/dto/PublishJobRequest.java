package com.bsjob.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PublishJobRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String location;
    private String salary;
}
