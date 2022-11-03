package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    //  登录(根据username和password去查询)
    //    Admin getByUsernameAndPassword(LoginRequest loginRequest);
    Admin getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    //  修改密码
    int updatePassword(PasswordRequest request);

    //  登录(根据username去查询)
    Admin getByUsername(String username);
}
