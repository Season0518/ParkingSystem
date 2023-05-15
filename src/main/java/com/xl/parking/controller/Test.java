package com.xl.parking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XL
 * @description
 * @date 2022/5/18 23:41
 */
@RestController
@RequestMapping("/test")
public class Test {

    @GetMapping("/hello")
    public String test(){
        return "hello word client";
    }
}
