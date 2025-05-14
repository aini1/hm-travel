package com.shixun.travel.dao;

import com.shixun.travel.domain.Route;
import com.shixun.travel.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户的持久层，由mybatis来实现*/
public interface UserDao  {
    /**
     * 通过用户名查询某个用户
     * mybatis 简化到在方法上面写一个sql语句就够了
     * */
    @Select("SELECT * FROM tab_user WHERE username=#{username}")
    User findByUserName(String username);
    /**
     * 通过手机号查询某个用户
     * */
    @Select("SELECT * FROM tab_user WHERE telephone=#{telephone}")
    User findByTelephone(String telephone);
    /**
     * 添加用户
     * */
    @Insert("INSERT INTO tab_user VALUES(null,#{username},#{password},#{telephone},#{salt})")
    void sava(User user);
    /**
     * 显示首页6条记录
     * */
    @Select("SELECT * FROM tab_route WHERE cid=#{cid} and rname like concat('%',#{rname},'%')")
    List<Route> findMostFavoriteRouteByCid(int cid);

}
