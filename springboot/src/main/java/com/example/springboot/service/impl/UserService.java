package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserMapper userMapper;

    //  查询所有
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    //  分页
    @Override
    public PageInfo<User> page(UserPageRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }

    //  添加
    @Override
    public void sava(User user) {
        Date date = new Date();
        //  生成卡号（当前时间 + 随机6位数字）
        user.setUsername(DateUtil.format(date,"yyyMMdd") + RandomUtil.randomNumbers(6));
        userMapper.sava(user);
    }

    //  修改（根据id查询）
    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    //  更新
    @Override
    public void update(User user) {
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    //  删除
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }
}
