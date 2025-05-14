package com.shixun.travel.service.impl;

import com.shixun.travel.dao.UserDao;
import com.shixun.travel.domain.ResultInfo;
import com.shixun.travel.domain.User;
import com.shixun.travel.service.UserService;
import com.shixun.travel.utils.Md5Utils;
import com.shixun.travel.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service //将当前这个对象放到spring容器中去
public class UserServiceImpl implements UserService {

    @Autowired //将userDao这个对象注入到业务对象中
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate; //注入redis模板对象，用来访问redis

    /**
     * 用户注册
     * @param user
     */
    @Override
    public ResultInfo register(User user,String smsCode) {
        //1.根据用户名判断用户是否存在
        User user1 = userDao.findByUserName(user.getUsername());
        //如果用户存在user1不为空，如果不存在返回null
        if (user1 !=null) {
            return new ResultInfo(false, "用户名已经存在");
        }
        //2.根据手机号判断用户是否存在
        User user2 = userDao.findByTelephone(user.getTelephone());
        if (user2!=null) {
            return new ResultInfo(false, "手机号已经被注册");
        }
        //----------------------判断验证码是否正确
        ValueOperations ops = redisTemplate.opsForValue();
        String code = (String) ops.get("smsCode_"+user.getTelephone());
        if(code == null){
            return new ResultInfo(false,"验证码过期");
        }
        //不为空就判断
        if(!code.equals(smsCode)){
            return new ResultInfo(false,"验证码错误");
        }
        //验证码正确，删除redis中的代码
        redisTemplate.delete("smsCode_"+user.getTelephone());
        //--------------------------------
        //3.将密码进行加盐加官
        //写入数据库中密码=md5(用户名+密码+盐)
        //得到盐
        String salt = UuidUtils.simpleUuid();
        //对拼接的字符串使用md5加密
        String md5 = Md5Utils.getMd5(user.getUsername() + user.getPassword() + salt);
        //设置密码
        user.setPassword(md5);
        //盐也要存下来
        user.setSalt(salt);

        //4.将用户写到数据库中
        userDao.sava(user);

        return new ResultInfo(true, "注册成功");
    }

    /**
     * 通过用户名
     *
     * @param username
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User findByTelephone(String telephone) {
        return userDao.findByTelephone(telephone);
    }

    /**
     * 发送手机短信
     *
     * @param telephone
     * @param smsCode
     */
    @Override
    public ResultInfo sendSms(String telephone, String smsCode) {
        //调用工具类发送手机短信
        //String result = SmsUtils.send(telephone,"黑马旅游","SMS_195723031","helloSicong");
        //String result = SmsUtils.send(telephone,"黑马旅游","SMS_195723031",smsCode);
        String result = "OK";
        //返回的是OK 发送成功
        if ("OK".equals(result)){
            //发送成功将验证码放到redis，并设置过期时间
            ValueOperations ops = redisTemplate.opsForValue();
            //键 值 时间 单位
            ops.set("smsCode_" + telephone,smsCode,30, TimeUnit.SECONDS);


            return new ResultInfo(true,"验证码发送成功");
        }else{
            return new ResultInfo(false,"验证码发送失败");
        }
    }

    /**
     * 登录方法
     *
     * @param param
     */
    @Override
    public ResultInfo login(Map<String, String> param) {
        //获取用户名，密码，验证   ctrl+d 复制一行
        String username = param.get("username");
        String password = param.get("password");
        String smsCode = param.get("smsCode");

        //1. 通过用户名查找用户
        User user = userDao.findByUserName(username);
        //2. 如果没有找到，则返回用户名不存在
        if (user == null) {
            return new ResultInfo(false, "用户名不存在");
        }
        //3. 如果找到，判断密码是否正确
        //3.1. 拼接字符串=用户名+用户输入的密码+盐
        String inputPassword = user.getUsername() + password + user.getSalt();
        //3.2. 对密码使用md5加密
        String md5 = Md5Utils.getMd5(inputPassword);

        //4. 如果密码错误，则返回密码错误的结果
        if (!md5.equals(user.getPassword())) {
            return new ResultInfo(false, "密码错误");
        }

        //5. 再从Redis中获取验证码，判断验证码是否为空
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String code = ops.get("smsCode_" + user.getTelephone());

        //6. 如果验证码为空，则表示验证码过期
        if (code == null) {
            return new ResultInfo(false, "验证码过期");
        }

        //7. 如果验证码不为空，则判断验证码是否正确
        if (!code.equals(smsCode)) {
            return new ResultInfo(false, "验证码错误");
        }

        //验证码正确，删除验证码，只使用一次
        redisTemplate.delete("smsCode_" + user.getTelephone());

        //8. 如果验证码正确，则返回登录成功，并且封装user对象到data属性中
        return new ResultInfo(true, "登录成功", user);
    }

    /**
     *
     * */


}
