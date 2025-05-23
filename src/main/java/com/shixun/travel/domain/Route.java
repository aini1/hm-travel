package com.shixun.travel.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 旅游线路商品实体类
 */
@Data
public class Route implements Serializable {

    //线路表中属性
    private int rid;//线路id，必输
    private String rname;//线路名称，必输
    private double price;//价格，必输
    private String routeIntroduce;//线路介绍
    private int num;//收藏数量
    private int cid;//所属分类，必输
    private String rimage;//缩略图
    private int sid;//所属商家

    private Category category;//所属分类

    private Seller seller;//所属商家

    private List<RouteImg> routeImgList;//商品详情图片列表
}
