package com.etc.jobsystem.Config;

import com.alibaba.fastjson.JSON;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.enums.AppHttpCodeEnum;
import com.etc.jobsystem.utils.JwtUtil;
import com.etc.jobsystem.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    RedisCache redisCache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入了拦截器");
        //获取请求头中的token
        String token = request.getHeader("token");
        System.out.println(token);
        System.out.println(request.getMethod());
        //这句话无比的重要
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        if (token==null){ //token为空
            ResultResponse(response);
            return false;
        }
        //todo 这里存在一个问题 使用以前的token获取数据后无法正常获取数据
        System.out.println("进入了拦截器企鹅token不为空");
        try{
            Claims claims = JwtUtil.parseJWT(token);
            String  userid  = claims.getSubject();
            System.out.println("userid"+userid);
            Object cacheObject = redisCache.getCacheObject(userid);
            System.out.println(cacheObject);
            if (Objects.isNull(cacheObject)){
                //证明获取到了token但是不在redis中存着
                ResultResponse(response);
            }

        }catch (Exception e){
            //怀疑这段代码
            ResultResponse(response);
//
//            response.sendError(AppHttpCodeEnum.TOKEN_ERRO.getCode(),AppHttpCodeEnum.TOKEN_ERRO.getMsg());
            return false;
        }
//        System.out.println("拦截器通过");
            return true;

    }
    //登录失败response的返回
    private void ResultResponse( HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write( JSON.toJSON(ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN)).toString());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

