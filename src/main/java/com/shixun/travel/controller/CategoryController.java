package com.shixun.travel.controller;

import com.shixun.travel.domain.Category;
import com.shixun.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  //将这个控制器对象放到Spring容器中，返回JSON字符串
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类数据
     * */
    @RequestMapping("/findAll")
    public List<Category> findAll(){
        return categoryService.findAll();
    }
}
