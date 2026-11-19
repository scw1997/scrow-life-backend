package com.scw.scrowlifebackend.controller;

import com.scw.scrowlifebackend.mojo.Response;
import com.scw.scrowlifebackend.mojo.User;
import com.scw.scrowlifebackend.service.EmailCodeService;
import com.scw.scrowlifebackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CorsFilter;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailCodeService emailService;

    @PostMapping("/register")
    public Response register(@RequestBody User user ) {
        System.out.println("xxx");
        userService.register(user);
        return Response.success(null);
    }

    @PostMapping("/send_code")
    public Response sendCode(@RequestBody User user) {
        emailService.sendCode(user.getEmail());
        return Response.success(null);
    }
}
