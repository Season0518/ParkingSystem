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
public class Recharge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充值编号
     */
    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    /**
     * 用户
     */
    private String person;

    /**
     * 充值金额
     */
    private Double money;

    /**
     * 充值时间
     */
    private LocalDateTime ctime;


}
