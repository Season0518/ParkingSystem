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
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息编号
     */
    @TableId(value = "mid", type = IdType.AUTO)
    private Integer mid;

    /**
     * 消息标题
     */
    private String msgTitle;

    /**
     * 消息内容
     */
    private String msgCont;

    /**
     * 用户
     */
    private String person;

    /**
     * 是否查看
     */
    private String msgSee;

    /**
     * 创建时间
     */
    private LocalDateTime mtime;


}
