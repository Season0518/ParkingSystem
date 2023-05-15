package com.xl.parking.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author XL
 * @date 2022/4/8
 */
@Data
public class StallResVo {

    private String person;

    private String stallArea;

    private LocalDateTime inTime;

    private LocalDateTime outTime;

    private Integer pageSize;

    private Integer pagenum;
}
