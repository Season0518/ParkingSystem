package com.xl.parking.domain.vo;

import lombok.Data;

/**
 * @author XL
 * 地理位置信息实体
 * @date 2022/1/12 15:14
 */
@Data
public class IpAddress {

    private String ip;

    private String pro;

    private String proCode;

    private String city;

    private String cityCode;

    private String region;

    private String addr;

    private String regionNames;

    private String err;
}
