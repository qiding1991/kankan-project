package com.kankan.param.mail;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.kankan.module.User;

import lombok.Data;

@Data
public class VerifySmsCode {

    @NotNull(message = "用户邮箱,不能为空")
    @NotEmpty(message = "用户邮箱,不能为空")
    @Email(message = "邮箱格式错误")
    private String userEmail;


    @NotNull(message = "用户邮箱,不能为空")
    @NotEmpty(message = "用户邮箱,不能为空")
    private String smsCode;

    public User toUser() {
        User user= User.builder().userEmail(userEmail).build();
        return user;
    }
}
