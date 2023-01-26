package com.etc.jobsystem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.etc.jobsystem.VO.LoginVO;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.enums.AppHttpCodeEnum;
import com.etc.jobsystem.service.UserService;
import com.etc.jobsystem.mapper.UserMapper;
import com.etc.jobsystem.utils.JwtUtil;
import com.etc.jobsystem.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@Slf4j(topic = "[user]:")
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{

    @Autowired
    RedisCache redisCache;
    @Override
    public ResponseResult login(String userName, String userPassword) {

        log.info(userName+userPassword);
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUserName,userName)
                 .eq(User::getUserPassword,userPassword);
        //登录
        User user = baseMapper.selectOne(userWrapper);
        //        log.info(String.valueOf(user));
        String token="";
        if (Objects.isNull(user)){
            return  ResponseResult.errorResult(AppHttpCodeEnum.LOSS_LOGIN);
        }else {
            //设置jwt
            token = JwtUtil.createJWT(user.getUserId());
            //登录成功存入redis，
            redisCache.setCacheObject(String.valueOf(user.getUserId()),user);
            LoginVO loginVO = new LoginVO(token,user);
            return ResponseResult.okResult(loginVO,"用户登录成功");
        }


    }
}
