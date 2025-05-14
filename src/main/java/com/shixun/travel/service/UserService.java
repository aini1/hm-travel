package com.shixun.travel.service;

import com.shixun.travel.domain.ResultInfo;
import com.shixun.travel.domain.User;

import java.util.Map;

public interface UserService {
    /**
     * 用户注册
     * <p>
     * 第二个参数验证码 当控制器调用业务层Service进行register注册时，把验证码存到redis中
     * 之前从控制器发送验证码时sendSms直接将验证码保存到session中 从session中获取验证码，现在为从redis中获取验证码
     */
    ResultInfo register(User user, String code);

    /**
     * 通过用户名查询用户
     */
    User findByUsername(String username);

    /**
     * 通过电话名查询电话
     */
    User findByTelephone(String telephone);

    /**
     * 发送手机短信
     */
    ResultInfo sendSms(String telephone, String smsCode);

    /**
     * 登录方法
     */
    ResultInfo login(Map<String,String> param);
}