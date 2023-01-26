package com.etc.jobsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.etc.jobsystem.entity.CompanyHr;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.entity.User;

public interface CompanyHrService extends IService<CompanyHr> {
    ResponseResult getAllHr(String ownerId);
}
