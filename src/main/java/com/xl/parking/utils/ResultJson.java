package com.xl.parking.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author XL
 * 统一返回JSON
 * @date 2022/1/11 16:03
 */
@ApiModel(value = "ResultJson返回结果",description = "统一json返回结果集")
public class ResultJson<T> implements Serializable {

    @ApiModelProperty("响应标志")
    private Boolean flag;
    @ApiModelProperty("响应状态码")
    private String code;
    @ApiModelProperty("提示信息")
    private String message;
    @ApiModelProperty("响应数据")
    private T data;

    private ResultJson() {
    }

    private ResultJson(Boolean flag, String code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    private ResultJson(Boolean flag, String code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 请求成功并有数据
     */
    public static <T> ResultJson<T> success(T data) {
        return new ResultJson<T>(ResultCode.SUCCESS.getState(),
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 请求成功没有数据
     */
    public static <T> ResultJson<T> success() {
        return new ResultJson<T>(ResultCode.SUCCESS.getState(),
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage());
    }

    /**
     * 请求失败有返回数据
     */
    public static <T> ResultJson<T> fail(T data) {
        return new ResultJson<T>(ResultCode.FAIL.getState(),
                ResultCode.FAIL.getCode(),
                ResultCode.FAIL.getMessage(), data);
    }

    /**
     * 请求失败有返回数据，自定义提示信息
     */
    public static <T> ResultJson<T> fail(String message, T data) {
        return new ResultJson<T>(ResultCode.FAIL.getState(),
                ResultCode.FAIL.getCode(),
               message, data);
    }

    /**
     * 请求失败无返回数据
     */
    public static <T> ResultJson<T> fail() {
        return new ResultJson<T>(ResultCode.FAIL.getState(),
                ResultCode.FAIL.getCode(),
                ResultCode.FAIL.getMessage());
    }

    /**
     * 系统异常
     */
    public static <T> ResultJson<T> error(T data) {
        return new ResultJson<T>(ResultCode.ERROR.getState(),
                ResultCode.ERROR.getCode(),
                ResultCode.ERROR.getMessage(), data);
    }

    /**
     * 自定义规范
     */
    public ResultJson<T> flag(Boolean flag) {
        this.flag = flag;
        return this;
    }

    public ResultJson<T> code(String code) {
        this.code = code;
        return this;
    }

    public ResultJson<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResultJson<T> data(T data) {
        this.data = data;
        return this;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
