package com.shixun.travel.controller;

import com.shixun.travel.domain.ResultInfo;
import com.shixun.travel.domain.User;
import com.shixun.travel.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController  //将这个控制器对象放到Spring容器中，返回JSON字符串
@RequestMapping("/user")  //指定模块的访问地址
@Slf4j  //记录日志
public class UserController extends BaseController{

    @Autowired  //将业务对象注入给控制器对象
    private UserService userService;

//    @Autowired
//    private HttpSession session;//会话对象 每个用户都有一个会话对象 用户发送的验证码把他放到会话域中去

    /**
     * 注册的方法
     */
    @SneakyThrows  //生成的字节码中自动捕获异常
    @RequestMapping("/register")
    public ResultInfo register(@RequestBody Map<String, Object> params) {
        //参数：{user={username=孙悟空, telephone=13512345678, password=123}, smsCode=1234}
        //添加日志
        log.info("注册用户，参数是：{}", params);
        System.out.println("注册用户，参数是：{}"+ params);
        //得到用户信息
        Map<String, String> userMap = (Map<String, String>) params.get("user");
        //复制成user对象
        User user = new User();
        //将userMap中所有同名的属性复制到user对象中
        BeanUtils.populate(user, userMap);
        //得到提交的验证码
        String smsCode = (String) params.get("smsCode");

//        //从会话域session中取出手机发送的验证码
//        String code = (String) session.getAttribute("smsCode_" + user.getTelephone());
//        //如果不相等
//        if (!smsCode.equals(code)) {
//            return new ResultInfo(false, "验证码错误");
//        }


        //验证码正确，调用业务层进行注册
        return userService.register(user,smsCode);
    }

    /**
     * 通过用户名判断用户是否存在
     * */
    @RequestMapping("/findByUserName")
    public ResultInfo findByUserName(String username) {
        log.info("判断用户名：{}", username);
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResultInfo(true,"用户可以使用");
        } else {
            return new ResultInfo(true,"用户不能使用");
        }
    }

    @RequestMapping("/findByTelephone")
    public ResultInfo findByTelephone(String telephone){
        log.info("判断手机号：{}", telephone);
        User user = userService.findByTelephone(telephone);
        if (user == null) {
            return new ResultInfo(true,"手机号能使用");
        } else {
            return new ResultInfo(false,"手机号已存在");
        }
    }

    /**
     * 发送短信
     */
    @RequestMapping("/sendSms")
    public ResultInfo sendSms(String telephone) {
        //1.调用工具类随机生成6位验证码
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        log.info("验证码：{}", code);
        //2.调用业务层完成短信的发送 返回ResultInfo对象发送结果
        ResultInfo resultInfo = userService.sendSms(telephone, code);
        //3.old如果发送成功，将验证码放到会话域中
        //if (resultInfo.getSuccess()) {
        //    session.setAttribute("smsCode_" + telephone, code);
        //}
        //3.new 业务层将验证码保存到了redis中
        //4.返回发送的结果
        return resultInfo;
    }

    /**
     * 登录发送手机号
     * */
    @RequestMapping("/loginSendMessage")
    public ResultInfo loginSendMessage(String username){
        //1．通过用户名查询用户对象
        User user = userService.findByUsername(username);
        //2．如果用户对象为空，则返回用户名不存在
        if(user == null){
            return new ResultInfo(false,"用户名不存在");
        }
        //3．否则获取手机号
        String telephone =  user.getTelephone();
        //4．随机生成验证码
        String smsCode = RandomStringUtils.randomNumeric(6);
        System.out.println("登录验证码"+smsCode);
        //5．调用业务层,发送短信验证码,代码重用
        ResultInfo resultInfo = userService.sendSms(telephone,smsCode);
        //6．返回发送的结果
        return resultInfo;
    }
    /**
     * 登录的方法
     */
    @RequestMapping("/login")
    ResultInfo login(@RequestBody Map<String,String> param) {
        //调用业务层实现登录
        ResultInfo resultInfo = userService.login(param);
        //判断是否登录成功
        if (resultInfo.getSuccess()) {
            //将用户的信息保存到会话域中
            session.setAttribute("user",resultInfo.getData());
        }
        //返回登录结果
        return resultInfo;
    }

    /**
     * 判断用户登录状态
     * */
    @RequestMapping("/isLogged")
    public ResultInfo isLogged(){

        User user = getUserFromSession();

        if(user!=null){
            return new ResultInfo(true,"用户已经登录",user.getUsername());
        }else{
            return new ResultInfo(false,"用户未登录");
        }
    }
    /**
     * 用户退出
     * */
    @RequestMapping("/logout")
    public void logout(){
        session.invalidate();
    }

}






























