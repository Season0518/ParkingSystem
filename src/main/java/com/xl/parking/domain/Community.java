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
 * @since 2022-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Community implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 社区编号
     */
    @TableId(value = "zid", type = IdType.AUTO)
    private Integer zid;

    /**
     * 社区名称
     */
    private String zname;


}
