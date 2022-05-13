package com.example.shirospringboot.service;

import com.example.shirospringboot.entity.User;

/**
 * @author wzy
 * @version 1.0.0
 * @ClassName UserService.java
 * @Description TODO
 * @createTime 2022年05月04日 15:23:00
 */
public interface UserService {

    public User queryByName(String name);
}
