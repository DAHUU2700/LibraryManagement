package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BorrowPageRequest;
import com.example.springboot.entity.Restore;
import com.example.springboot.service.IRestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/restore")
public class RestoreController {
    @Autowired
    IRestoreService restoreService;

    //  根据条件查询
    @GetMapping("/page")
    public Result page(BorrowPageRequest pageRequest) {
        //  BorrowPageRequest复用，相同的查询条件
        return Result.success(restoreService.page(pageRequest));
    }

    //  添加
    @PostMapping("/save")
    public Result save(@RequestBody Restore obj) {
        restoreService.save(obj);
        return Result.success();
    }

    //  删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        restoreService.deleteById(id);
        return Result.success();
    }

    /**
     * 首页数据
     * @param timeRange:week month month2 month3
     * @return
     */
    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange) {
        return Result.success(restoreService.getCountByTimeRange(timeRange));
    }
}
