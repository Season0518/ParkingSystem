package com.xl.parking.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录日志编号
     */
    @TableId(value = "yid", type = IdType.AUTO)
    private Integer yid;

    /**
     * 用户
     */
    private String person;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 网络地址
     */
    private String ipLocation;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;


}
