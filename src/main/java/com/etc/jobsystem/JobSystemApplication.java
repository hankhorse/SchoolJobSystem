package com.etc.jobsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.etc.jobsystem.**.mapper")
public class JobSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSystemApplication.class, args);
    }

}
