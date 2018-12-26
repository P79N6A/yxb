package com.yxbkj.yxb.member.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.member.MemberProperty;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.member.service.MemberInfoService;
import com.yxbkj.yxb.member.service.MemberPropertyHisService;
import com.yxbkj.yxb.member.service.MemberPropertyService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.ValidateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户资产信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@RestController
@RequestMapping("/memberProperty")
public class MemberPropertyController {
    @Autowired
    private MemberPropertyService memberPropertyService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberPropertyHisService memberPropertyHisService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 作者: 李明
     * 描述: 获取会员资产信息
     * 备注:
     * @param token
     * @return
     */
    @ApiOperation(value = "获取会员资产信息",notes = "获取会员资产信息")
    @GetMapping("/getMemberProperty")
    public Result<MemberProperty> getMemberProperty(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
      ){
        return memberPropertyService.getMemberProperty(token);
    }

    /**
     * 作者: 李明
     * 描述: 获取资产历史记录信息
     * 备注:
     * @param token
     * @param type
     * @param inOrOut
     * @param offset
     * @param limit
     * @return
     */
    @ApiOperation(value = "获取资产历史记录信息",notes = "获取资产历史记录信息")
    @GetMapping("/getMemberPropertyHis")
    public Result<Page<MemberPropertyHis>> getMemberPropertyHis(
             @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token
            ,@ApiParam(value = "类型(0是资产 1是易豆 不传为所有)",required = false)@RequestParam(value="type")String type
            ,@ApiParam(value = "收支(0是收入 1是支出 不传为所有)",required = false)@RequestParam(value="inOrOut")String inOrOut
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
    ){

        return memberPropertyHisService.getMemberPropertyHis(token,type,inOrOut,offset,limit);

    }

    /**
     * 作者: 李明
     * 描述: 初始化老用户资产信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "初始化老用户资产信息",notes = "初始化老用户资产信息")
    @GetMapping("/initMemberProperty")
    public Result<Boolean> initMemberProperty(){
        Result<Boolean> res = null;
        List<MemberInfo> memberInfos = memberInfoService.selectList(new EntityWrapper<>());

        for(MemberInfo member : memberInfos){
            String memberId = member.getMemberId();
            EntityWrapper<MemberProperty> wrapper = new EntityWrapper<>();
            wrapper.eq("member_id",memberId);

           try{
               MemberProperty memberProperty = memberPropertyService.selectOne(wrapper);
               if(memberProperty==null){
                   // 初始化资产信息
                   MemberProperty property = new MemberProperty();
                   property.setId(StringUtil.getUuid());
                   property.setMemberId(memberId);
                   property.setPropertyAmount(BigDecimal.ZERO);
                   property.setFrozenAmount(BigDecimal.ZERO);
                   property.setAvailableAmount(BigDecimal.ZERO);
                   property.setSubmittedAmount(BigDecimal.ZERO);
                   property.setEbean(0);
                   memberPropertyService.insert(property);
               }
           }catch (Exception e){
               e.printStackTrace();
               logger.info("初始化"+memberId+"用户时出现异常"+e.getMessage());
           }


        }

        res = new Result<>(Code.SUCCESS,"初始化老用户资产信息成功",true,Code.IS_ALERT_NO);
        return res;
    }



}
