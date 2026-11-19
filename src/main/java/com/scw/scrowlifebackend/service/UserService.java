package com.scw.scrowlifebackend.service;

import com.scw.scrowlifebackend.mapper.UserMapper;
import com.scw.scrowlifebackend.mojo.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    public void register(User user) {
        userMapper.insert(user);
    }
}
