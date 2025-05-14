package com.shixun.travel.domain;

import lombok.Data;

import java.util.HashMap;

/**
 * 购物车对象
 */
@Data
public class Cart {

    //购物车项的集合，注：创建new出对象来，键：rid(字符串) 值：CartItem对象
    private HashMap<String, CartItem> cartItemMap = new HashMap<>();
    private int cartNum;  //购物车商品总数量
    private double cartTotal;  //购物车中总金额

    /**
     * 计算总数量
     * @return
     */
    public int getCartNum() {
        cartNum = 0;
        //遍历集合中每一项，每一项是cartItem对象
        for (CartItem cartItem : cartItemMap.values()) {
            cartNum += cartItem.getNum();
        }
        return cartNum;
    }

    /**
     * 计算总金额
     * @return
     */
    public double getCartTotal() {
        cartTotal = 0;
        //遍历集合中每一项，每一项是cartItem对象
        for (CartItem cartItem : cartItemMap.values()) {
            cartTotal += cartItem.getSubTotal();
        }
        return cartTotal;
    }
}
