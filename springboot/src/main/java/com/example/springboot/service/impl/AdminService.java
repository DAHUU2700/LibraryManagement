package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.service.IAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    //  查询所有
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    //  分页
    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admins);
    }

    //  添加
    @Override
    public void save(Admin admin) {
        adminMapper.sava(admin);
    }

    //  根据id查询
    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    //  更新
    @Override
    public void update(Admin admin) {
        admin.setUpdatetime(new Date());
        adminMapper.updateById(admin);
    }

    //  删除
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }
}
