package com.example.shirospringboot.mapper;

import com.example.shirospringboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author wzy
 * @version 1.0.0
 * @ClassName UserMapper.java
 * @Description TODO
 * @createTime 2022年05月04日 15:19:00
 */
@Mapper // 表示这是一个mybatis的接口类
@Repository // 被spring整合
public interface UserMapper {

    public User queryByName(String name);
}
