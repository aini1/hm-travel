package com.shixun.travel.service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import com.shixun.travel.domain.Route;
import com.shixun.travel.domain.RouteImg;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/*都改回真确版本的

我看到当前的route_list.html文件内容已经和正确版本基本一致了。主要有一个小问题需要修正：</body></html>标签的位置需要移到Vue脚本之后。

* 路线业务层*/
public interface RouteService {
    /**
     * 分页查询，封装一页的数据
     * @param cid 分类id
     * @param pageNum 第几页
     * @param rname 线路名字
     */
    PageInfo<Route> findRouteByCid(int cid, int pageNum, String rname);

    /**
     * 查询某个分类下收藏人数最多的6条记录
     */
    List<Route> findMostFavoriteRouteByCid(int cid);

    /**
     * 将四张表中数据封装到一个Route对象中
     */
    Route findRouteByRid(int rid);

    /**
     * 通过rid查询一条线路，返回route对象
     * */
    Route findRouteById(int rid);

}
