package com.xl.parking.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XL
 * @date 2022/3/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StallVo {

    private String carArea;

    private String carType;

    private String carState;

    private Integer pagenum;

    private Integer pageSize;
}
