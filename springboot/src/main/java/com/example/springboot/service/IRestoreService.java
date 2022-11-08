package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Restore;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IRestoreService {

    //  分页
    PageInfo<Restore> page(BaseRequest baseRequest);

    //  添加
    void save(Restore obj);

    //  删除
    void deleteById(Integer id);

    //  首页数据
    Map<String, Object> getCountByTimeRange(String timeRange);
}
