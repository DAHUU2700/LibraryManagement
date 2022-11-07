package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Restore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RestoreMapper {

    //  条件查询
    public List<Restore> listCondition(BaseRequest baseRequest);

    //  添加
    public void save(Restore obj);

    //  删除
    void deleteById(Integer id);

    //  修改状态
    void updateStatus(@Param("status") String status,@Param("id") Integer id);
}
