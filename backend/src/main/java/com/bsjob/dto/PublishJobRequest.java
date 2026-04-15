package com.bsjob.dto;

import jakarta.validation.constraints.NotBlank;

public class PublishJobRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String location;
    private String salary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
