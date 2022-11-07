package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BorrowPageRequest;
import com.example.springboot.entity.Restore;
import com.example.springboot.service.IRestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/return")
public class ReturnController {
    @Autowired
    IRestoreService returnService;

    //  根据条件查询
    @GetMapping("/page")
    public Result page(BorrowPageRequest pageRequest) {
        //  BorrowPageRequest复用，相同的查询条件
        return Result.success(returnService.page(pageRequest));
    }

    //  添加
    @PostMapping("/save")
    public Result save(@RequestBody Restore obj) {
        returnService.save(obj);
        return Result.success();
    }

    //  删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        returnService.deleteById(id);
        return Result.success();
    }
}
