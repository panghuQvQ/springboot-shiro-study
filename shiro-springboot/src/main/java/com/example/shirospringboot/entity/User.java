package com.example.shirospringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzy
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2022年05月04日 15:17:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;

    private String name;

    private String pwd;

    private String perms;
}
