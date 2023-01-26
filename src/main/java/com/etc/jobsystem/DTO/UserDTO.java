package com.etc.jobsystem.DTO;

import com.etc.jobsystem.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private Integer userId;

    private String userName;



    private String userPhone;


    private String userEmail;

    private String jobName;

    public UserDTO(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
    }


}
