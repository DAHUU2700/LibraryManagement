package com.example.springboot.controller.request;

import lombok.Data;

/**
 *  传入登录请求参数
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
