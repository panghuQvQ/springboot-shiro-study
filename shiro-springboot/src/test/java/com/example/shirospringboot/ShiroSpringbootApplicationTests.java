package com.example.shirospringboot;

import com.example.shirospringboot.entity.User;
import com.example.shirospringboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Autowired
    private UserService service;

    @Test
    void contextLoads() {
        User user = service.queryByName("wzy");
        System.out.println(user);

    }

}
