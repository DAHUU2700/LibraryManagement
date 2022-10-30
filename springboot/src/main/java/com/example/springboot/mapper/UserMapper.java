package com.example.springboot.mapper;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

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

    /**
     * 添加
     * @param user
     */
    void sava(User user);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 更新
     * @param user
     */
    void updateById(User user);

    /**
     * 删除
     * @param id
     */
    void deleteById(Integer id);
}
