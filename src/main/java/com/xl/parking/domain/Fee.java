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
public class Fee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收费标准ID
     */
    @TableId(value = "fid", type = IdType.AUTO)
    private Integer fid;

    /**
     * 车位类型
     */
    private String carType;

    /**
     * 价格
     */
    private Double money;

    /**
     * 价格描述
     */
    private String moneyDesc;

    /**
     * 创建时间
     */
    private LocalDateTime feeTime;


}
