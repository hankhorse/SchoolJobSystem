package com.etc.jobsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.entity.User;

public interface UserService extends IService<User> {
    ResponseResult login(String userName, String userPassword);
}
