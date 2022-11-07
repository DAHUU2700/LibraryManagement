package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
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

    @Override
    public void deleteById(Integer id) {
        restoreMapper.deleteById(id);
    }
}
