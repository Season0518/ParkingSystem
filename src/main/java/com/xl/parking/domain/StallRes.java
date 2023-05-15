package com.xl.parking.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author XL
 * @date 2022/3/23
 */
@Data
@TableName("stall_res")
public class StallRes {


    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    @TableField("person")
    private String person;

    @TableField("stall_id")
    private Integer stallId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("over_time")
    private LocalDateTime overTime;

    @TableField("pay_money")
    private Double money;

    @TableField(exist = false)
    private Stall stall;
}
