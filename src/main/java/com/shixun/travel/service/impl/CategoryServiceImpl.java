package com.shixun.travel.service.impl;

import com.shixun.travel.dao.CategoryDao;
import com.shixun.travel.dao.UserDao;
import com.shixun.travel.domain.Category;
import com.shixun.travel.service.CategoryService;
import com.shixun.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service //将当前这个对象放到spring容器中去
public class CategoryServiceImpl implements CategoryService {

    @Autowired //将CategoryDao这个对象注入到业务对象中
    private CategoryDao categoryDao;

    @Autowired
    private RedisTemplate<String,List<Category>> redisTemplate; //注入redis模板对象，用来访问redis

    @Autowired
    public List<Category> findAll(){
        //1.从Redis中获取
        ValueOperations<String, List<Category>> ops = redisTemplate.opsForValue();
        //2.获取分类集合
        List<Category> categories = ops.get("categories");
        //3.判断是否为空
        if(categories == null){
            System.out.println("从MySQL中查询分类数据");
            //表示redis中没有数据，从mysql中获取
            categories = categoryDao.findAll();
            //将数据放到redis中，供下次使用
            ops.set("categories",categories);
        }else{
            System.out.println("从Redis中获取数据");
        }
        return categories;
    }
}
