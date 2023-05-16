package com.xl.parking.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.parking.domain.Car;
import com.xl.parking.domain.Stall;
import com.xl.parking.domain.vo.CarVo;
import com.xl.parking.domain.vo.StallCarVo;
import com.xl.parking.mapper.StallMapper;
import com.xl.parking.service.CarService;
import com.xl.parking.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private StallMapper stallMapper;

    @PostMapping("/getList")
    public ResultJson<Object> getList(@RequestBody CarVo carVo){
        return ResultJson.success(carService.getList(carVo));
    }

    @PostMapping("/add")
    public ResultJson<Object> addCar(@RequestBody Car car){
        return ResultJson.success(carService.addCar(car));
    }

    @GetMapping("/del")
    public ResultJson<Boolean> del(Integer tid){
        return ResultJson.success(carService.removeById(tid));
    }

    @PostMapping("/update")
    public ResultJson<Object> updateCar(@RequestBody Car car){
        return ResultJson.success(carService.updateById(car));
    }


    @PostMapping("/getCar")
    public ResultJson<Object> getStallUser(@RequestBody StallCarVo stallCarVo){
        Page<Stall> page = new Page<>(stallCarVo.getPagenum(),stallCarVo.getPageSize());
        return ResultJson.success(stallMapper.getStallAll(page,stallCarVo.getNike(),stallCarVo.getCard()));
    }
}

