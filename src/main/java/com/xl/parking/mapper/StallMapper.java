package com.xl.parking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.parking.domain.Stall;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-03-22
 */
@Repository
public interface StallMapper extends BaseMapper<Stall> {

    int setStallOrg(@Param("sid")Integer sid);

    IPage<Stall> getStallAll(Page<Stall> page, @Param("nike")String nike, @Param("card")String card);
}
