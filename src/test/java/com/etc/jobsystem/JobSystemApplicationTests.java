package com.etc.jobsystem;

import com.alibaba.fastjson.JSON;
import com.etc.jobsystem.entity.Company;
import com.etc.jobsystem.entity.Factory.Msg.RequireAddCompanyMsg;
import com.etc.jobsystem.entity.Notice;
import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.enums.notice.ActionTypeEnum;
import com.etc.jobsystem.service.CompanyService;
import com.etc.jobsystem.service.NoticeService;
import com.etc.jobsystem.service.UserService;
import com.etc.jobsystem.utils.JwtUtil;
import com.etc.jobsystem.utils.NoticeUtils;
import com.etc.jobsystem.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class JobSystemApplicationTests {

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserService userService;
    @Autowired
    CompanyService  companyService;
    @Autowired
    RequireAddCompanyMsg requireAddCompanyMsg;
    @Resource
    NoticeService noticeService;
    @Test
    void contextLoads() {
    }

    @Test
    void TestRedis() throws Exception {
        //登录 存token
        User user =userService.getById(1);
        String token = JwtUtil.createJWT(user.getUserId());
        System.out.println("login:"+token);
        redisCache.setCacheObject("login:"+"1",user);
        String subject=null;
        //使用  取token
        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3ODYwNDEyYzFhOTY0NTE4YWM2YmNjMGYzYjE4MmI4OCIsInN1YiI6IjEiLCJpc3MiOiJlbCIsImlhdCI6MTY2NzQ4MTE2NywiZXhwIjoxNjY3NDg0NzY3fQ.L8aNEQB4Pu29JXfjEWdHlTHf1FnMQlbHQgqh_-fMl64");
        subject = claims.getSubject();
//        try{
//            Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3ODYwNDEyYzFhOTY0NTE4YWM2YmNjMGYzYjE4MmI4OCIsInN1YiI6IjEiLCJpc3MiOiJlbCIsImlhdCI6MTY2NzQ4MTE2NywiZXhwIjoxNjY3NDg0NzY3fQ.L8aNEQB4Pu29JXfjEWdHlTHf1FnMQlbHQgqh_-fMl64");
//             subject = claims.getSubject();
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException("token非法");
//        }

        User cacheObject = (User) redisCache.getCacheObject("login :" + subject);
//eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZTJiNTFjYWE4ZTg0NGY5OTc3NjkzMWZiODVlMTQ2MSIsInN1YiI6IjEiLCJpc3MiOiJlbCIsImlhdCI6MTY2NzQ3OTgwNiwiZXhwIjoxNjY3NDgzNDA2fQ.gNmq6sqi26A3D3cQYVu9wPi84QIfFa5gNpMmWbfuUKw
        System.out.println(cacheObject);
    }

    @Test
    void testNotice(){
        User sender = JSON.parseObject("", User.class);
        System.out.println(sender.getUserId());
    }

    @Test
    void testsend(){
        User user = userService.getById(2);
        Company company = companyService.getById(2);
        company.setCompanyId(null);
        //发送一条消息只需要获取发送者 接收者  内容 的 对象 和操作类型 再调用想生成的消息的方法即可获得一个消息实体
        Notice firstNotice = NoticeUtils.getNoticeFromJavaBeanToString(user, null, company, ActionTypeEnum.ACTION_TYPE.getCode());
        Notice notice = requireAddCompanyMsg.getNotice(firstNotice);
        noticeService.save(notice);
        System.out.println(notice);

    }

}
