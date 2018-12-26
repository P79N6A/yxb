package com.yxbkj.yxb.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.common.entity.YxbConstants;
import com.yxbkj.yxb.common.redis.RedisTemplateUtils;
import com.yxbkj.yxb.common.utils.*;
import com.yxbkj.yxb.domain.mapper.MemberInfoMapper;
import com.yxbkj.yxb.domain.model.MemberInfo;
import com.yxbkj.yxb.service.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员信息表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
@Service
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements MemberInfoService {
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Override
    public MemberInfo findMemberByPhone(String phone) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setPhone(phone);
        return memberInfoMapper.selectOne(memberInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> registMember(String phone, String code, HttpServletRequest request) {
        Result<Map<String, Object>> result = null;
        if(phone==null || !ValidateUtils.isPhone(phone)){
            result = new Result<>(Code.FAIL, "注册失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        String redis_code = redisTemplateUtils.getStringValue(phone);
        if(redis_code!=null){
            if(redis_code.equals(code)){
                //查询出当前手机号的用户信息
                MemberInfo memberInfo = findMemberByPhone(phone);
                if(memberInfo==null){
                    //组装保存数据信息 并保存
                    memberInfo = new MemberInfo();
                    memberInfo.setPhone(phone);
                    memberInfo.setId(StringUtil.getUuid());
                    memberInfo.setCreatorIp(IpUtil.getAddrIP(request));
                    memberInfo.setCreatorTime(YxbConstants.sysDate());
                    memberInfo.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
                    insert(memberInfo);
                    // 构建相应信息
                    String token = StringUtil.getTokenById(memberInfo.getId());
                    redisTemplateUtils.stringAdd(token,memberInfo.getId(),Constants.TOKEN_MAX_TIME);
                    Map<String, Object> map = new HashMap<>();
                    map.put("user",memberInfo);
                    map.put("token",token);
                    result = new Result<>(Code.SUCCESS, "注册成功!", map, Code.IS_ALERT_NO);
                    //移除之前的验证码
                    redisTemplateUtils.detele(phone);
                }else{
                    result = new Result<>(Code.FAIL, "注册失败,已经存在当前用户!", null, Code.IS_ALERT_YES);
                }
            }else{
                result = new Result<>(Code.FAIL, "注册失败!验证码不存在或已过期!", null, Code.IS_ALERT_YES);
            }
        }else{
            result = new Result<>(Code.FAIL, "注册失败!验证码不存在或已过期!", null, Code.IS_ALERT_YES);
        }
        return result;
    }
}
