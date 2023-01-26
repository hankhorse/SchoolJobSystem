package com.etc.jobsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * (Job)实体类
 *
 * @author makejava
 * @since 2022-11-03 17:14:27
 */
@Data
@TableName(value="job")
public class Job  {

    @TableId(type = IdType.AUTO)
    private Integer jobId;

    @NotNull
    private String jobName;
    
    private Integer userId;
    
    private String jobSalary;
    @NotNull
    private String jobContext;
    @NotNull
    private String jobEducation;



}

