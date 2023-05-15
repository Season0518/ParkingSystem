package com.xl.parking.service.impl;

import cn.dev33.satoken.util.SaFoxUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.parking.domain.*;
import com.xl.parking.domain.vo.StallResVo;
import com.xl.parking.domain.vo.StallVo;
import com.xl.parking.mapper.*;
import com.xl.parking.service.StallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-22
 */
@Service
public class StallServiceImpl extends ServiceImpl<StallMapper, Stall> implements StallService {

    @Autowired
    private StallMapper stallMapper;

    @Autowired
    private StallResMapper stallResMapper;

    @Autowired
    private StallTypeMapper stallTypeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RechargeMapper rechargeMapper;

    @Override
    public Object getStallList(StallVo stallVo) {
        Page<Stall> page = new Page<>(stallVo.getPagenum(),stallVo.getPageSize());
        //查询条件
        QueryWrapper<Stall> wrapper =new QueryWrapper<>();
        if(!SaFoxUtil.isEmpty(stallVo.getCarArea())){
            wrapper.eq("stall_area",stallVo.getCarArea());
        }
        if(!SaFoxUtil.isEmpty(stallVo.getCarState())){
            wrapper.eq("stall_state",stallVo.getCarState());
        }
        if(!SaFoxUtil.isEmpty(stallVo.getCarType())){
            wrapper.eq("stall_type",stallVo.getCarType());
        }
        wrapper.eq("stall_live","1");
        return stallMapper.selectPage(page,wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object orderStall(Integer uid, Integer sid) {
        Map<String,Object> res = new HashMap<>();
        Stall stall = stallMapper.selectById(sid);
        if(SaFoxUtil.isEmpty(stall.getUserId())){
            stall.setUserId(uid);
            stall.setStallState("已停车");
            stallMapper.updateById(stall);
            StallRes stallRes = new StallRes();
            User user = userMapper.selectById(uid);
            stallRes.setPerson(user.getUsername());
            stallRes.setStallId(sid);
            stallRes.setCreateTime(LocalDateTime.now());
            stallResMapper.insert(stallRes);
            return true;
        }
        return false;
    }

    @Override
    public Object addStall(Stall stall) {
        Map<String,Object> res = new HashMap<>();
        StallType stallType = stallTypeMapper.selectOne(new QueryWrapper<StallType>().eq("otype",stall.getStallType()));
        Stall stall1 = stallMapper.selectOne(new QueryWrapper<Stall>().eq("stall_num",stall.getStallNum()).eq("stall_area",stall.getStallArea()).eq("stall_type",stall.getStallType()));
        if(SaFoxUtil.isEmpty(stall1)){
            stall.setStallState("空闲中");
            stall.setStallLive("1");
            stall.setStallMoney(stallType.getOmoney());
            int cou = stallMapper.insert(stall);
            if(cou >0){
                res.put("flag",true);
                res.put("msg","添加成功");
            }else{
                res.put("flag",false);
                res.put("msg","添加失败，请重新尝试");
            }
        }else{
            res.put("flag",false);
            res.put("msg","该车位已经存在");
        }
        return res;
    }

    @Override
    public Object updateStall(Stall stall) {
        Map<String,Object> res = new HashMap<>();
        Stall stall1 = stallMapper.selectOne(new QueryWrapper<Stall>().eq("stall_num",stall.getStallNum()).eq("stall_area",stall.getStallArea()));
        if(SaFoxUtil.isEmpty(stall1)){
            int cou = stallMapper.updateById(stall);
            if(cou>0){
                res.put("flag",true);
                res.put("msg","修改成功");
            }else{
                res.put("flag",false);
                res.put("msg","修改失败，请重新操作");
            }
        }else{
            res.put("flag",false);
            res.put("msg",stall.getStallArea()+"已有该车位");
        }
        return res;
    }

    @Override
    public Object getAllStallRes(String person) {
        return stallResMapper.getAllStallRes(person);
    }

    @Override
    public Object getAllListStallRes(StallResVo stallResVo) {
        Page<StallRes> page = new Page<>(stallResVo.getPagenum(),stallResVo.getPageSize());
        return stallResMapper.getAllListStallRes(page,stallResVo.getPerson(),stallResVo.getInTime(),stallResVo.getOutTime(),stallResVo.getStallArea());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object payMoneyPerson(StallRes stallRes) {
        Map<String,Object> res = new HashMap<>();
        res.put("flag",false);
        res.put("msg","缴费失败!");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",stallRes.getPerson()));
        if(!SaFoxUtil.isEmpty(user)){
            if(user.getMoney()>=stallRes.getMoney()){
                user.setMoney(user.getMoney()-stallRes.getMoney());
                userMapper.updateById(user);

                stallMapper.setStallOrg(stallRes.getStallId());

                StallRes stallRes1 = new StallRes();
                stallRes1.setPid(stallRes.getPid());
                stallRes1.setOverTime(LocalDateTime.now());
                stallRes1.setMoney(stallRes.getMoney());
                stallResMapper.updateById(stallRes1);

                Recharge recharge = new Recharge();
                recharge.setMoney(stallRes.getMoney());
                recharge.setPerson(stallRes.getPerson());
                recharge.setCtime(LocalDateTime.now());
                rechargeMapper.insert(recharge);
                res.put("flag",true);
                res.put("msg","缴费成功");
            }else{
                res.put("msg","余额不足，请先充值");
            }

            return res;
        }

        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object payMoneyManager(StallRes stallRes) {

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",stallRes.getPerson()));
        if(!SaFoxUtil.isEmpty(user)){
            user.setMoney(user.getMoney()-stallRes.getMoney());
            userMapper.updateById(user);

            stallMapper.setStallOrg(stallRes.getStallId());

            StallRes stallRes1 = new StallRes();
            stallRes1.setPid(stallRes.getPid());
            stallRes1.setOverTime(LocalDateTime.now());
            stallRes1.setMoney(stallRes.getMoney());
            stallResMapper.updateById(stallRes1);

            Recharge recharge = new Recharge();
            recharge.setMoney(stallRes.getMoney());
            recharge.setPerson(stallRes.getPerson());
            recharge.setCtime(LocalDateTime.now());
            rechargeMapper.insert(recharge);

            return true;
        }

        return false;
    }

    @Override
    public List<StallRes> getAllNoPay(String person) {
        return stallResMapper.getAllStallRes(person);
    }
}
