package com.etc.jobsystem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.etc.jobsystem.entity.UserInfo;
import com.etc.jobsystem.mapper.UserInfoMapper;
import com.etc.jobsystem.service.UserInfoService;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper,UserInfo> implements UserInfoService{

}
