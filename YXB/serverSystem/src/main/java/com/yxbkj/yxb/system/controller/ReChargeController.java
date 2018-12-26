package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.ReChargeService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zy
 * @desc
 * @since
 */
@RestController
@RequestMapping("/reChargeController")
public class ReChargeController {
    @Autowired
    private ReChargeService reChargeService;
    @PostMapping("/reChargeMoney")
    public Result<Map<String, Object>> reCharge(
            @ApiParam(value = "电话",required = true)@RequestParam(value = "phone",required = true,defaultValue = "") String  phone
            ,@ApiParam(value = "金额",required = true)@RequestParam(value = "cardNum",required = true) Integer cardNum) {
        return reChargeService.reCharge(phone,cardNum);
    }
}
