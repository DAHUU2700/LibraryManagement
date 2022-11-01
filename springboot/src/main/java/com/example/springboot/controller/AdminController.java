package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin    //  跨域
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IAdminService adminService;

    //  查询所有
    @GetMapping("/list")
    public Result list() {
        List<Admin> list = adminService.list();
        return Result.success(list);
    }

    //  分页
    @GetMapping("/page")
    public Result page(AdminPageRequest adminPageRequest) {
        return Result.success(adminService.page(adminPageRequest));
    }

    //  添加
    @PostMapping("/sava")   //这里不加，也可以直接通过"/"访问
    public Result save(@RequestBody Admin admin) {
        adminService.save(admin);
        return Result.success();
    }

    //  （根据id查询）
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    //  更新
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }

    //  删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.deleteById(id);
        return Result.success();
    }

    //  登录（是基于Admin的，所以写这）
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        return Result.success(adminService.login(loginRequest));
    }
}
