package com.yxbkj.yxb.controller;
import com.yxbkj.yxb.common.entity.BaseController;
import com.yxbkj.yxb.common.entity.YxbConstants;
import com.yxbkj.yxb.common.redis.RedisTemplateUtils;
import com.yxbkj.yxb.common.utils.*;
import com.yxbkj.yxb.domain.model.MemberInfo;
import com.yxbkj.yxb.service.MemberInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 会员信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
@RestController
@RequestMapping("/memberInfo")
public class MemberInfoController{
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    /**
     * 作者: 李明
     * 描述: 注册用户
     * 备注:
     * @param phone
     * @param code
     * @return
     */
    @ApiOperation(value = "注册用户",notes = "注册用户")
    @GetMapping("/registMember")
    public Result<Map<String, Object>> registMember(@ApiParam(value = "手机号码",required = true)@RequestParam(value="phone")String phone,
                                                    @ApiParam(value = "验证码",required = true)@RequestParam(value="code")String code,
                                                    HttpServletRequest request
                                                    ) {
        return memberInfoService.registMember(phone, code, request);
    }
    /**
     * 作者: 李明
     * 描述: 发送验证码
     * 备注:
     * @param phone
     * @return
     */
    @ApiOperation(value = "发送验证码",notes = "发送验证码")
    @GetMapping("/sendSmsCode")
    public Result<String> sendSmsCode(@ApiParam(value = "手机号码",required = true)@RequestParam(value="phone")String phone) {
        String verifyCode = String .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        redisTemplateUtils.stringAdd(phone,verifyCode,Constants.CODE_MAX_TIME);
        Result<String> result = new Result<>(Code.SUCCESS, "短信发送成功!", verifyCode, Code.IS_ALERT_NO);
        return result;
    }
}
