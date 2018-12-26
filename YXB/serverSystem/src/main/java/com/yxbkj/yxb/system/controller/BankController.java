package com.yxbkj.yxb.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxbkj.yxb.entity.app.Bank;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.CodeType;
import com.yxbkj.yxb.system.service.BankService;
import com.yxbkj.yxb.system.service.ConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 银行表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-21
 */
@RestController
@RequestMapping("/bank")
public class BankController{
    @Autowired
    private BankService bankService;
    @Autowired
    private ConfigService configService;

    /**
     * 作者: 李明
     * 描述: 获取所有银行信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取所有银行信息",notes = "获取所有银行信息")
    @GetMapping("/getAllBank")
    public Result<List<Bank>> getAllBank(){
        EntityWrapper<Bank> bankWrapper = new EntityWrapper<>();
        bankWrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        List<Bank> banks = bankService.selectList(bankWrapper);
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        for(Bank bank : banks){
            bank.setBankLogo(systemImageUrl+bank.getBankLogo());
        }
        return  new Result<List<Bank>>(Code.SUCCESS,"查询成功!",banks,Code.IS_ALERT_NO);
    }

}
