package com.xl.parking.service.impl;

import cn.dev33.satoken.util.SaFoxUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.parking.domain.Car;
import com.xl.parking.domain.vo.CarVo;
import com.xl.parking.mapper.CarMapper;
import com.xl.parking.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    @Autowired
    private CarMapper carMapper;

    @Override
    public Object getList(CarVo carVo) {
        Page<Car> page = new Page<>(carVo.getPagenum(),carVo.getPageSize());
        QueryWrapper<Car> wrapper = new QueryWrapper<>();
        if(!SaFoxUtil.isEmpty(carVo.getPerson())){
            wrapper.like("person",carVo.getPerson());
        }
        if(!SaFoxUtil.isEmpty(carVo.getCard())){
            wrapper.like("card",carVo.getCard());
        }
        if(!SaFoxUtil.isEmpty(carVo.getCarType())){
            wrapper.like("car_type",carVo.getCarType());
        }

        return carMapper.selectPage(page,wrapper);
    }

    @Override
    public Object addCar(Car car) {
        Map<String,Object> res = new HashMap<>();
        Car car1 = carMapper.selectOne(new QueryWrapper<Car>().eq("card",car.getCard()).eq("person",car.getPerson()));
        if(SaFoxUtil.isEmpty(car1)){
            car.setXtime(LocalDateTime.now());
            int cou = carMapper.insert(car);
            if(cou>0){
                res.put("flag",true);
                res.put("msg","添加成功");
            }else{
                res.put("flag",false);
                res.put("msg","添加失败，请重新尝试");
            }
        }else{
            res.put("flag",false);
            res.put("msg","已存在该用户的车牌信息");
        }
        return res;
    }
}
