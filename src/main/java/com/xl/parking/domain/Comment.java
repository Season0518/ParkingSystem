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
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论编号
     */
    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    /**
     * 评论内容
     */
    private String resCont;

    /**
     * 用户
     */
    private String person;

    /**
     * 社区
     */
    private String comArea;

    /**
     * 评论时间
     */
    private LocalDateTime rtime;


}
