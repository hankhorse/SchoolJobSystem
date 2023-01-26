package com.etc.jobsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
