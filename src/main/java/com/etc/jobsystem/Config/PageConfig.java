package com.etc.jobsystem.Config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageConfig {


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //创建拦截器，对执行的sql进行拦截
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //对Mysql拦截
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
