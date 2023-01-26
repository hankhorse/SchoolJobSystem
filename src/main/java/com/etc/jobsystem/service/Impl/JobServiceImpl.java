package com.etc.jobsystem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.etc.jobsystem.entity.Job;
import com.etc.jobsystem.mapper.JobMapper;
import com.etc.jobsystem.service.JobService;
import org.springframework.stereotype.Service;


@Service
public class JobServiceImpl extends ServiceImpl<JobMapper,Job> implements JobService{

}
