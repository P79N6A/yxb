package com.yxbkj.yxb.member.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.ZrpxParam;
import com.yxbkj.yxb.member.service.ZhongruiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 中锐保险控制器 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-17
 */
@RestController
@RequestMapping("/zhongrui")
public class ZhongruiController {

    @Autowired
    private ZhongruiService zhongruiService;

    /**
     * 作者: 李明
     * 描述: 中瑞认证接口
     * 备注:
     * @param param
     * @return
     */
    @ApiOperation(value = "中瑞认证接口",notes = "中瑞认证接口")
    @PostMapping("/baseAuth")
    public Result<Map<String, Object>> baseAuth(@RequestBody ZrpxParam param){
        return zhongruiService.baseAuth(param);
    }
}
