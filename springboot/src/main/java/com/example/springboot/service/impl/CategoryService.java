package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {
    @Resource
    CategoryMapper categoryMapper;

    //  查询所有
    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    //  根据条件查询
    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        // 自关联查询
        List<Category> categories = categoryMapper.listByCondition(baseRequest);
        return new PageInfo<>(categories);
    }

    //  新增
    @Override
    public void save(Category category) {
        categoryMapper.save(category);
    }

    //  根据id查询
    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    //  更新
    @Override
    public void update(Category category) {
        category.setUpdatetime(LocalDate.now());
        categoryMapper.updateById(category);
    }

    //  删除（根据id删除）
    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
