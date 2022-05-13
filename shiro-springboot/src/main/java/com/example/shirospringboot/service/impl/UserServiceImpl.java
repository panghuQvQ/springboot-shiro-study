package com.example.shirospringboot.service.impl;

import com.example.shirospringboot.entity.User;
import com.example.shirospringboot.mapper.UserMapper;
import com.example.shirospringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wzy
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2022年05月04日 15:24:00
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryByName(String name) {
        User user = userMapper.queryByName(name);
        return user;
    }
}
