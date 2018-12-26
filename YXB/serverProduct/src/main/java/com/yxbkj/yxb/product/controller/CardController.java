package com.yxbkj.yxb.product.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.product.service.CardService;
import com.yxbkj.yxb.util.AccessToken;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;
    @GetMapping("/card")
    @AccessToken
    public Result<Map<String, Object>> card (@ApiParam(value = "产品ID")@RequestParam(value = "productId",required = true)String productId,
                                             @ApiParam(value = "交易金额")@RequestParam(value = "amount",required = true)BigDecimal amount,
                                             @ApiParam(value = "车牌号")@RequestParam(value = "plateNumber",required = true)String plateNumber,
                                             @ApiParam(value = "令牌",required = true)@RequestParam(value="token", required = true)String token,
                                             @ApiParam(value = "投保人名字")@RequestParam(value = "policyHolder",required = true)String policyHolder,
                                             @ApiParam(value = "投保人身份证")@RequestParam(value = "policyCard",required = true)String policyCard,
                                             @ApiParam(value = "VIN码")@RequestParam(value = "chassisNumber",required = true)String chassisNumber,
                                             @ApiParam(value = "投保人手机号")@RequestParam(value = "policyPhone",required = true)String policyPhone,
                                             @ApiParam(value = "核定载客人数")@RequestParam(value = "number",required = true)String number,
                                             @ApiParam(value = "来源")@RequestParam(value = "source",required = true)String source
    ){
        return cardService.insertCard(productId, amount, plateNumber, token, policyHolder,
                policyCard, chassisNumber, policyPhone, number, source);
    }

}
