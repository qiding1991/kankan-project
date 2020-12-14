package com.kankan.param.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import com.kankan.dao.entity.UserEntity;
import com.kankan.module.User;
import com.kankan.service.UserService;
import com.kankan.util.Md5Util;

import lombok.Data;

@Data
public class LoginParam {
    @NotNull(message = "用户邮箱,不能为空")
    @NotEmpty(message = "用户邮箱,不能为空")
    private String password;

    @NotNull(message = "用户邮箱,不能为空")
    @NotEmpty(message = "用户邮箱,不能为空")
    @Email(message = "邮箱格式错误")
    private String userEmail;

    public User toUser(UserService userService) {
        UserEntity userEntity = userService.findUserByEmail(userEmail);
        User user = new User();
        if (userEntity == null || !Md5Util.md5Hex(password).equalsIgnoreCase(userEntity.getPassword())) {
            user = User.emptyUser();
        } else {
            BeanUtils.copyProperties(userEntity, user);
            user.setUserId(userEntity.getId());
        }
        return user;
    }
}
