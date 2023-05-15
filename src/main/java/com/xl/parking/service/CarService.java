package com.xl.parking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xl.parking.domain.Car;
import com.xl.parking.domain.vo.CarVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface CarService extends IService<Car> {

    /**
     * 获取车辆信息
     * @param carVo 车辆实体
     * @return Object
     */
    Object getList(CarVo carVo);

    /**
     * 新增车辆信息
     * @param car 车辆信息
     * @return Object
     */
    Object addCar(Car car);
}
