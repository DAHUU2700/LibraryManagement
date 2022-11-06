package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    //  查询所有
    List<Book> list();

    //  条件查询
    List<Book> listByCondition(BaseRequest baseRequest);

    //  添加
    void save(Book book);

    //  根据id查询
    Book getById(Integer id);

    //  更新
    void updateById(Book book);

    //  删除
    void deleteById(Integer id);

    //  校验积分
    Book getByNo(String userNo);
}
