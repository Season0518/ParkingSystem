package com.xl.parking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xl.parking.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.redis.connection.DataType;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-03-21
 */
public interface UserMapper extends BaseMapper<User> {

    List<DataType> getUserDataType(@Param("person")String person);

    List<DataType> getUserDataMoney(@Param("person")String person);
}
