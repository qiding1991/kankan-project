package com.kankan.param.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.kankan.module.User;
import com.kankan.service.TokenService;
import com.kankan.util.Md5Util;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Data
public class RegisterInfo {

    @NotNull(message = "用户邮箱,不能为空")
    @NotEmpty(message = "用户邮箱,不能为空")
    private String username;

    @NotNull(message = "用户邮箱,不能为空")
    @NotEmpty(message = "用户邮箱,不能为空")
    private String password;

    public User toUser(TokenService tokenService, String verifyToken) {
        User user = tokenService.findUserByToken(verifyToken);
        if (user.isNotEmpty()) {
            user.username(username).password(Md5Util.md5Hex(password));
        }
        return user;
    }
}
