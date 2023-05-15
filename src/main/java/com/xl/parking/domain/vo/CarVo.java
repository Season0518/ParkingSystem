package com.xl.parking.domain.vo;

import lombok.Data;

/**
 * @author XL
 * @description
 * @date 2022/4/2
 */
@Data
public class CarVo {

    private String person;

    private String carType;

    private String card;

    private Integer pagenum;

    private Integer pageSize;
}
