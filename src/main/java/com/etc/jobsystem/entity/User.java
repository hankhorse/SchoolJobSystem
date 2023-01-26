package com.etc.jobsystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
/**
 * (User)实体类
 *
 * @author makejava
 * @since 2022-11-03 15:02:04
 */

@Data
@TableName(value="user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String userName;

    private String userPassword;

    private String userPhone;

    private String userImg;

    private String userEmail;

    private String userRole;

    public void  set(Integer userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }


}

