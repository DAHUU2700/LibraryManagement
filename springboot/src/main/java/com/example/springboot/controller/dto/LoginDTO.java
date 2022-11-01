package com.example.springboot.controller.dto;

import lombok.Data;

//  专门存放返回值dto
@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String phone;
    private String email;
}
