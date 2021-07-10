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
public class SendSmsCode {

  @NotNull(message = "用户邮箱,不能为空")
  @NotEmpty(message = "用户邮箱,不能为空")
  @Email(message = "邮箱格式错误")
  private String userEmail;



  public User toUser() {
    User user = User.builder().userEmail(userEmail).build();
    return user;
  }
}
