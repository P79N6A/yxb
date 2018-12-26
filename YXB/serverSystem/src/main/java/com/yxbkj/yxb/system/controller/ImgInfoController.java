package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.ImgInfoService;
import com.yxbkj.yxb.util.ValidateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 图片管理 前端控制器
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-03
 */
@RestController
@RequestMapping("/imgInfo")
public class ImgInfoController {
    @Autowired
    private ImgInfoService imgInfoService;
    /**
     * 作者: 唐漆
     * 描述:
     * 备注:
     * @param
     * @return
     */

    @GetMapping("/imgInfo")
    public Result<Map<String, Object>> imgInfo(@ApiParam(value = "位置信息",required = true)@RequestParam(value="addType")String addType){
        return imgInfoService.imgInfo(addType);
    }
}
