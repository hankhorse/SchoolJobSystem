package com.etc.jobsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * (Company)表实体类
 *
 * @author makejava
 * @since 2022-11-03 17:14:50
 */
@SuppressWarnings("serial")
@Data
@TableName(value="company")
public class Company {

    @TableId(type = IdType.AUTO)
    private Integer companyId;
    @NotNull
    private String companyName;

    private String companyContext;
    @NotNull
    private String companyAddress;

    private Integer companyOwnerid;

    private Integer companyNumber;
    @NotNull
    private String companyPhone;

    private String companyImg;
    //新加的字段
    private String companyProof;
//图片
    private String proofImg;
}

