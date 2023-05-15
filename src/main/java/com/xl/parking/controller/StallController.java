package com.xl.parking.controller;


import com.xl.parking.domain.Stall;
import com.xl.parking.domain.StallRes;
import com.xl.parking.domain.vo.StallResVo;
import com.xl.parking.domain.vo.StallVo;
import com.xl.parking.service.StallService;
import com.xl.parking.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-22
 */
@RestController
@RequestMapping("/stall")
public class StallController {

    @Autowired
    private StallService stallService;

    @PostMapping("/getStallList")
    public ResultJson<Object> getStallList(@RequestBody StallVo stallVo){
        return ResultJson.success(stallService.getStallList(stallVo));
    }

    @GetMapping("/orderStall")
    public ResultJson<Object> orderStall(Integer uid, Integer sid){
        return ResultJson.success(stallService.orderStall(uid,sid));
    }

    @PostMapping("/add")
    public ResultJson<Object> addStall(@RequestBody Stall stall){
        return ResultJson.success(stallService.addStall(stall));
    }

    @PostMapping("/update")
    public ResultJson<Object> updateStall(@RequestBody Stall stall){
        return ResultJson.success(stallService.updateStall(stall));
    }

    @GetMapping("/del")
    public ResultJson<Object> deleteStall(Integer sid){
        Stall stall = new Stall();
        stall.setSid(sid);
        stall.setStallLive("0");
        return ResultJson.success(stallService.updateById(stall));
    }

    @GetMapping("/allRes")
    public ResultJson<Object> allStallRes(String person){
        return ResultJson.success(stallService.getAllStallRes(person));
    }

    @GetMapping("/allNoPay")
    public ResultJson<Object> allNoPay(String person){
        return ResultJson.success(stallService.getAllNoPay(person).stream().filter(r -> r.getOverTime()==null));
    }

    @PostMapping("/allList")
    public ResultJson<Object> allStallResList(@RequestBody StallResVo stallResVo){
        return ResultJson.success(stallService.getAllListStallRes(stallResVo));
    }

    @PostMapping("/payMoney")
    public ResultJson<Object> payMoney(@RequestBody StallRes stallRes){
        return ResultJson.success(stallService.payMoneyManager(stallRes));
    }

    @PostMapping("/payMoneyPerson")
    public ResultJson<Object> payMoneyPerson(@RequestBody StallRes stallRes){
        return ResultJson.success(stallService.payMoneyPerson(stallRes));
    }



}

