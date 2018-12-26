package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(value = "ImgInfoController",description = "banner图片")
@RestController
@RequestMapping("/imgInfo")
public class ImgInfoController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    /**
     * 作者: 唐漆
     * 描述:
     * 备注:
     * @param addType
     * @return
     */

    @ApiOperation(value = "获取图片信息",notes = "获取图片信息")
    @GetMapping("/imgInfo")
    public Result<Map<String, Object>> imgInfo(String addType){
        return serverSystemFeignClient.imgInfo(addType);
    }
}
