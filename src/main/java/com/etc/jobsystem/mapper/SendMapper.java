package com.etc.jobsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.etc.jobsystem.entity.Send;
import com.etc.jobsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SendMapper extends BaseMapper<Send> {
}
