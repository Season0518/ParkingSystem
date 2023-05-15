package com.xl.parking.controller;


import cn.dev33.satoken.util.SaFoxUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xl.parking.domain.*;
import com.xl.parking.service.*;
import com.xl.parking.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/fee")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @Autowired
    private StallTypeService stallTypeService;

    @Autowired
    private StallService stallService;

    @Autowired
    private UserService userService;

    @Autowired
    private RechargeService rechargeService;

    @GetMapping("/list")
    public ResultJson<List<Fee>> getListFee(String carType){
        return ResultJson.success(feeService.list(new QueryWrapper<Fee>().like("car_type",carType)));
    }

    @PostMapping("update")
    public ResultJson<Object> updateFee(@RequestBody Fee fee){
        Map<String,Object> res = new HashMap<>();
        res.put("flag",false);
        res.put("msg","更新失败，请重新操作！");
        if(!SaFoxUtil.isEmpty(fee.getCarType())&&!SaFoxUtil.isEmpty(fee.getMoney())){
            if(feeService.update(fee,new QueryWrapper<Fee>().eq("car_type",fee.getCarType()))){
                res.put("flag",true);
                res.put("msg","更新成功！");
                Stall stall = new Stall();
                stall.setStallType(fee.getCarType());
                stall.setStallMoney(fee.getMoney());
                stallService.update(stall,new QueryWrapper<Stall>().eq("stall_type",fee.getCarType()));

                StallType stallType = new StallType();
                stallType.setOmoney(fee.getMoney());
                stallType.setOtype(fee.getCarType());
                stallTypeService.update(stallType,new QueryWrapper<StallType>().eq("otype",fee.getCarType()));
            }
        }else{
            res.put("msg","更新失败，填写不完善");
        }
        return ResultJson.success(res);
    }


    @PostMapping("/userFee")
    public ResultJson<Object> feeUser(@RequestBody User user){
        Map<String,Object> res = new HashMap<>();
        res.put("flag",false);
        res.put("msg","充值失败，请重新尝试");
        if(!SaFoxUtil.isEmpty(user.getUsername())&&!SaFoxUtil.isEmpty(user.getMoney())){
            User user1 = userService.getOne(new QueryWrapper<User>().eq("username",user.getUsername()));
            if(!SaFoxUtil.isEmpty(user1)){
                double mon = user.getMoney();
                double non = user1.getMoney()==null ? 0 : user1.getMoney();
                user.setMoney(non+user.getMoney());
                if(userService.update(user,new QueryWrapper<User>().eq("username",user.getUsername()))){
                    res.put("flag",true);
                    res.put("msg","充值成功");

                    Recharge recharge = new Recharge();
                    recharge.setCtime(LocalDateTime.now());
                    recharge.setPerson(user.getUsername());
                    recharge.setMoney(mon);
                    rechargeService.save(recharge);
                }
            }else{
                res.put("msg","充值失败,用户不存在");
            }
        }else{
            res.put("msg","充值失败，填写不完善");
        }
        return ResultJson.success(res);
    }

}

