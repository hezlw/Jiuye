package com.bsjob.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bsjob.dto.PublishJobRequest;
import com.bsjob.entity.Job;
import com.bsjob.mapper.JobMapper;
import com.bsjob.service.JobService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private final JobMapper jobMapper;

    public JobServiceImpl(JobMapper jobMapper) {
        this.jobMapper = jobMapper;
    }

    @Override
    public Job publish(Long companyId, PublishJobRequest request) {
        Job job = new Job();
        job.setCompanyId(companyId);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setCreatedAt(LocalDateTime.now());
        jobMapper.insert(job);
        return job;
    }

    @Override
    public List<Job> list(String keyword) {
        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Job::getTitle, keyword).or().like(Job::getDescription, keyword);
        }
        wrapper.orderByDesc(Job::getCreatedAt);
        return jobMapper.selectList(wrapper);
    }
}
