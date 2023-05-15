package com.xl.parking.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2022-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Stall implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车位编号
     */
    @TableId(value = "sid", type = IdType.AUTO)
    private Integer sid;

    /**
     * 车位号
     */
    @TableField("stall_num")
    private String stallNum;

    /**
     * 车位区域
     */
    @TableField("stall_area")
    private String stallArea;

    /**
     * 车位类型
     */
    @TableField("stall_type")
    private String stallType;

    /**
     * 车位状态
     */
    @TableField("stall_state")
    private String stallState;

    /**
     * 车位收费
     */
    @TableField("stall_money")
    private Double stallMoney;

    /**
     *  逻辑删除标志
     */
    @TableField("stall_live")
    private String stallLive;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Integer userId;

    @TableField(exist = false)
    private User user;


}
