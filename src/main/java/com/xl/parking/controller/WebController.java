package com.xl.parking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Controller
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "index"; // 这里的 "index" 是你的Vue.js应用的index.html文件
    }
}