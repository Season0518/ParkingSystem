package com.xl.parking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xl.parking.domain.Stall;
import com.xl.parking.domain.StallRes;
import com.xl.parking.domain.vo.StallResVo;
import com.xl.parking.domain.vo.StallVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-22
 */
public interface StallService extends IService<Stall> {

    /**
     * 分页获取
     * @param stallVo 参数
     * @return Object
     */
    Object getStallList(StallVo stallVo);

    /**
     * 预定车位
     * @param uid 用户编号
     * @param sid 车位编号
     * @return Object
     */
    Object orderStall(Integer uid,Integer sid);

    /**
     * 添加车位
     * @param stall 车位信息
     * @return Object
     */
    Object addStall(Stall stall);

    /**
     * 更新车位
     * @param stall 车位信息
     * @return Object
     */
    Object updateStall(Stall stall);

    /**
     * 获取用户所有停车记录
     * @param person 用户
     * @return Object
     */
    Object getAllStallRes(String person);

    /**
     * 获取所有停车记录
     * @param stallResVo 查询信息
     * @return Object
     */
    Object getAllListStallRes(StallResVo stallResVo);

    /**
     * 停车缴费
     * @param stallRes 停车信息
     * @return Object
     */
    Object payMoneyPerson(StallRes stallRes);

    /**
     * 停车缴费
     * @param stallRes 停车信息
     * @return Object
     */
    Object payMoneyManager(StallRes stallRes);

    /**
     * 获取代缴费清单
     * @param person  用户ID
     * @return List
     */
    List<StallRes> getAllNoPay(String person);
}
