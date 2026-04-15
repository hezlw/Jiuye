package com.bsjob.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bsjob.dto.ApplyRequest;
import com.bsjob.entity.Resume;
import com.bsjob.mapper.ResumeMapper;
import com.bsjob.service.ResumeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final ResumeMapper resumeMapper;

    public ResumeServiceImpl(ResumeMapper resumeMapper) {
        this.resumeMapper = resumeMapper;
    }

    @Override
    public Resume apply(Long userId, ApplyRequest request) {
        Resume existed = resumeMapper.selectOne(new LambdaQueryWrapper<Resume>()
                .eq(Resume::getUserId, userId)
                .eq(Resume::getJobId, request.getJobId()));
        if (existed != null) {
            throw new RuntimeException("该职位已投递，请勿重复投递");
        }
        Resume resume = new Resume();
        resume.setUserId(userId);
        resume.setJobId(request.getJobId());
        resume.setContent(request.getContent());
        resume.setStatus("submitted");
        resume.setCreatedAt(LocalDateTime.now());
        resumeMapper.insert(resume);
        return resume;
    }

    @Override
    public List<Resume> listByUser(Long userId) {
        return resumeMapper.selectList(new LambdaQueryWrapper<Resume>()
                .eq(Resume::getUserId, userId)
                .orderByDesc(Resume::getCreatedAt));
    }
}
