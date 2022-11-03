package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICategoryService {
    //  查询所有
    List<Category> list();

    //  分页
    PageInfo<Category> page(BaseRequest baseRequest);

    //  添加
    void save(Category category);

    //  根据id查询
    Category getById(Integer id);

    //  更新
    void update(Category category);

    //  删除
    void deleteById(Integer id);
}
