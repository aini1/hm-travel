package com.shixun.travel.service;

import com.shixun.travel.domain.Category;
import com.shixun.travel.domain.ResultInfo;

import javax.xml.transform.Result;
import java.util.List;

public interface CategoryService {

    List<Category> findAll();
}
