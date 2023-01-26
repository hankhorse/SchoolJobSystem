package com.etc.jobsystem.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.etc.jobsystem.entity.Company;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JobVO {

    private Integer jobId;
    private String jobName;

    private Integer userId;

    private String jobSalary;
    private String jobContext;
    private String jobEducation;

    private String userName;
    private String companyName;
    private String companyImg;

    public JobVO() {
    }

    public void set(String userName, Company company) {
        this.userName = userName;
        this.companyName = company.getCompanyName();
        this.companyImg = company.getCompanyImg();
    }
}
