package com.kankan.param.mail;

import com.kankan.module.User.ThreePartLogin;
import com.kankan.param.user.ThreePartLoginParam;
import java.util.Arrays;
import java.util.Objects;
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


    private ThreePartLoginParam threeLoginParam;

    public User toUser() {
        User user= User.builder().userEmail(userEmail).build();
        if (Objects.nonNull(threeLoginParam)) {
            ThreePartLogin threePartLogin = ThreePartLogin.builder()
                .threePartId(threeLoginParam.getThreePartId())
                .threePartType(threeLoginParam.getThreePartType())
                .build();
            user.setThreePartLogin(Arrays.asList(threePartLogin));
        }
        return user;
    }
}
