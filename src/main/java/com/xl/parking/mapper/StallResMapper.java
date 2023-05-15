package com.xl.parking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.parking.domain.StallRes;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author XL
 * @date 2022/3/23
 */
public interface StallResMapper extends BaseMapper<StallRes> {

    List<StallRes> getAllStallRes(@Param("person")String person);

    IPage<StallRes> getAllListStallRes(Page<StallRes> page, @Param("person")String person,
                                       @Param("inTime")LocalDateTime inTime,
                                       @Param("outTime")LocalDateTime outTime,
                                       @Param("stallArea")String stallArea);
}
