package com.xl.parking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xl.parking.domain.User;
import com.xl.parking.domain.vo.UserVo;
import com.xl.parking.service.UserService;
import com.xl.parking.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  用户前端控制器
 * </p>
 * @author Administrator
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
  @PostMapping("/login")
  public ResultJson<Object> login(@RequestBody User user){
      return ResultJson.success(userService.login(user));
  }

    /**
     * 注册
     */
  @PostMapping("/register")
  public ResultJson<Boolean> register(@RequestBody User user){
      return ResultJson.success(userService.register(user));
  }

  @GetMapping("/checkUsername")
  public ResultJson<Boolean> checkUsername(String username){
      return ResultJson.success(userService.getOne(new QueryWrapper<User>().eq("username",username))==null);
  }

    /**
     * 新增
     */
  @PostMapping("/add")
  public ResultJson<Boolean> addUser(@RequestBody User user){
      return ResultJson.success(userService.add(user));
  }

    /**
     * 根据用户ID删除用户信息
     */
  @GetMapping("/del")
  public ResultJson<Boolean> delUser(Integer uid){
      return ResultJson.success(userService.delUser(uid));
  }

    /**
     * 模糊查询用户
     */
  @GetMapping("/detail")
  public ResultJson<Object> getUser(User user){
      return ResultJson.success(userService.getUsers(user));
  }

  @PostMapping("/getUsers")
  public ResultJson<Object> getUsersPage(@RequestBody UserVo userVo){
      return ResultJson.success(userService.getUsersPage(userVo));
  }

    /**
     * 更改用户信息
     */
  @PostMapping("/update")
  public ResultJson<Boolean> update(@RequestBody User user){
      return ResultJson.success(userService.updateUser(user));
  }

    /**
     * 用户更改个人信息
     */
  @PostMapping("/updateByUser")
  public ResultJson<Boolean> updateByUser(@RequestBody User user){
      return ResultJson.success(userService.updateByUser(user));
  }

    /**
     * 重置密码
     */
  @GetMapping("/release")
  public ResultJson<Boolean> updatePassword(Integer uid){
      return ResultJson.success(userService.release(uid));
  }


  @GetMapping("/getUserByUid")
  public ResultJson<User> gerUserByUid(Integer uid){
      return ResultJson.success(userService.getById(uid));
  }


  @PostMapping("/userPay")
  public ResultJson<Boolean> userPay(@RequestBody User user){
      return ResultJson.success(userService.userPay(user));
  }


  @GetMapping("/dataType")
  public ResultJson<Object> getUserResCo(String person){
      return ResultJson.success(userService.getUserDataType(person));
  }

    @GetMapping("/dataMoney")
    public ResultJson<Object> getUserMoney(String person){
        return ResultJson.success(userService.getUserDataMoney(person));
    }

}

