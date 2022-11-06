package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BorrowMapper {
    //  查询所有
    List<Borrow> list();

    //  条件查询
    List<Borrow> listByCondition(BaseRequest baseRequest);

    //  添加
    void save(Borrow borrow);

    //  根据id查询
    Borrow getById(Integer id);

    //  更新
    void updateById(Borrow borrow);

    //  删除
    void deleteById(Integer id);
}
