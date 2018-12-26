package com.yxbkj.yxb.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.CodeInfo;
import com.yxbkj.yxb.entity.vo.ZrpxParam;
import com.yxbkj.yxb.member.mapper.MemberInfoMapper;
import com.yxbkj.yxb.member.service.CodeInfoService;
import com.yxbkj.yxb.member.service.MemberInfoService;
import com.yxbkj.yxb.member.service.ZhongruiService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.zrbx.ZRBXUtils;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 车辆违章记录表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-17
 */
@Service
public class ZhongruiServiceImpl   implements ZhongruiService {

    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private CodeInfoService codeInfoService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Result<Map<String, Object>> baseAuth(ZrpxParam param) {
        String token = param.getToken();
        if(token==null){
            return new Result<Map<String, Object>>(Code.FAIL,"token不能为空!",null,Code.IS_ALERT_YES);
        }
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<Map<String, Object>>(Code.FAIL,"会员ID不存在!",null,Code.IS_ALERT_YES);
        }
        //会员信息
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id",memberId);
        MemberInfo memberInfo = memberInfoService.selectOne(wrapper);
        if(memberInfo==null){
            return new Result<>(Code.FAIL,"会员信息不存在!",null,Code.IS_ALERT_YES);
        }
        if(YxbConstants.IEC_STATUS_TYPE_YES.equals(memberInfo.getInternetAuthStatus())){
            return new Result<>(Code.FAIL,"该会员已经认证!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getUserSex())){
            return new Result<>(Code.FAIL,"性别不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getUserName())){
            return new Result<>(Code.FAIL,"姓名不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getUserNation())){
            return new Result<>(Code.FAIL,"民族不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getUserIdType())){
            return new Result<>(Code.FAIL,"证件类型不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getUserIdCode())){
            return new Result<>(Code.FAIL,"证件号码不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getEducationalLeveL())){
            return new Result<>(Code.FAIL,"文化程度不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getPolitical())){
            return new Result<>(Code.FAIL,"政治面貌不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getUserBirthday())){
            return new Result<>(Code.FAIL,"出生年月不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getUserIdCode())){
            return new Result<>(Code.FAIL,"文化程度不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getUserPhone())){
            return new Result<>(Code.FAIL,"移动电话不能为空!",null,Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(param.getAddress())){
            return new Result<>(Code.FAIL,"现住址不能为空!",null,Code.IS_ALERT_YES);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userName",param.getUserName());
        if("10000022".equals(param.getUserSex())){
            // 2代表本系统中的 女(中瑞为0)  其他之为男
            map.put("userSex","0");
        }else{
            map.put("userSex","1");
        }
        //名族
        map.put("userNation",getCodeNameByValue(param.getUserNation()));
        //证件类型
        map.put("userIdType",getCodeNameByValue(param.getUserIdType()));
        //文化程度
        map.put("educationalLeveL",getCodeNameByValue(param.getEducationalLeveL()));
        //政治面貌
        map.put("political",getCodeNameByValue(param.getPolitical()));
        map.put("userIdCode",param.getUserIdCode());
        map.put("userBirthday",param.getUserBirthday());
        map.put("userPhone",param.getUserPhone());
        map.put("address",param.getAddress());
        map.put("timestamp",System.currentTimeMillis()+"");
        if(param.getUniversity()!=null)map.put("university",param.getUniversity());
        if(param.getPostcode()!=null)map.put("postcode",param.getPostcode());
        Map<String, Object> res = new HashMap<>();
        String res_str = null;
        try{
            logger.info("请求参数"+map+"参数串"+map.toString());
            res_str = ZRBXUtils.zrbxAuthentication(map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(Code.FAIL,"认证失败!第三方服务异常"+e.getMessage(),null,Code.IS_ALERT_YES);
        }
        logger.info("认证结果{}",res_str);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(res_str);
        int ret = jsonObject.getIntValue("ret");
        //if(ret!=0){//临时注释 不判断中瑞的结果
        if(ret!=0){
            return new Result<>(Code.FAIL,"认证失败!",null,Code.IS_ALERT_YES);
        }else{
            //修改会员信息
            memberInfo.setInternetAuthStatus(YxbConstants.IEC_STATUS_TYPE_YES);
            memberInfo.setMemberType(YxbConstants.MEMBER_TYPE_YES);
            memberInfo.setMemberName(param.getUserName());
            memberInfo.setSex(param.getUserSex()+"");
            memberInfo.setMemberEthnic(param.getUserNation());
            memberInfo.setIdcard(param.getUserIdCode());
            memberInfo.setMemberEdu(param.getEducationalLeveL());
            memberInfo.setPolicitalStatus(param.getPolitical());
            memberInfo.setMemberBirth(param.getUserBirthday());
            memberInfo.setMemberAddress(param.getAddress());
            memberInfo.setUniversity(param.getUniversity());
            memberInfo.setPostCode(param.getPostcode());
            memberInfo.setModifierTime(YxbConstants.sysDate());
            memberInfoService.updateById(memberInfo);
        }
        return new Result<>(Code.SUCCESS,"操作成功!",map,Code.IS_ALERT_NO);
    }

    private String getCodeNameByValue(String codeName){
        if(StringUtil.isEmpty(codeName))  return "";
        EntityWrapper<CodeInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("code_value", codeName);
        CodeInfo codeInfo = codeInfoService.selectOne(wrapper);
        if(codeInfo!=null){
            return codeInfo.getCodeName();
        }else{
            return "";
        }
    }

}
