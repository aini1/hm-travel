package com.shixun.travel.controller;

import com.shixun.travel.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * 所有控制器的父类
 */
public class BaseController {

    @Autowired
    protected HttpSession session;  //会话对象

    /**
     * 从会话域中获取用户对象
     */
    protected User getUserFromSession() {
        return (User) session.getAttribute("user");
    }
}
