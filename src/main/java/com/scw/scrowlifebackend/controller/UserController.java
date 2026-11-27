package com.scw.scrowlifebackend.controller;

import com.scw.scrowlifebackend.dto.UserDto;
import com.scw.scrowlifebackend.group.UserGroup;
import com.scw.scrowlifebackend.mojo.Response;
import com.scw.scrowlifebackend.mojo.User;
import com.scw.scrowlifebackend.service.EmailCodeService;
import com.scw.scrowlifebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailCodeService emailService;

    @PostMapping("/register")
    public Response register(@RequestBody @Validated(UserGroup.CreateUserGroup.class) UserDto user ) {
        userService.register(user);
        return Response.success(null);
    }

    @PostMapping("/send_code")
    public Response sendCode(@RequestBody @Validated(UserGroup.SendCodeGroup.class) UserDto user) {
        emailService.sendCode(user.getEmail());
        return Response.success(null);
    }
}
