package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBorrowService {
    //  查询所有
    List<Borrow> list();

    //  分页
    PageInfo<Borrow> page(BaseRequest baseRequest);

    //  添加
    void save(Borrow borrow);

    //  根据id查询
    Borrow getById(Integer id);

    //  更新
    void update(Borrow borrow);

    //  删除
    void deleteById(Integer id);
}
