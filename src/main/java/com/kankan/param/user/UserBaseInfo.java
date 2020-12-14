package com.kankan.param.user;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;

import com.kankan.module.User;
import com.kankan.service.TokenService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBaseInfo {
    private String username;
    private String password;
    private String userPhoto;
    @Email(message = "邮箱格式错误")
    private String userEmail;

    public User toUser(TokenService tokenService, String userToken) {
        User user = tokenService.findUserByToken(userToken);
        if (user.isNotEmpty()) {
            if (StringUtils.isNotEmpty(username)) {
                user.setUsername(username);
            }
            if (StringUtils.isNotEmpty(password)) {
                user.setPassword(password);
            }
            if (StringUtils.isNotEmpty(userPhoto)) {
                user.setUserPhoto(userPhoto);
            }
            if (StringUtils.isNotEmpty(userEmail)) {
                user.setUserEmail(userEmail);
            }
        }
        return user;
    }
}
