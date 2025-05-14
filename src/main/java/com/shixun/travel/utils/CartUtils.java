package com.shixun.travel.utils;

import com.shixun.travel.domain.Cart;
import com.shixun.travel.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 购物车的工具类
 * 注：修改applicationContext.xml文件中组件扫描
 * <context:component-scan base-package="com.itheima.travel.service,com.itheima.travel.utils"/>
 */
@Component  //将对象放到Spring容器中
public class CartUtils {
    @Autowired
    private RedisTemplate<String, Cart> redisTemplate;

    /**
     * 向redis中添加某个用户的购物车对象
     */
    public void setCartToRedis(User user, Cart cart) {
        System.out.println("设置用户：" + user.getUsername() + "的购物车：" + cart);
        ValueOperations<String, Cart> ops = redisTemplate.opsForValue();
        ops.set("cart_" + user.getUsername(), cart);
    }


    /**
     * 从redis中获取某个用户的购物车对象，如果没有，则创建一个新的
     */
    public Cart getCartFromRedis(User user) {
        ValueOperations<String, Cart> ops = redisTemplate.opsForValue();
        Cart cart = ops.get("cart_" + user.getUsername());
        //判断是否为空，如果为空创建一个新的
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }

    /**
     * 删除购物车对象
     */
    public void removeCart(User user) {
        System.out.println("清空用户：" + user.getUsername() + "，购物车");
        redisTemplate.delete("cart_" + user.getUsername());
    }
}
