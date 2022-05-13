package com.example.shirospringboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Subject 用户
 * SecurityManager 管理所有用户
 * Realm 连接数据
 */
@Configuration
public class ShiroConfig {

    // ShiroFilterFactoryBean--->步骤3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        // 设置shiro的内置过滤器
        /*
        * anon: 无需认证就可以访问
        * authc: 必须认证，才能访问
        * user: 必须拥有，记住我功能，才能使用
        * perm: 拥有对某个资源的权限才能访问
        * role: 拥有某个角色权限才能访问
        */
        // 登录拦截功能
        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
        filterMap.put("/user/*","authc");

        // 授权,正常情况下，没有授权会跳转到未授权页面
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

        bean.setFilterChainDefinitionMap(filterMap);

        // 设置登录的请求，(如果没有权限访问页面，则跳转至登录页)
        bean.setLoginUrl("/toLogin");

        // 设置未授权请求
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }           

    // DefaultWebSecurityManager--->步骤2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建realm对象，需要自定义类--->步骤1
     * UserRealm 父类 AuthorizingRealm 将获取 Subject 相关信息分成两步：
     *      获取身份验证信息（doGetAuthenticationInfo）及授权信息（doGetAuthorizationInfo）
     */
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    // 整合ShiroDialect 用来整合 shtro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
