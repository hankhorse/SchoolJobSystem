package com.etc.jobsystem.resolve;

import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.service.UserService;
import com.etc.jobsystem.utils.JwtUtil;
import com.etc.jobsystem.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    RedisCache redisCache;

    @Resource
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
       //获取token
        String token = webRequest.getHeader("token");
        System.out.println("解析器中的token为"+token);
        //这里不用判断token是否为空 前面判断过
        //解析token
        Claims claims = JwtUtil.parseJWT(token);
        String subject = claims.getSubject();
        System.out.println("解析器中的redisCache为"+redisCache);
        //要不要修改成直接获取
//        User user = (User)redisCache.getCacheObject(subject);
        User user=userService.getById(Integer.valueOf(subject));
        //获取user对象
        if (!Objects.isNull(user)){
            //能进到这里说明  有token但是过期了 redis中取不到值
            return user;
        }
        System.out.println("在解析器 redis获取的过程中 user为null");
        return null;
    }
}
