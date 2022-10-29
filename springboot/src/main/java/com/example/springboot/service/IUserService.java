package com.example.springboot.service;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;

import java.util.List;

public interface IUserService {
    //  查询所有
    List<User> list();

    //  分页
    Object page(UserPageRequest userPageRequest);
}
