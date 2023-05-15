package com.xl.parking.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XL
 * 全局异常处理,全局异常拦截（拦截项目中的所有异常）
 * @date 2022/1/11 16:26
 */
@Slf4j
@ControllerAdvice
public class GlobalException {

    @ResponseBody
    @ExceptionHandler
    public ResultJson<Object> handlerException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 不同异常返回不同状态码
        ResultJson<Object> aj = null;
        // 日志打印错误异常
        String msg = "错误异常";

        // 打印堆栈，以供调试
        e.printStackTrace();
        aj = ResultJson.error(e.getMessage());
        msg = "系统异常";
        // 返回给前端
        return aj;
    }
}
