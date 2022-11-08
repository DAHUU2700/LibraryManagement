package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.RestoreCount;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Restore;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.RestoreMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IRestoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class RestoreService implements IRestoreService {
    @Resource
    RestoreMapper restoreMapper;

    @Resource
    BookMapper bookMapper;

    @Resource
    UserMapper userMapper;

    //  查询
    @Override
    public PageInfo<Restore> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        return new PageInfo<>(restoreMapper.listCondition(baseRequest));
    }

    //  还书
    @Transactional
    @Override
    public void save(Restore obj) {
        //  改状态
        obj.setStatus("已归还");
        obj.setRealDate(LocalDate.now());
        restoreMapper.updateStatus("已归还",obj.getId());

        //  图书的数量增加
        restoreMapper.save(obj);
        bookMapper.updateNumByNo(obj.getBookNo());

        // 返还和扣除用户积分
        Book book = bookMapper.getByNo(obj.getBookNo());
        if (book != null) {
            long until = 0;
            if (obj.getRealDate().isBefore(obj.getReturnDate())) {
                until = obj.getRealDate().until(obj.getReturnDate(), ChronoUnit.DAYS);
            } else if (obj.getRealDate().isAfter(obj.getReturnDate())) {
                // 逾期归还，要扣额外的积分
                until = -obj.getReturnDate().until(obj.getRealDate(), ChronoUnit.DAYS);
            }
            int score = (int) until * book.getScore();
            // 获取待归还的积分
            User user = userMapper.getByUsername(obj.getUserNo());
            int account = user.getAccount() + score;
            user.setAccount(account);
            if (account < 0) {
                // 锁定账号
                user.setStatus(false);
            }
            userMapper.updateById(user);
        }
    }

    //  删除
    @Override
    public void deleteById(Integer id) {
        restoreMapper.deleteById(id);
    }

    //  首页数据
    @Override
    public Map<String, Object> getCountByTimeRange(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange) {
            case "week":
                // offsetDay 计算时间的一个工具方法
                // rangeToList 返回从开始时间到结束时间的一个时间范围
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -6), today, DateField.DAY_OF_WEEK);
                break;
            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -29), today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -59), today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -89), today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        //  datetimeToDateStr() 就是一个处理的方法， 把 DateTime类型的List转换成一个 String的List
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date", dateStrRange);  // x轴的日期数据生产完毕

        //  timeRange = week  month
        // getCountByTimeRange 不会统计数据库没有的日期，比如 数据库 11.4 这一天数据没有，他不会返回 date=2022-11-04,count=0 这个数据
        List<RestoreCount> borrowCount = restoreMapper.getCountByTimeRange(timeRange, 1);   // 1 borrow  2 return
        List<RestoreCount> returnCount = restoreMapper.getCountByTimeRange(timeRange, 2);
        map.put("borrow", countList(borrowCount, dateStrRange));
        map.put("retur", countList(returnCount, dateStrRange));
        return map;
    }

    // 把 DateTime类型的List转换成一个 String的List
    private List<String> datetimeToDateStr(List<DateTime> dateTimeList) {
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)) {
            return list;
        }
        for (DateTime dateTime : dateTimeList) {
            String date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }

    // 对数据库未统计的时间进行处理
    private List<Integer> countList(List<RestoreCount> countPOList, List<String> dateRange) {
        List<Integer> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPOList)) {
            return list;
        }
        for (String date : dateRange) {
            // orElse(0) 对没匹配的数据返回0
            // "2022-11-04" 没有的话 就返回0
            Integer count = countPOList.stream().filter(countPO -> date.equals(countPO.getDate()))
                    // .map(RestoreCount::getCount) 取出 对象里的 count值
                    .map(RestoreCount::getCount).findFirst().orElse(0);
            list.add(count);
        }
        return list;
    }

}
