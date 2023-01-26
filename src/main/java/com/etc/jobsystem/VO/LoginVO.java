package com.etc.jobsystem.VO;

import com.etc.jobsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    private String token;
    private User user;

}
