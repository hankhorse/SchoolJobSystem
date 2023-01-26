package com.etc.jobsystem.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.etc.jobsystem.entity.Job;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JobDTO {

    private Integer jobId;


    private String jobName;

    private Integer userId;

    private String jobSalary;

    private String jobContext;

    private String jobEducation;

    private String companyName;

    private  String hrName;

    private String companyAddress;

    public JobDTO(Job job) {
        this.jobId = job.getJobId();
        this.jobName =  job.getJobName();
        this.userId =  job.getUserId();
        this.jobSalary =  job.getJobSalary();
        this.jobContext =  job.getJobContext();
        this.jobEducation =  job.getJobEducation();

    }



}
