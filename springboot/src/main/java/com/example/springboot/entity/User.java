package com.example.springboot.entity;

import lombok.Data;

//  简化实体类的书写
@Data
public class User {
    private Integer id;
    private String name;
    private String username;
    private Integer age;
    private String sex;
    private String phone;
    private String address;
}
