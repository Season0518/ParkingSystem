package com.xl.parking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xl.parking.domain.User;
import com.xl.parking.domain.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-21
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param user 用户
     * @return Object
     */
    Object login(User user);

    /**
     * 用户注册
     * @param user 用户信息
     * @return boolean
     */
    boolean register(User user);

    /**
     * 新增用户
     * @param  user 用户信息
     * @return boolean
     */
    boolean add(User user);

    /**
     *  删除用户
     * @param uid 用户id
     * @return boolean
     */
    boolean delUser(Integer uid);

    /**
     * 模糊查询用户
     * @param user 用户信息
     * @return Object
     */
    Object getUsers(User user);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return boolean
     */
    boolean updateUser(User user);

    /**
     * 用户更新个人信息
     * @param user 用户信息
     * @return 用户
     */
    boolean updateByUser(User user);

    /**
     * 重置密码
     * @param uid 用户编号
     * @return boolean
     */
    boolean release(Integer uid);

    /**
     * 分页获取用户
     * @param userVo 条件信息
     * @return Object
     */
    Object getUsersPage(UserVo userVo);

    /**
     * 用户充值
     * @param user 用户信息
     * @return Boolean
     */
    Boolean userPay(User user);

    /**
     * 获取用户停车类型记录
     * @param person 用户
     * @return Object
     */
    Object getUserDataType(String person);

    /**
     * 获取用户缴费记录
     * @param person 用户
     * @return Object
     */
    Object getUserDataMoney(String person);
}
