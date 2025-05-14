package com.shixun.travel.domain;

import lombok.Data;

/**
 * 购物车项
 */
@Data   //生成get和set方法
public class CartItem {

    private Route route;  //线路对象
    private int num;  //多少件
    private double subTotal;  //小计

    /**
     * 计算这一项的金额
     */
    public double getSubTotal() {
        subTotal = num * route.getPrice();
        return subTotal;
    }
}
