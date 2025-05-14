package com.shixun.travel.controller;

import com.shixun.travel.domain.Address;
import com.shixun.travel.domain.Cart;
import com.shixun.travel.domain.ResultInfo;
import com.shixun.travel.domain.User;
import com.shixun.travel.service.AddressService;
import com.shixun.travel.utils.CartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/order",produces = "application/json;charset=utf-8")
public class AddressController extends BaseController{

    //调用收货地址业务层
    @Autowired
    private AddressService addressService;

    //调用购物车工具类
    @Autowired
    private CartUtils cartUtils;

    /**
     * 显示商品结算页面
     */
    @RequestMapping("/prepareOrder")
    public ResultInfo prepareOrder() {
        //1.从会话域中获取用户对象
        User user = getUserFromSession();
        //2. 调用地址业务对象，查询当前用户的所有收货地址
        List<Address> addressList = addressService.findAddressByUid(user.getUid());
        //3.从Redis中查询当前用户的购物车
        Cart cart = cartUtils.getCartFromRedis(user);
        //4.将两个对象封装到Map对象
        HashMap<String,Object> map = new HashMap<>();
        map.put("addressList", addressList);
        map.put("cart",cart);
        //5.返回结果对象
        return new ResultInfo(true, map);
    }
}

