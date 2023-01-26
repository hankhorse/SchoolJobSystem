package com.etc.jobsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (UserInfo)实体类
 *
 * @author makejava
 * @since 2022-11-03 17:14:01
 */
@Data
@TableName(value="user_info")
public class UserInfo{

    @TableId(type = IdType.AUTO)
    private Integer infoId;
    
    private Integer userId;
    
    private String infoAge;
    
    private String infoEducation;
    
    private String infoSex;
    
    private String infoIntroduction;
    
    private String infoSchool;
    
    private String infoSalary;
    
    private String infoPosition;


}

