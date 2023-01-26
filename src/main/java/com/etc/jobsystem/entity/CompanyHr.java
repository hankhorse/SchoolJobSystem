package com.etc.jobsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (CompanyHr)实体类
 *
 * @author makejava
 * @since 2022-11-03 17:15:16
 */
@Data
@TableName(value="company_hr")
@AllArgsConstructor
@NoArgsConstructor
public class CompanyHr  {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer companyId;
    
    private Integer hrId;



}

