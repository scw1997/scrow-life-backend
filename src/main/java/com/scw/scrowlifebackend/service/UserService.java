package com.scw.scrowlifebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scw.scrowlifebackend.dto.UserDto;
import com.scw.scrowlifebackend.mapper.UserMapper;
import com.scw.scrowlifebackend.mojo.MyException;
import com.scw.scrowlifebackend.mojo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final EmailCodeService emailService;


    @Transactional
    public void register(UserDto params) {
        // 1. 校验邮箱验证码（只有创建时才有这个字段）
        Boolean checked = emailService.checkCode(params.getEmailCode(),params.getEmail() );
        if (!checked) {
            throw new MyException(400, "邮箱验证码错误或已过期");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, params.getUsername()).or().eq(User::getEmail, params.getEmail());
        if(userMapper.exists(wrapper)) {
            throw new MyException(400, "用户名或邮箱已存在");
        }

       User user = new User();
       user.setUsername(params.getUsername());
       user.setPassword(params.getPassword());
       user.setEmail(params.getEmail());
       int count = userMapper.insert(user);
       if(count != 1){
            throw new MyException(500, "注册失败");
        }

    }
}
