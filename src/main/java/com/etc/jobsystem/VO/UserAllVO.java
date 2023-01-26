package com.etc.jobsystem.VO;

import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAllVO {
    private User user;
    private UserInfo userInfo;
}
