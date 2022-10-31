package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IAdminService {
    //  查询所有
    List<Admin> list();

    //  分页
    PageInfo<Admin> page(BaseRequest baseRequest);

    //  添加
    void save(Admin admin);

    //  根据id查询
    Admin getById(Integer id);

    //  更新
    void update(Admin admin);

    //  删除
    void deleteById(Integer id);

}
