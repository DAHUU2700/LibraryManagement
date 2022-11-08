package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class RestoreCount {
    private String date;
    private Integer count;
}
