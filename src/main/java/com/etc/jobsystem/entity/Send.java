package com.etc.jobsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Send)实体类
 *
 * @author makejava
 * @since 2022-11-03 17:14:13
 */
@Data
@TableName(value="send")
@NoArgsConstructor
public class Send  {

    @TableId(type = IdType.AUTO)
    private Integer sendId;
    
    private Integer userId;
    
    private Integer jobId;



}

