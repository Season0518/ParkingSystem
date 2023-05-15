package com.xl.parking.controller;


import com.xl.parking.service.StallTypeService;
import com.xl.parking.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/stallType")
public class StallTypeController {

    @Autowired
    private StallTypeService stallTypeService;

    @GetMapping("/list")
    public ResultJson<Object> getList(){
        return ResultJson.success(stallTypeService.list());
    }
}

