package com.scw.scrowlifebackend.dto;

import com.scw.scrowlifebackend.group.UserGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank(message = "用户ID不可为空", groups = {UserGroup.UpdateUserGroup.class})
    private Integer id;
    @NotBlank(message = "用户名不可为空", groups = {UserGroup.CreateUserGroup.class})
    private String username;
    @NotBlank(message = "密码不可为空", groups = {UserGroup.CreateUserGroup.class})
    private String password;
    @NotBlank(message = "邮箱不可为空", groups = {UserGroup.CreateUserGroup.class, UserGroup.SendCodeGroup.class})
    private String email;
    @NotBlank(message = "确认密码不可为空", groups = {UserGroup.CreateUserGroup.class})
    private String confirmPassword;
    @NotBlank(message = "邮箱验证码不可为空", groups = {UserGroup.CreateUserGroup.class})
    private String emailCode;
}
