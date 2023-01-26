package com.etc.jobsystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //一定要加上这个注解，成为Springboot的配置类，不然不会生效
public class WebMvcConfiguration implements WebMvcConfigurer {
    //以这种方式将拦截器注入为一个bean，可以防止拦截器中无法注入bean的问题出现
    @Bean
    public MyInterceptor getMyInterceptor(){
        return new MyInterceptor();
    }

    @Override   //拦截器配置 
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()) //拦截器注册对象
        .addPathPatterns("/") //指定要拦截的请求
        .excludePathPatterns("/user/login","/**/page/**","/user/register","/**/admin/**")
        ;

    }
//    @Bean
//    public RedisCache getRedisCache(){
//        return new RedisCache();
//    }
}
