package com.shixun.test;

import com.shixun.travel.domain.User;
import com.shixun.travel.utils.UuidUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)   //由Spring容器来管理JUnit
@ContextConfiguration("classpath:applicationContext.xml")  //指定Spring的配置文件
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test  //测试方法
    public void testUser() {
        //获取操作字符串的对象，专门用于操作字符串类型的数据
        ValueOperations ops = redisTemplate.opsForValue();

        //操作普通字符串
        ops.set("city","广州");
        String city = (String) ops.get("city");
        System.out.println(city);

        //操作user对象
        User user = new User();
        user.setUid(3333);
        user.setUsername("🐂");
        user.setPassword("123");
        user.setTelephone("17329796333");
        user.setSalt(UuidUtils.simpleUuid());

        //放到redis中
        ops.set("user", user);  //将对象转成JSON字符串
        User user1 = (User) ops.get("user");
        System.out.println(user1);
    }
}
