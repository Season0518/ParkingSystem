package com.xl.parking.controller;


import com.xl.parking.domain.Community;
import com.xl.parking.service.CommunityService;
import com.xl.parking.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/list")
    public ResultJson<List<Community>> getCommunity(){
        return ResultJson.success(communityService.list());
    }
}

