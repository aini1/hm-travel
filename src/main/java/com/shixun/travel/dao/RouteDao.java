package com.shixun.travel.dao;

import com.shixun.travel.domain.Route;
import com.shixun.travel.domain.RouteImg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * mybits 分页插件 分页查询的每一页 和 总数...
 * 线路持久层
*/
public interface RouteDao {
    /**
     * 查询某个分页的数据
     * @param cid 分类主键
     * @return 所有线路
     * 注：mybits中如果查询条件友两个以上的参数，必须要使用@Param注解来指定参数的名字
     * 动态生成SQL语句，是用xml配置方式，而不是用@SELECT注解
     *
     * */
//    @Select("SELECT * FROM tab_route WHERE cid=#{cid} and rname like concat('%',#{rname},'%')")
    List<Route> findRouteByCid(@Param("cid") int cid, @Param("rname") String rname);

    /**
     * 查询某个分类下收藏人数最多的6条记录
     */
    @Select("select * from tab_route where cid=#{cid} order by num desc limit 6")
    List<Route> findMostFavoriteRouteByCid(int cid);

    /**
     * 查询指定rid的一条线路，包含三张表的数据：分类，线路，商家
     */
    @Select("SELECT * FROM tab_category c INNER JOIN tab_route r ON c.cid = r.cid INNER JOIN tab_seller s ON  r.sid = s.sid WHERE rid=#{rid}")
    Map<String, Object> findRouteByRid(int rid);

    /**
     * 通过rid查询这条线路对应的图片
     */
    @Select("SELECT * FROM tab_route_img WHERE rid=#{rid}")
    List<RouteImg> findRouteImgsByRid(int rid);

    /**
     * 通过rid查询一条线路，返回route对象
     * */
    @Select("SELECT * FROM tab_route WHERE rid=#{rid}")
    Route findRouteById(int rid);
}
