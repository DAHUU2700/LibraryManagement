package com.example.springboot.mapper;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 查询所有
     * @return
     */
    List<User> list();

    /**
     * 条件查询
     * @param userPageRequest
     */
    List<User> listByCondition(UserPageRequest userPageRequest);
}
