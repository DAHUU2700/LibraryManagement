package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
public class BookService implements IBookService {

    @Resource
    BookMapper bookMapper;

    //  查询所有
    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    //  根据条件查询
    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        // 自关联查询
        List<Book> categories = bookMapper.listByCondition(baseRequest);
        return new PageInfo<>(categories);
    }

    //  新增
    @Override
    public void save(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            bookMapper.save(book);
        } catch (Exception e) {
            throw new ServiceException("数据插入错误",e);
        }

    }

    //  根据id查询
    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    //  更新
    @Override
    public void update(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            book.setUpdatetime(LocalDate.now());
            bookMapper.updateById(book);
        } catch (Exception e) {
            throw new ServiceException("数据更新错误");
        }


    }

    //  删除（根据id删除）
    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    //  设置分类（更新和新增的公用方法）
    private String category(List<String> categories) {
        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)) {
            categories.forEach(v -> sb.append(v).append(" > "));
            return sb.substring(0, sb.lastIndexOf(" > "));
        }
        return sb.toString();
    }
}
