package com.xl.parking.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StallType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车位类型编号
     */
    @TableId(value = "oid", type = IdType.AUTO)
    private Integer oid;

    /**
     * 车位类型
     */
    private String otype;

    /**
     * 车位价格
     */
    private Double omoney;


}
