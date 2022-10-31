package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    //  查询所有
    List<Admin> list();

    //  条件查询（多态的方式）
    List<Admin> listByCondition(BaseRequest baseRequest);

    //  添加
    void sava(Admin admin);

    //  根据id查询
    Admin getById(Integer id);

    //  更新
    void updateById(Admin admin);

    //  删除
    void deleteById(Integer id);
}
