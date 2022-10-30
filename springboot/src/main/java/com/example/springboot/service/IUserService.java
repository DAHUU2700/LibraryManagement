package com.example.springboot.service;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;

import java.util.List;

public interface IUserService {
    //  查询所有
    List<User> list();

    //  分页
    Object page(UserPageRequest userPageRequest);

    //  添加
    void sava(User user);

    //  修改（根据id查询）
    User getById(Integer id);

    //  更新
    void update(User user);

    //  删除
    void deleteById(Integer id);
}
