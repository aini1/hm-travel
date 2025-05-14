package com.shixun.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shixun.travel.dao.RouteDao;
import com.shixun.travel.domain.Category;
import com.shixun.travel.domain.Route;
import com.shixun.travel.domain.RouteImg;
import com.shixun.travel.domain.Seller;
import com.shixun.travel.service.RouteService;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;

    /**
     * 分页查询，封装一一夜数据
     *
     * @param cid
     * @param pageNum
     */
    @Override
    public PageInfo<Route> findRouteByCid(int cid, int pageNum,String rname) {
        PageHelper.startPage(pageNum,3);
        List<Route> list = routeDao.findRouteByCid(cid,rname);

        return new PageInfo<>(list);
    }

    /**
     * 查询某个分类下收藏人数最多的6条记录
     *
     * @param cid
     */
    @Override
    public List<Route> findMostFavoriteRouteByCid(int cid) {
        return routeDao.findMostFavoriteRouteByCid(cid);
    }


    /**
     * 将四张表的数据封装到一个Route对象中
     *
     * @param rid
     */
    @Override
    @SneakyThrows
    public Route findRouteByRid(int rid) {
        //调用dao 查询三张表数据 分类 线路 商家
        Map<String,Object> routeMap= routeDao.findRouteByRid(rid);
        //复制route对象属性
        Route route = new Route();
        //使用apache包
        BeanUtils.populate(route,routeMap);
        //复制分类和商家对象
        Category category = new Category();
        //使用apache包
        BeanUtils.populate(category,routeMap);
        Seller seller = new Seller();
        //使用apache包
        BeanUtils.populate(seller,routeMap);
        //4.调用dao查询线路对应的图片集合
        List<RouteImg> imgList = routeDao.findRouteImgsByRid(rid);
        //5.将三个属性放到route对象中去
        route.setCategory(category);
        route.setSeller(seller);
        route.setRouteImgList(imgList);
        //6.返回route对象
        return route;
    }

    /**
     * 通过rid查询一条线路，返回route对象
     *
     * @param rid
     */
    @Override
    public Route findRouteById(int rid) {
        return routeDao.findRouteById(rid);
    }


}
