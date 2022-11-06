package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BorrowService implements IBorrowService {

    @Resource
    BorrowMapper borrowMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    BookMapper bookMapper;

    //  查询所有
    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }

    //  根据条件查询
    @Override
    public PageInfo<Borrow> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        // 自关联查询
        List<Borrow> categories = borrowMapper.listByCondition(baseRequest);
        return new PageInfo<>(categories);
    }

    //  新增
    @Override
    @Transactional  //保证数据同时更新
    public void save(Borrow borrow) {
        //  校验
        //  1.用户的积分是否足够
        String userNo = borrow.getUserNo();
        User user = userMapper.getByUsername(userNo);
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");
        }
        //  2.图书数量是否足够
        Book book = bookMapper.getByNo(borrow.getBookNo());
        if (Objects.isNull(book)) {
            throw new ServiceException("所借图书不存在");
        }

        Integer account = user.getAccount();
        Integer score = book.getScore();
        //  3.用户账号余额
        if (score > account) {
            throw new ServiceException("用户积分不足");
        }

        //  4.图书数量
        if (book.getNums() < 1) {
            throw new ServiceException("图书数量不足");
        }
        //  更新余额
        user.setAccount(user.getAccount() - score);
        userMapper.updateById(user);
        //  更新图书数量
        book.setNums(book.getNums() - 1);
        bookMapper.updateById(book);
        //  新增借书记录


        borrowMapper.save(borrow);
    }

    //  根据id查询
    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    //  更新
    @Override
    public void update(Borrow borrow) {
        borrow.setUpdatetime(LocalDate.now());
        borrowMapper.updateById(borrow);
    }

    //  删除（根据id删除）
    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }
}
