package com.shixun.travel.controller;

import com.github.pagehelper.PageInfo;
import com.shixun.travel.domain.Route;
import com.shixun.travel.domain.RouteImg;
import com.shixun.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/route",produces = "application/json;charset=utf-8")
public class RouteController extends BaseController{
    @Autowired
    private RouteService routeService;


    /**
     * 分页查询线路
     * @param cid 分类id
     * @param pageNum 每页大小
     * @param rname 查询线路名字
     */
    @RequestMapping("/findRouteListByCid")
    public PageInfo<Route> findRouteListByCid(Integer cid, Integer pageNum,String rname){
        return routeService.findRouteByCid(cid,pageNum,rname);
    }

    /**
     * 查询某个分类下收藏人数最多的6条记录
     * @param cid
     */
    @RequestMapping("/findMostFavoritRouteByCid")
    public List<Route> findMostFavoritRouteByCid(int cid) {
        return routeService.findMostFavoriteRouteByCid(cid);
    }

    /**
     * 通过rid查询一条线路
     */
    @RequestMapping("/findRouteByRid")
    public Route findRouteByRid(int rid) {
        System.out.println("查询线路rid: " + rid);
        return routeService.findRouteByRid(rid);
    }

}
