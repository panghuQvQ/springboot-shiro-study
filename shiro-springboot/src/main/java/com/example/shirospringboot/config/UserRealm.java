package com.example.shirospringboot.config;

import com.example.shirospringboot.entity.User;
import com.example.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author wzy
 * @version 1.0.0
 * @ClassName UserRealm.java
 * @Description 自定义的UserRealm
 * @createTime 2022年05月03日 16:54:00
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.addStringPermission("user:add");

        // 拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal(); // 拿到当前User对象
        /**
         * 设置当前用户的权限
         * 如果数据库中存储的权限为user:add,user:update等多种权限时，可用addStringPermissions
         * 反之，如果为user:add一种权限时，用addStringPermission
         */
//        info.addStringPermission(currentUser.getPerms());
        String perms = currentUser.getPerms();
        List<String> permsList = Arrays.asList(perms.split(","));
        info.addStringPermissions(permsList);
        return info;
    }

    // 认证  AuthenticationToken用于存储前端传来的登录信息，通俗来说就是用户名及密码等
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        // 用户名，密码认证  从数据库中取
        User user = userService.queryByName(userToken.getUsername());

        if (user == null) {
            return null; // 抛出UnknownAccountException, 用户名不存在
        }

        // 在session添加当前用户信息
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",user);

        /**
         * SimpleAuthenticationInfo中可以传三个参数也可以传四个参数--->身份信息（用户名）、凭据（密文密码）、盐（username+salt）
         * 第一个参数：传入的都是com.java.entity包下的User类的user对象。
         *          Subject subject = SecurityUtils.getSubject();
         *          User user = (User) subject.getPrincipal();
         *          这个参数填了之后，上面doGetAuthorizationInfo授权功能，就可以获取当前用户了
         * 第二个参数: 传入的是从数据库中获取到的password,然后再与token中的password进行对比，匹配上了就通过，匹配不上就报异常。
         * 第三个参数，盐–用于加密密码对比。 若不需要，则可以设置为空 ""
         * 第四个参数：当前realm的名字。
         */
        // 密码认证，shiro自己做，防止密码泄露,可以加密---> MD5加密  MD5盐值加密
        return new SimpleAuthenticationInfo(user, user.getPwd(), "");
    }
}
