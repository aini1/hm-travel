package com.shixun.travel.controller;

import com.shixun.travel.domain.*;
import com.shixun.travel.service.RouteService;
import com.shixun.travel.utils.CartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 *
 * */

@RestController //放到spring容器
@RequestMapping(value = "/cart",produces = "application/json;charset=utf-8")//设置返回的数据格式为json格式
public class CartController extends BaseController{

    //注入线路的业务对象
    @Autowired
    private RouteService routeService;
    //注入购物车工具类
    @Autowired
    private CartUtils cartUtils;

    /**
     * 添加线路（商品）到购物车
     * */
    @RequestMapping("/addCart")
    public ResultInfo addCart(Integer num,String rid){
        //1.从会话域中获取用户对象
        User user = getUserFromSession();
        //2.调用工具类获取当前用户的购物车对象
        Cart cart = cartUtils.getCartFromRedis(user);
        //3.获取到所有购物车的集合
        HashMap<String, CartItem> cartItemMap = cart.getCartItemMap();
        //4.通过rid来查找这个购物车是否存在
        CartItem cartItem = cartItemMap.get(rid);
        //5.不存在创建一个新的
        if(cartItem == null){
            cartItem = new CartItem();
            //获取线路对象
            Route route = routeService.findRouteById((Integer.parseInt(rid)));
            cartItem.setRoute(route);
            //设置数量
            cartItem.setNum(num);
            //添加到购物车对象集合中去
            cartItemMap.put(rid,cartItem);
        }else{
            //项已经存在，数量累加
            cartItem.setNum(cartItem.getNum()+num);
        }
        //6.将购物车更新到redis中去
        cartUtils.setCartToRedis(user,cart);
        //7.将刚刚添加到购物车的这一项到会话域中去，供后面显示功能使用
        session.setAttribute("cartItem",cartItem);
        //8.封装到结果对象，并返回
        return new ResultInfo(true,"加入购物车成功");

    }

    /**
     * 从会话域中获取到刚刚添加的购物车顶，并且返回结果对象
     * */
    @RequestMapping("/showCartItem")
    public ResultInfo showCartItem(){
        CartItem cartItem = (CartItem) session.getAttribute("cartItem");
        //判断购物车是否为空
        if(cartItem == null){
            //表示会话已过期
            return new ResultInfo(false,"请重新登录");
        }
        //如果有数据就封装购物车顶返回
        return new ResultInfo(true,cartItem);
    }

    /**
     * 显示整个购物车内容
     * */
    @RequestMapping("/findAll")
    public ResultInfo findAll(){
        //1.从会话域中获取用户对象
        User user = getUserFromSession();
        //2.通过用户对象获取它的购物车对象
        Cart cart = cartUtils.getCartFromRedis(user);
        //3.将购物车对象封装到操作结果对象中
        return new ResultInfo(true,cart);
    }

    /**
     * 删除购物车中的一项
     * */
    @RequestMapping("/deleteCartItem")
    public Cart deleteCartItem(String rid){
        //1.从会话域中获取用户对象
        User user = getUserFromSession();
        //2.通过用户对象获取它的购物车对象
        Cart cart = cartUtils.getCartFromRedis(user);
        //3.获取购物车中所有的项
        HashMap<String,CartItem> cartItemMap = cart.getCartItemMap();
        //4.通过rid删除这一项
        cartItemMap.remove(rid);
        //5.更新删除以后的购物车
        cartUtils.setCartToRedis(user,cart);
        //6.返回cart对象
        return cart;
    }
}
