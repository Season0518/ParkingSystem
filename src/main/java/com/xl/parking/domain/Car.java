package com.xl.parking.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆编号
     */
    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    /**
     * 车牌号
     */
    private String card;

    /**
     * 车类别
     */
    @TableField("car_type")
    private String carType;

    /**
     * 用户
     */
    private String person;

    /**
     * 录入时间
     */
    private LocalDateTime xtime;


    @TableField(exist = false)
    private User user;


}
