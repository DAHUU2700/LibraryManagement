package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //  查询所有
    List<Category> list();

    //  条件查询（多态的方式）
    List<Category> listByCondition(BaseRequest baseRequest);

    //  添加
    void save(Category category);

    //  根据id查询
    Category getById(Integer id);

    //  更新
    void updateById(Category category);

    //  删除
    void deleteById(Integer id);


}
