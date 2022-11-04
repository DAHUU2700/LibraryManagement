package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBookService {
    //  查询所有
    List<Book> list();

    //  分页
    PageInfo<Book> page(BaseRequest baseRequest);

    //  添加
    void save(Book book);

    //  根据id查询
    Book getById(Integer id);

    //  更新
    void update(Book book);

    //  删除
    void deleteById(Integer id);
}
