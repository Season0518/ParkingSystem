package com.xl.parking.utils;

/**
 * @author XL
 * 返回码定义
 * @date 2022/1/11 15:57
 */
public enum  ResultCode {

    /* 成功 */
    SUCCESS(true,"200","成功"),
    /* 失败 */
    FAIL(false,"555","失败"),
    /* 系统异常 */
    ERROR(false,"666","系统发生异常");

    /** 返回状态 */
    private Boolean state;
    private String code;
    private String message;

    ResultCode(Boolean state,String code,String message){
        this.state = state;
        this.code = code;
        this.message = message;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
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
}
