package com.etc.jobsystem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.etc.jobsystem.entity.Send;
import com.etc.jobsystem.mapper.SendMapper;
import com.etc.jobsystem.service.SendService;
import org.springframework.stereotype.Service;


@Service
public class SendServiceImpl extends ServiceImpl<SendMapper,Send> implements SendService{

}
