package com.shixun.travel.dao;

import com.shixun.travel.domain.Category;
import com.shixun.travel.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryDao {

    @Select("SELECT * FROM tab_category ORDER BY cid")
    List<Category> findAll();


}
