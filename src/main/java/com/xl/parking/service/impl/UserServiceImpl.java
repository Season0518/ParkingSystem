package com.xl.parking.service.impl;

import cn.dev33.satoken.util.SaFoxUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.parking.domain.LoginInfo;
import com.xl.parking.domain.User;
import com.xl.parking.domain.vo.UserVo;
import com.xl.parking.mapper.LoginInfoMapper;
import com.xl.parking.mapper.UserMapper;
import com.xl.parking.service.UserService;
import com.xl.parking.utils.MyUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-03-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    public Object login(User user) {
        Map<String,Object> res = new HashMap<>();
        boolean flag = false;
        String msg = "用户名或密码不允许为空！";
        if(user!=null&&user.getUsername()!=null&&user.getPassword()!=null){
            User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("username",user.getUsername()));
            if(user1!=null){
                if(user.getPassword().equals(user1.getPassword())){
                    flag = true;
                    res.put("uid",user1.getUid());
                    msg="验证成功";
                    recommendLoginInfo(user1.getUsername(), MyUtils.getRequest());
                }else{
                    msg = "密码不正确";
                }
            }else{
                msg = "用户名不存在";
            }

        }
        res.put("state",flag);
        res.put("msg",msg);
        return res;
    }

    /**
     * 记录登录日志
     */
    public void recommendLoginInfo(String username, HttpServletRequest request){
        LoginInfo loginInfo = new LoginInfo();
        // 获取客户端IP
        String ip = MyUtils.getIpAddress(request);
        // 获取地理位置1
        String location = MyUtils.getLocationByIp(ip);
        // 获取客户端操作信息
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        loginInfo.setIp(ip);
        loginInfo.setLoginTime(LocalDateTime.now());
        loginInfo.setBrowser(userAgent.getBrowser().getName());
        loginInfo.setIpLocation(location);
        loginInfo.setOs(userAgent.getOperatingSystem().getName());
        loginInfo.setPerson(username);
        loginInfoMapper.insert(loginInfo);
    }

    @Override
    public boolean register(User user) {
        boolean flag = false;
        if(user!=null){
            if(userMapper.selectOne(new QueryWrapper<User>().eq("username",user.getUsername()))==null){
                flag = userMapper.insert(user)>0;
            }
        }
        return flag;
    }

    @Override
    public boolean add(User user) {
        if(!SaFoxUtil.isEmpty(user)){
            if(!SaFoxUtil.isEmpty(user.getUsername())){
                User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("username",user.getUsername()));
                if(SaFoxUtil.isEmpty(user1)){
                    int res = userMapper.insert(user);
                    return  res > 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean delUser(Integer uid) {
        return userMapper.deleteById(uid) > 0;
    }

    @Override
    public Object getUsers(User user) {
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean updateByUser(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean release(Integer uid) {
        User user = new User();
        user.setUid(uid);
        user.setPassword("123456");
        return userMapper.updateById(user) > 0;
    }

    @Override
    public Object getUsersPage(UserVo userVo) {
        Page<User> page = new Page<>(userVo.getPagenum(),userVo.getPageSize());
        //查询条件
        QueryWrapper<User> wrapper =new QueryWrapper<>();
        if(!SaFoxUtil.isEmpty(userVo.getUsername())){
            wrapper.like("username",userVo.getUsername());
        }
        if(!SaFoxUtil.isEmpty(userVo.getNike())){
            wrapper.like("nike",userVo.getNike());
        }
        if(!SaFoxUtil.isEmpty(userVo.getCard())){
            wrapper.like("card",userVo.getCard());
        }

        return userMapper.selectPage(page,wrapper);
    }

    @Override
    public Boolean userPay(User user) {
        User user1 = userMapper.selectById(user.getUid());
        if(!SaFoxUtil.isEmpty(user1.getMoney())){
           user.setMoney(user1.getMoney()+user.getMoney());
        }
        return userMapper.updateById(user)>0;
    }

    @Override
    public Object getUserDataType(String person) {
        return userMapper.getUserDataType(person);
    }

    @Override
    public Object getUserDataMoney(String person) {
        return userMapper.getUserDataMoney(person);
    }
}
