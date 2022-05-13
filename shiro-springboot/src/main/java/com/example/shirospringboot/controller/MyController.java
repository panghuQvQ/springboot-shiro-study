package com.example.shirospringboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * @author wzy
 * @version 1.0.0
 * @ClassName MyController.java
 * @Description 在templates目录下的所有页面，只能通过@Controller来跳转
 * 这个需要模板引擎的支持！thymeleaf
 * @createTime 2022年05月03日 16:40:00
 */
@Controller
public class MyController {

    @RequestMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello Shiro"); // 视图添加属性
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update() {
        return "user/update";
    }

    // 跳转至登录页面
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    // 登录功能
    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token); // 执行登录方法，如果没有异常就说明OK了
            return "index";
        } catch (UnknownAccountException e) { // 用户名不存在
            model.addAttribute("msg", "用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) { // 密码不存在
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    // 跳转至未授权页面
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized () {
        return "未经授权无法访问此页面";
    }
}
