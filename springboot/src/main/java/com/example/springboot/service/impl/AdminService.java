package com.example.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.service.IAdminService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//  实现类
@Slf4j
@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    private static final String DEFAULT_PASS = "123";
    private static final String DEFAULT_SALT = "DAHUU";


    //  查询所有
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    //  分页
    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admins);
    }

    //  添加
    @Override
    public void save(Admin admin) {
        //  设置默认密码
        if (StrUtil.isBlank(admin.getPassword())) {
            admin.setPassword(DEFAULT_PASS);
        }
        //  md5加密
        //admin.setPassword(SecureUtil.md5(admin.getPassword() + DEFAULT_SALT));
        admin.setPassword(SecurePass(admin.getPassword()));
        adminMapper.sava(admin);
    }

    //  根据id查询
    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    //  更新
    @Override
    public void update(Admin admin) {
        admin.setUpdatetime(new Date());
        adminMapper.updateById(admin);
    }

    //  删除
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    //  登录
    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        //  登录的时候，也需要加密，
        //loginRequest.setPassword(SecureUtil.md5(loginRequest.getPassword() + DEFAULT_SALT));
        loginRequest.setPassword(SecurePass(loginRequest.getPassword()));
        //  需要考虑异常！！
        Admin adminLoginUAP = adminMapper.getByUsernameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
        if (adminLoginUAP == null) {
            throw new ServiceException("用户名或密码错误");
        }
        if (!adminLoginUAP.isStatus()) {
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(adminLoginUAP,loginDTO);

        //  生成token
        String token = TokenUtils.genToken(String.valueOf(adminLoginUAP.getId()),adminLoginUAP.getPassword());
        loginDTO.setToken(token);

        return loginDTO;
    }

    //  修改密码
    @Override
    public void changePass(PasswordRequest request) {
        //  对新密码进行加密
        request.setNewPass(SecurePass(request.getNewPass()));
        int count = adminMapper.updatePassword(request);
        if (count <= 0) {
            throw new ServiceException("修改密码失败");
        }
    }

    //  封装-加密
    public String SecurePass(String password) {
        return SecureUtil.md5(password + DEFAULT_SALT);
    }
}
