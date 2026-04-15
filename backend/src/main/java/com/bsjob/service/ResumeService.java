package com.bsjob.service;

import com.bsjob.dto.ApplyRequest;
import com.bsjob.entity.Resume;

import java.util.List;

public interface ResumeService {
    Resume apply(Long userId, ApplyRequest request);

    List<Resume> listByUser(Long userId);
}
