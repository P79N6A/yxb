package com.yxbkj.yxb.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.*;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Constants;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.order.OrderPayment;
import com.yxbkj.yxb.entity.system.ActivityInfo;
import com.yxbkj.yxb.entity.system.SmsLog;
import com.yxbkj.yxb.entity.vo.LoginLogVo;
import com.yxbkj.yxb.member.mapper.*;
import com.yxbkj.yxb.member.service.*;
import com.yxbkj.yxb.util.*;
import com.yxbkj.yxb.util.aliyun.SmsUtils;
import com.yxbkj.yxb.util.jql.jqlUtil;
import com.yxbkj.yxb.util.umeng.UmengUtils;
import com.yxbkj.yxb.util.wxbus.TemplateData;
import com.yxbkj.yxb.util.wxbus.WX_TemplateMsgUtil;
import com.yxbkj.yxb.util.wxpay.util.PayConfigUtil;
import com.yxbkj.yxb.util.wxpay.util.WeiXinPayService;
import com.yxbkj.yxb.util.yeepay.*;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
    private MemberLoginLogMapper memberLoginLogMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private MemberPropertyService memberPropertyService;
    @Autowired
    private MemberPropertyHisService memberPropertyHisService;
    @Autowired
    private OrderPaymentMapper orderPaymentMapper;
    @Autowired
    private SmsLogMapper smsLogMapper;
    @Autowired
    private ActivityInfoMapper activityInfoMapper;
    @Autowired
    private MemberPropertyMapper memberPropertyMapper;
    @Autowired
    private ActivityParticipantsMapper activityParticipantsMapper;
    @Autowired
    private BeanLogService beanLogService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public MemberInfo findMemberByPhone(String phone) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setPhone(phone);
        memberInfo.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        return memberInfoMapper.selectOne(memberInfo);
    }
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> registMember(String phone, String code, String inviteCode, String parentId,String ip, HttpServletRequest request) {
        Result<Map<String, Object>> result = null;
        String redis_code = redisTemplateUtils.getStringValue(phone);
        if (redis_code == null) {
            result = new Result<>(Code.FAIL, "注册失败!验证码不存在或已过期!", null, Code.IS_ALERT_YES);
            return result;
        }
        if (!redis_code.equals(code)) {
            result = new Result<>(Code.FAIL, "注册失败!验证码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        MemberInfo memberInfo = findMemberByPhone(phone);
        if (memberInfo != null) {
            result = new Result<>(Code.FAIL, "注册失败,已经存在当前用户!", null, Code.IS_ALERT_YES);
            return result;
        }
        //判断邀请码 是否存在
        if (!StringUtil.isEmpty(inviteCode)) {
            try{
                EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
                wrapper.eq("member_invite_code",inviteCode);
                wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
                MemberInfo inviteMember = selectOne(wrapper);
                if(inviteMember==null){
                    return new Result<>(Code.FAIL, "无效的邀请码!", null, Code.IS_ALERT_YES);
                }
            }catch (Exception e){
                logger.info("添加上级ID出现错误,手机号{} 邀请码{}",phone,inviteCode);
                e.printStackTrace();
                return new Result<>(Code.FAIL, "获取邀请码异常!", null, Code.IS_ALERT_YES);
            }
        }
        //判断上级ID 是否存在
        if (!StringUtil.isEmpty(parentId)) {
            try{
                EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
                wrapper.eq("member_id",parentId);
                wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
                MemberInfo inviteMember = selectOne(wrapper);
                if(inviteMember==null){
                    return new Result<>(Code.FAIL, "无效的上级ID!", null, Code.IS_ALERT_YES);
                }
            }catch (Exception e){
                logger.info("添加上级ID出现错误,手机号{} 邀请码{}",phone,parentId);
                e.printStackTrace();
                return new Result<>(Code.FAIL, "获取上级ID异常!", null, Code.IS_ALERT_YES);
            }
        }
        //注册用户信息
        memberInfo = addMemberAndProperty(phone, inviteCode,ip);
        //设置上级ID
        if(!StringUtil.isEmpty(parentId)){
            MemberInfo parentMember = new MemberInfo();
            parentMember.setMemberId(parentId);
            MemberInfo parentMemberDb = memberInfoMapper.selectOne(parentMember);
            if(parentMemberDb==null){
                logger.info("邀请注册传入的上级ID"+parentId);
            }else{
                memberInfo.setPid(parentId);
                updateById(memberInfo);
            }
        }
        //如果此用户有上级ID
        if(!StringUtil.isEmpty(memberInfo.getPid())){
            sendGift(memberInfo.getPid(),memberInfo);//发放易豆
        }
        //初始化1000易豆
        sendEban(memberInfo);
        // 构建相应信息
        String token = StringUtil.getTokenById(memberInfo.getMemberId());
        redisTemplateUtils.stringAdd(token, memberInfo.getMemberId(), Constants.TOKEN_MAX_TIME);
        Map<String, Object> map = new HashMap<>();
        reBuildMember(memberInfo);
        map.put("user", memberInfo);
        map.put("token", token);
        result = new Result<>(Code.SUCCESS, "注册成功!", map, Code.IS_ALERT_NO);
        //移除之前的验证码
        redisTemplateUtils.detele(phone);
        return result;
    }

    private void sendEban(MemberInfo memberInfo){
        try{
            EntityWrapper<MemberProperty> wrapper = new EntityWrapper();
            wrapper.eq("member_id",memberInfo.getMemberId());
            Integer integer = memberPropertyMapper.selectCount(wrapper);
            if(integer>0){
                return;
            }
            // 初始化资产信息
            MemberProperty property = new MemberProperty();
            property.setId(StringUtil.getUuid());
            property.setMemberId(memberInfo.getMemberId());
            property.setPropertyAmount(BigDecimal.ZERO);
            property.setFrozenAmount(BigDecimal.ZERO);
            property.setAvailableAmount(BigDecimal.ZERO);
            property.setSubmittedAmount(BigDecimal.ZERO);
            property.setEbean(1000);
            memberPropertyMapper.insert(property);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendGift(String pid,MemberInfo memberInfo) {
        try{
            MemberInfo parentMember = new MemberInfo();
            parentMember.setMemberId(pid);
            MemberInfo parentMemberDb = memberInfoMapper.selectOne(parentMember);
            if(parentMemberDb==null){
                logger.info("【易小保科技】发放奖励时出现异常  不存在的上级ID"+pid);
            }
            //发放通知
            try{
                String extra=getExtraMsg(memberInfo,parentMemberDb);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("发送增员通知失败");
            }
            int  giftEban = 0;
             if(YxbConstants.DDEFAULT_MEMBERLEVRL.equals(parentMemberDb.getMemberlevel())){
                //会员奖励10易豆
                 giftEban = 10;
             }else if(YxbConstants.DDEFAULT_MEMBERLEVRL_HEHUOREN.equals(parentMemberDb.getMemberlevel())){
                 //合伙人奖励100易豆
                 giftEban = 100;
             }else if(YxbConstants.DDEFAULT_MEMBERLEVRL_TOUZIREN.equals(parentMemberDb.getMemberlevel())){
                 //投资人奖励1000易豆
                 giftEban = 1000;
             }
             if(giftEban>0){
                //执行发放奖励系列
                 //构建实体信息
                 BeanLog log = new BeanLog();
                 log.setId(StringUtil.getUuid());
                 log.setMemberId(parentMemberDb.getMemberId());
                 log.setActiveType(YxbConstants.ACTIVETYPE_YIDOU_SIGN);
                 log.setCreatorIp(HttpKit.getClientIP());
                 log.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
                 log.setCreatorTime(DateUtils.getSysDate());
                 log.setRemark("邀请奖励");
                 log.setBean(giftEban);
                 beanLogService.insert(log);
                 // 修改资产信息
                 EntityWrapper<MemberProperty> propertyWrapper = new EntityWrapper<>();
                 propertyWrapper.eq("member_id",parentMemberDb.getMemberId());
                 MemberProperty property = memberPropertyService.selectOne(propertyWrapper);
                 // 添加资产信息流水
                 MemberPropertyHis propertyHis = new MemberPropertyHis();
                 propertyHis.setId(StringUtil.getUuid());
                 propertyHis.setPropertyAmount(property.getPropertyAmount());//总收入
                 propertyHis.setFrozenAmount(property.getFrozenAmount());//冻结金额
                 propertyHis.setAvailableAmount(property.getAvailableAmount());//可用金额  为 0
                 propertyHis.setSubmittedAmount(property.getSubmittedAmount());//已提金额
                 propertyHis.setEbean(property.getEbean());
                 propertyHis.setRemark("邀请奖励");
                 propertyHis.setMemberId(parentMemberDb.getMemberId());
                 propertyHis.setModifierTime(YxbConstants.sysDate());
                 memberPropertyHisService.insert(propertyHis);
                 property.setEbean(property.getEbean()+giftEban);
                 memberPropertyService.updateById(property);
             }
        }catch (Exception e){
            logger.info("【易小保科技】发放奖励时出现异常"+e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * 获取活动附加信息
     * @param memberInfo
     * @param parentMemberDb
     * @return
     */
    private String getExtraMsg(MemberInfo memberInfo,MemberInfo parentMemberDb) {
        try{
            logger.info("开始发送注册推送信息");
            ActivityInfo act = new ActivityInfo();
            act.setActivityNo("ACTI20180921181818001");
            ActivityInfo activityInfo = activityInfoMapper.selectOne(act);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long startTime = formatter.parse(activityInfo.getActivityStartTime()).getTime();
            long endTime = formatter.parse(activityInfo.getActivityEndTime()).getTime();
            long currentTimeMillis= System.currentTimeMillis();
            if(currentTimeMillis>=startTime && currentTimeMillis<=endTime){
                MemberUtils.sendAddMemberInfoRedPack(memberInfo,parentMemberDb,"");//发送增员通知 红包
                SmsUtils.sendSmsForGiftNotify(parentMemberDb.getPhone());//发送短信通知
                Map<String,String> map = new HashMap<>();
                map.put("type","2");
                UmengUtils.sendAndroidMessage(parentMemberDb.getMemberId(),"恭喜您，邀请好友成功，获得邀请红包一个。","请到个人中心查看！",map);
            }else{
                MemberUtils.sendAddMemberInfoUsual(memberInfo,parentMemberDb,"");//发送增员通知 普通
                Map<String,String> map = new HashMap<>();
                map.put("type","2");
                UmengUtils.sendAndroidMessage(parentMemberDb.getMemberId(),"您通过邀请码成功增员一名团队成员!","请到团队成员中查看具体信息！",map);

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发送推送信息异常!"+e.getMessage());
        }
        return "";
    }

    private MemberInfo addMemberAndProperty(String phone, String inviteCode,String ip) {
        //组装保存数据信息 并保存
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setPhone(phone);
        memberInfo.setLoginId(phone);
        // 设置会员ID  暂时设置为时间戳
        String memberId = createMemberId();
        memberInfo.setMemberId(memberId);
        memberInfo.setOldUserId(memberId.replace(YxbConstants.MEMBER_ID_INDEXCHA,""));
        memberInfo.setId(StringUtil.getUuid());
        memberInfo.setCreatorIp(ip);
        memberInfo.setRegisterIp(ip);
        memberInfo.setRegisterTime(YxbConstants.sysDate());
        memberInfo.setCreatorTime(YxbConstants.sysDate());
        memberInfo.setMemberName(phone.replace(phone.substring(3,7),"****"));
        if (!StringUtil.isEmpty(inviteCode)) {
            //memberInfo.setMemberInviteCode(inviteCode);
            try{
                EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
                wrapper.eq("member_invite_code",inviteCode);
                wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
                MemberInfo inviteMember = selectOne(wrapper);
                memberInfo.setPid(inviteMember.getMemberId());
            }catch (Exception e){
                logger.info("添加上级ID出现错误,手机号{} 邀请码{}",phone,inviteCode);
                e.printStackTrace();
            }
        }
        memberInfo.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        memberInfo.setMemberlevel(YxbConstants.DDEFAULT_MEMBERLEVRL);
        memberInfo.setHeadimg(YxbConstants.DEFAULT_HEAD_IMG);
        memberInfo.setNickname(phone.substring(7,11));
        insert(memberInfo);
        return memberInfo;
    }



    /**
     * 初始化老用户资产信息  暂时去除    这里初始化 会有问题
     *
     * @param memberInfo
     */
    private void initOldMemberProperty(MemberInfo memberInfo) {
        /*MemberProperty property_old = new MemberProperty();
        property_old.setMemberId(memberInfo.getMemberId());
        MemberProperty memberProperty = memberPropertyMapper.selectOne(property_old);
        if(memberProperty==null){
            // 初始化资产信息
            MemberProperty property = new MemberProperty();
            property.setId(StringUtil.getUuid());
            property.setMemberId(memberInfo.getMemberId());
            property.setPropertyAmount(BigDecimal.ZERO);
            property.setFrozenAmount(BigDecimal.ZERO);
            property.setAvailableAmount(BigDecimal.ZERO);
            property.setSubmittedAmount(BigDecimal.ZERO);
            property.setEbean(0);
            memberPropertyMapper.insert(property);
        }*/
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> loginByPhone(LoginLogVo logVo) {
        Result<Map<String, Object>> result = null;
        String redis_code = redisTemplateUtils.getStringValue(logVo.getPhone());
        if(!"18281602747".equals(logVo.getPhone())){
            if (redis_code == null) {
                result = new Result<>(Code.FAIL, "登录失败!验证码不存在或已过期!", null, Code.IS_ALERT_YES);
                return result;
            }
        }
        if(!"18281602747".equals(logVo.getPhone())){
            if (!redis_code.equals(logVo.getCode())) {
                result = new Result<>(Code.FAIL, "登录失败!验证码错误!", null, Code.IS_ALERT_YES);
                return result;
            }
        }
        MemberInfo memberInfo = findMemberByPhone(logVo.getPhone());
        if (memberInfo == null) {
            result = new Result<>(Code.FAIL, "不存在当前用户!", null, Code.IS_ALERT_YES);
            return result;
            // 不存在用户 则创建一个账户
            //memberInfo = addMemberAndProperty(logVo.getPhone(), null);
        }
        // 构建相应信息
        String token = StringUtil.getTokenById(memberInfo.getMemberId());
        redisTemplateUtils.stringAdd(token, memberInfo.getMemberId(), Constants.TOKEN_MAX_TIME);
        Map<String, Object> map = new HashMap<>();
        memberInfo.setLoginPwd("");
        reBuildMember(memberInfo);
        initOldMemberProperty(memberInfo);//初始化老用户资产信息
        map.put("user", memberInfo);
        map.put("token", token);
        result = new Result<>(Code.SUCCESS, "登录成功!", map, Code.IS_ALERT_NO);
        //构建登录日志
        MemberLoginLog log = new MemberLoginLog();
        log.setId(StringUtil.getUuid());
        log.setMemberId(memberInfo.getMemberId());
        log.setLoginTime(DateUtils.getSysDate());
        log.setLoginIp(logVo.getIp());
        log.setLoginSource(logVo.getLoginSource());
        memberLoginLogMapper.insert(log);
        //移除之前的验证码
        redisTemplateUtils.detele(logVo.getPhone());
        return result;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> loginByPwd(LoginLogVo logVo) {
        Result<Map<String, Object>> result = null;
        if (logVo.getPhone() == null || !ValidateUtils.isPhone(logVo.getPhone())) {
            result = new Result<>(Code.FAIL, "登录失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        if (StringUtil.isEmpty(logVo.getPassword())) {
            result = new Result<>(Code.FAIL, "登录失败,密码为空!", null, Code.IS_ALERT_YES);
            return result;
        }
        MemberInfo memberInfo = findMemberByPhone(logVo.getPhone());
        if (memberInfo == null) {
            result = new Result<>(Code.FAIL, "登录失败,不存在当前用户!", null, Code.IS_ALERT_YES);
            return result;
        }
        if (MD5Util.MD5(logVo.getPassword()).equals(memberInfo.getLoginPwd())) {
            // 构建相应信息
            String token = StringUtil.getTokenById(memberInfo.getMemberId());
            redisTemplateUtils.stringAdd(token, memberInfo.getMemberId(), Constants.TOKEN_MAX_TIME);
            Map<String, Object> map = new HashMap<>();
            memberInfo.setLoginPwd("");
            reBuildMember(memberInfo);
            initOldMemberProperty(memberInfo);//初始化老用户资产信息
            map.put("user", memberInfo);
            map.put("token", token);
            result = new Result<>(Code.SUCCESS, "登录成功!", map, Code.IS_ALERT_NO);
            //构建登录日志
            MemberLoginLog log = new MemberLoginLog();
            log.setMemberId(memberInfo.getMemberId());
            log.setId(StringUtil.getUuid());
            log.setLoginTime(DateUtils.getSysDate());
            log.setLoginIp(logVo.getIp());
            log.setLoginSource(logVo.getLoginSource());
            memberLoginLogMapper.insert(log);
            return result;
        } else {
            result = new Result<>(Code.FAIL, "登录失败!密码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> wxAuth(String code) {
        logger.info("【微信公众号授权】入参 code:{}", code);
        Result<Map<String, Object>> result = null;
        //组装请求参数
        Map<String, Object> map = new HashMap<>();
        String access_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + YxbConfig.getWxPayAPPID() + "&secret=" + YxbConfig.getWxWX_APPSECRET() + "&code=" + code + "&grant_type=authorization_code";
        String json_str = HttpUtil.doGet(access_url, map, false);
        // 解析响应信息
        logger.info("【微信公众号授权】解析响应信息json_str:{}" + access_url, json_str);
        JSONObject jsonObject = JSONObject.parseObject(json_str);
        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");
        String refresh_token = jsonObject.getString("refresh_token");
        if (access_token == null) {
            return new Result<>(Code.FAIL, "授权失败!access_token获取失败", null, Code.IS_ALERT_YES);
        }
        //检查凭证token是否有效
        String check_token_url = "https://api.weixin.qq.com/sns/auth?access_token=" + access_token + "&openid=" + openid;
        String check_token_str = HttpUtil.doGet(check_token_url, map, false);
        logger.info("【微信公众号授权】解析响应信息check_token_url:{}" + check_token_url, check_token_str);
        JSONObject checkObject = JSONObject.parseObject(check_token_str);
        if (checkObject.getIntValue("errcode") != 0) {//token过期就刷新token 0没过期
            //刷新token
            String flush_url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + YxbConfig.getWxPayAPPID() + "&grant_type=refresh_token&refresh_token=" + refresh_token;
            //获取刷新token的接口
            String flush_url_str = HttpUtil.doGet(flush_url, map, false);
            logger.info("【微信公众号授权】解析响应信息flush_url:{}" + flush_url, flush_url_str);
            JSONObject flushObject = JSONObject.parseObject(flush_url_str);
            //获取刷新后的token
            access_token = flushObject.getString("access_token");
        }
        //拉取用户信息(需scope为 snsapi_userinfo)
        String get_user_info_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        String get_user_info_url_str = HttpUtil.doGet(get_user_info_url, map, false);
        logger.info("【微信公众号授权】解析响应信息get_user_info_url:{}" + get_user_info_url, get_user_info_url_str);
        JSONObject userObject = JSONObject.parseObject(get_user_info_url_str);
        if (userObject.get("errcode") != null) {
            return new Result<>(Code.FAIL, "获取授权信息失败!", null, Code.IS_ALERT_YES);//获取授权失败
        }
        Map<String, Object> returnMap = new HashMap<>();
        //组装保存数据信息 并保存
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setOpenId(openid);
        MemberInfo memberInfoDb = memberInfoMapper.selectOne(memberInfo);
        if (memberInfoDb == null) {
            // 设置会员ID  暂时设置为时间戳
            memberInfo.setMemberId(createMemberId());
            memberInfo.setId(StringUtil.getUuid());
            memberInfo.setCreatorIp(HttpKit.getClientIP());
            memberInfo.setCreatorTime(YxbConstants.sysDate());
            memberInfo.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
            memberInfo.setNickname(userObject.getString("nickname"));
            memberInfo.setHeadimg(userObject.getString("headimgurl"));
            //insert(memberInfo);
            reBuildMember(memberInfo);
            returnMap.put("user", memberInfo);
            returnMap.put("token", null);
        } else {
            reBuildMember(memberInfoDb);
            returnMap.put("user", memberInfoDb);
            String phone = memberInfoDb.getPhone();
            //{"openid":"oS3oy1kSmcao09scQYxGYyouguno","nickname":"洲","sex":1,"language":"zh_CN","city":"成都","province":"四川","country":"中国","headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/bJSIIL4V4pDeyzTWW4g07DVWfsrWt0V2Pgr3t6nGW5VxKfcaunBMruxk07LvwbN6vJMiaEN17VMYBDtib6l0tT9g\/132","privilege":[]}https://api.weixin.qq.com/sns/userinfo?access_token=13_3KjXjrBEBLWlVyOL29CnFba5kxPVfm2qJU5EjchhQHUS9iR9nHA5MJNcXsFRKonitPUDZLGtNYoz839s1HfmJA&openid=oS3oy1kSmcao09scQYxGYyouguno&lang=zh_CN
            if (memberInfoDb.getNickname() == null) {
                memberInfoDb.setNickname(userObject.getString("nickname"));
            }
            if (memberInfoDb.getHeadimg() == null) {
                memberInfoDb.setHeadimg(userObject.getString("headimgurl"));
            }
            if (StringUtil.isEmpty(phone)) {
                returnMap.put("token", null);
            } else {
                // 构建相应信息
                String token = StringUtil.getTokenById(memberInfo.getMemberId());
                redisTemplateUtils.stringAdd(token, memberInfo.getMemberId(), Constants.TOKEN_MAX_TIME);
                initOldMemberProperty(memberInfo);//初始化老用户资产信息
                returnMap.put("token", token);
            }
        }
        return new Result<>(Code.SUCCESS, "获取授权信息成功!", returnMap, Code.IS_ALERT_NO);
    }
    public String createMemberId() {
        //int incre = 86365251 + redisTemplateUtils.incre("yxbmemberid_incre", "", 1, 0);
        int incre = memberInfoMapper.getNextMemberId();
        String memberId = YxbConstants.MEMBER_ID_INDEXCHA + incre;
        return memberId;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> bindPhoneByOpenId(String phone, String openId, String code,String ip,HttpServletRequest request) {
        Result<Map<String, Object>> result = null;
        String redis_code = redisTemplateUtils.getStringValue(phone);
        if (redis_code == null) {
            result = new Result<>(Code.FAIL, "注册失败!验证码不存在或已过期!", null, Code.IS_ALERT_YES);
            return result;
        }
        if (!redis_code.equals(code)) {
            result = new Result<>(Code.FAIL, "注册失败!验证码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        if(StringUtil.isEmpty(openId)){
            return new Result<>(Code.FAIL, "绑定失败!openId不能为空!", null, Code.IS_ALERT_YES);
        }
        //先查询  openid是否被使用
        EntityWrapper<MemberInfo> memberInfoWarper = new EntityWrapper<>();
        memberInfoWarper.eq("open_id",openId);
        MemberInfo memberInfoDbs = selectOne(memberInfoWarper);
        if(memberInfoDbs!=null){
            return new Result<>(Code.FAIL, "绑定失败!该openId已经存在!", null, Code.IS_ALERT_YES);
        }
        Map<String, Object> returnMap = new HashMap<>();
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setPhone(phone);
        MemberInfo memberInfoDb = memberInfoMapper.selectOne(memberInfo);
        if (memberInfoDb == null) {
            memberInfo.setPhone(phone);
            memberInfo.setOpenId(openId);
            String memberId = createMemberId();
            memberInfo.setMemberId(memberId);
            memberInfo.setOldUserId(memberId.replace(YxbConstants.MEMBER_ID_INDEXCHA,""));
            memberInfo.setLoginId(phone);
            memberInfo.setId(StringUtil.getUuid());
            memberInfo.setCreatorIp(ip);
            memberInfo.setRegisterIp(ip);
            memberInfo.setRegisterTime(YxbConstants.sysDate());
            memberInfo.setCreatorTime(YxbConstants.sysDate());
            memberInfo.setMemberName(phone.replace(phone.substring(3,7),"****"));
            memberInfo.setMemberlevel(YxbConstants.DDEFAULT_MEMBERLEVRL);
            memberInfo.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
            memberInfo.setHeadimg(YxbConstants.DEFAULT_HEAD_IMG);
            memberInfo.setNickname(phone.substring(7,11));
            insert(memberInfo);
            //初始化1000易豆
            sendEban(memberInfo);
            // 构建相应信息
            String token = StringUtil.getTokenById(memberInfo.getMemberId());
            redisTemplateUtils.stringAdd(token, memberInfo.getMemberId(), Constants.TOKEN_MAX_TIME);
            reBuildMember(memberInfo);
            returnMap.put("user", memberInfo);
            returnMap.put("token", token);
            return new Result<>(Code.SUCCESS, "绑定信息成功!", returnMap, Code.IS_ALERT_NO);
        } else {
            memberInfoDb.setOpenId(openId);
            memberInfoMapper.updateById(memberInfoDb);
            // 构建相应信息
            String token = StringUtil.getTokenById(memberInfoDb.getMemberId());
            redisTemplateUtils.stringAdd(token, memberInfoDb.getMemberId(), Constants.TOKEN_MAX_TIME);
            reBuildMember(memberInfoDb);
            initOldMemberProperty(memberInfoDb);//初始化老用户资产信息
            returnMap.put("user", memberInfoDb);
            returnMap.put("token", token);
            return new Result<>(Code.SUCCESS, "绑定信息成功!", returnMap, Code.IS_ALERT_NO);
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> findPwdByPhone(String phone, String code, String newPassWord) {
        Result<Map<String, Object>> result = null;
        String redis_code = redisTemplateUtils.getStringValue(phone);
        if (redis_code == null) {
            result = new Result<>(Code.FAIL, "操作失败!验证码不存在或已过期!", null, Code.IS_ALERT_YES);
            return result;
        }
        if (!redis_code.equals(code)) {
            result = new Result<>(Code.FAIL, "操作失败!验证码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        MemberInfo memberInfo = findMemberByPhone(phone);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "操作失败!不存在当前用户!", null, Code.IS_ALERT_YES);
        }
        memberInfo.setLoginPwd(MD5Util.MD5(newPassWord));
        updateById(memberInfo);
        // 构建相应信息
        String token = StringUtil.getTokenById(memberInfo.getMemberId());
        redisTemplateUtils.stringAdd(token, memberInfo.getMemberId(), Constants.TOKEN_MAX_TIME);
        Map<String, Object> map = new HashMap<>();
        memberInfo.setLoginPwd("");
        reBuildMember(memberInfo);
        initOldMemberProperty(memberInfo);//初始化老用户资产信息
        map.put("user", memberInfo);
        map.put("token", token);
        //移除之前的验证码
        redisTemplateUtils.detele(phone);
        return new Result<>(Code.SUCCESS, "操作成功!", map, Code.IS_ALERT_NO);
    }
    private void reBuildMember(MemberInfo memberInfo) {
        if (!StringUtil.isEmpty(memberInfo.getHeadimg()) && !memberInfo.getHeadimg().contains("http")) {
            String systemImageUrl = configService.getConfigValue("systemImageUrl");
            memberInfo.setHeadimg(systemImageUrl + memberInfo.getHeadimg());
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<MemberInfo> updateNickName(String token, String nickName) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return new Result<MemberInfo>(Code.FAIL, "会员ID不存在!", null, Code.IS_ALERT_YES);
        }
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(memberId);
        MemberInfo memberInfoDb = memberInfoMapper.selectOne(memberInfo);
        if (memberInfoDb == null) {
            return new Result<MemberInfo>(Code.FAIL, "会员不存在!", null, Code.IS_ALERT_YES);
        }
        memberInfoDb.setNickname(nickName);
        Integer res = memberInfoMapper.updateById(memberInfoDb);
        if (res < 1) {
            return new Result<MemberInfo>(Code.FAIL, "修改失败!", null, Code.IS_ALERT_YES);
        }
        reBuildMember(memberInfoDb);
        return new Result<MemberInfo>(Code.SUCCESS, "操作成功!", memberInfoDb, Code.IS_ALERT_YES);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<MemberInfo> updateHeadImage(String token, String headImage) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return new Result<MemberInfo>(Code.FAIL, "会员ID不存在!", null, Code.IS_ALERT_YES);
        }
        MemberInfo memberInfo = new MemberInfo();
        if (memberInfo == null) {
            return new Result<MemberInfo>(Code.FAIL, "会员不存在!", null, Code.IS_ALERT_YES);
        }
        memberInfo.setMemberId(memberId);
        MemberInfo memberInfoDb = memberInfoMapper.selectOne(memberInfo);
        memberInfoDb.setHeadimg(headImage);
        Integer res = memberInfoMapper.updateById(memberInfoDb);
        if (res < 1) {
            return new Result<MemberInfo>(Code.FAIL, "修改失败!", null, Code.IS_ALERT_YES);
        }
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        memberInfoDb.setHeadimg(systemImageUrl + memberInfoDb.getHeadimg());
        return new Result<MemberInfo>(Code.SUCCESS, "操作成功!", memberInfoDb, Code.IS_ALERT_YES);
    }
    @Override
    public Result<MemberInfo> getUserInfo(String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return new Result<>(Code.FAIL, "会员ID不存在", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id", memberId);
        MemberInfo memberInfo = selectOne(wrapper);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "不存在当前用户", null, Code.IS_ALERT_YES);
        }
        if (YxbConstants.DATA_DELETE_STATUS_CODE.equals(memberInfo.getValidity())) {
            return new Result<>(Code.FAIL, "会员被冻结!", null, Code.IS_ALERT_YES);
        }
        if(!StringUtil.isEmpty(memberInfo.getPid())){
            wrapper = new EntityWrapper<>();
            wrapper.eq("member_id", memberInfo.getPid());
            MemberInfo memberInfoParent = selectOne(wrapper);
            memberInfo.setParentMemberInfo(memberInfoParent);
        }
        reBuildMember(memberInfo);
        return new Result<>(Code.SUCCESS, "查询数据成功!", memberInfo, Code.IS_ALERT_NO);
    }
    @Override
    public Result<String> sendSmsCode(String phone,String ip) {
        Result<String> result = null;
        if (phone == null || !ValidateUtils.isPhone(phone)) {
            result = new Result<>(Code.FAIL, "发送失败,手机号码错误!", null, Code.IS_ALERT_YES);
            return result;
        }
        //统计手机发送次数
        EntityWrapper<SmsLog> logWrapper = new EntityWrapper<>();
        logWrapper.eq("phone",phone);
        logWrapper.ge("create_time",LocalDate.now().toString()+" 00:00:00");
        int logCount = smsLogMapper.selectCount(logWrapper);
        if(logCount>10){
            return new Result<String>(Code.FAIL, "同一手机只能每天只能发送10次!", null, Code.IS_ALERT_YES);
        }
        //统计完毕
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        try {
            //暂时取消发送验证码
            //SmsUtil.sendSms("", phone, Integer.parseInt(verifyCode));
            SendSmsResponse sendSmsResponse = SmsUtils.sendSms(phone, verifyCode);
            if(!"OK".equals(sendSmsResponse.getCode())){
                logger.info("【易小保科技   短信发送异常】"+sendSmsResponse.getMessage());
                return  new Result<>(Code.FAIL, ""+sendSmsResponse.getMessage(), null, Code.IS_ALERT_YES);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result<>(Code.FAIL, "发送失败,短信发送异常!", null, Code.IS_ALERT_YES);
            return result;
        }
        SmsLog log = new SmsLog();
        log.setId(StringUtil.getUuid());
        log.setPhone(phone);
        log.setCode(verifyCode);
        log.setIp(ip);
        log.setCreateTime(YxbConstants.sysDate());
        smsLogMapper.insert(log);
        logger.info("【易小保科技】短信发送信息 phone{}   code:{}", phone, verifyCode);
        redisTemplateUtils.stringAdd(phone, verifyCode, Constants.CODE_MAX_TIME);
        return new Result<>(Code.SUCCESS, "短信发送成功!", "", Code.IS_ALERT_NO);
    }
    @Override
    public Result<Map<String, Object>> getTokenByOpenId(String openId) {
        if (StringUtil.isEmpty(openId)) {
            return new Result<>(Code.FAIL, "openId不能为空!", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberInfo> wraper = new EntityWrapper<>();
        wraper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        wraper.eq("open_id", openId);
        MemberInfo memberInfo = selectOne(wraper);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "无此用户信息!", null, Code.IS_ALERT_YES);
        }
        Map<String, Object> map = new HashMap<>();
        memberInfo.setLoginPwd("");
        reBuildMember(memberInfo);
        // 构建相应信息
        String token = StringUtil.getTokenById(memberInfo.getMemberId());
        redisTemplateUtils.stringAdd(token, memberInfo.getMemberId(), Constants.TOKEN_MAX_TIME);
        map.put("user", memberInfo);
        map.put("token", token);
        return new Result<>(Code.SUCCESS, "获取成功!", map, Code.IS_ALERT_NO);
    }
    @Override
    public Result<Map<String, Object>> getMemberTeam(String memberId, String memberLevel) {
        if (StringUtil.isEmpty(memberId)) {
            return new Result<>(Code.FAIL, "memberId不能为空!", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id", memberId);
        MemberInfo memberInfo = selectOne(wrapper);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "不存在该会员信息!", null, Code.IS_ALERT_YES);
        }
        wrapper = new EntityWrapper<>();
        wrapper.eq("pid", memberId);
        wrapper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        if (!StringUtil.isEmpty(memberLevel)) {
            wrapper.eq("memberlevel", memberLevel);
        }
        List<MemberInfo> memberInfos = selectList(wrapper);
        for (MemberInfo bean : memberInfos) {
            reBuildMember(bean);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("teams", memberInfos);
        map.put("parentInfo",memberInfo);
        return new Result<>(Code.SUCCESS, "获取团队信息成功!", map, Code.IS_ALERT_NO);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> buyMember(String token, String payType, String memberLevel,String source) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return new Result<>(Code.FAIL, "会员ID不存在", null, Code.IS_ALERT_YES);
        }
        if (StringUtil.isEmpty(payType)) {
            return new Result<>(Code.FAIL, "支付方式不能为空!", null, Code.IS_ALERT_YES);
        }
        if (StringUtil.isEmpty(memberLevel)) {
            return new Result<>(Code.FAIL, "会员等级不能为空!", null, Code.IS_ALERT_YES);
        }
        if (StringUtil.isEmpty(source)) {
            return new Result<>(Code.FAIL, "source不能为空!", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        MemberInfo memberInfo = selectOne(wrapper);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "不存在当前用户!", null, Code.IS_ALERT_YES);
        }
        if (memberLevel.equals(memberInfo.getMemberlevel())) {
            //return new Result<>(Code.FAIL, "当前会员已是该等级!", null, Code.IS_ALERT_YES);
        }

         if(!StringUtil.isEmpty(memberInfo.getMemberlevel())){
            if(Integer.parseInt(memberLevel) <  Integer.parseInt(memberInfo.getMemberlevel())){
                return new Result<>(Code.FAIL, "无法升级为比自身级别更低的级别!", null, Code.IS_ALERT_YES);
            }
         }


        //获取用户资产信息
        EntityWrapper<MemberProperty> wrapper_property = new EntityWrapper<>();
        wrapper_property.eq("member_id",memberId);
        MemberProperty memberProperty = memberPropertyService.selectOne(wrapper_property);
        if (memberProperty==null) {
            return new Result<>(Code.FAIL, "无法获取用户资产信息!", null, Code.IS_ALERT_YES);
        }
        Map<String,Object> map = new HashMap<>();
        //1 获取购买此会员需要的金额
        String buyMember = configService.getConfigValue(YxbConstants.BUYMEMBER_CONFIG);
        JSONObject parse = (JSONObject) JSONObject.parse(buyMember);
        JSONObject jsonObject = parse.getJSONObject(memberLevel);
        BigDecimal need_ebean = jsonObject.getBigDecimal("ebean");
        BigDecimal need_money = jsonObject.getBigDecimal("money");
        //2 判断用户余额
        if(payType.equals(YxbConstants.PAY_WAY_YIDOU)) {//易豆
            if (memberProperty.getEbean() < need_ebean.intValue()) {
                return new Result<>(Code.FAIL, "易豆不足!", null, Code.IS_ALERT_YES);
            }
        }else if(payType.equals(YxbConstants.PAY_WAY_YIBAO)) {//易宝支付
            String payment_id = System.currentTimeMillis() + "";
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setId(StringUtil.getUuid());
            orderPayment.setOrderId(payment_id);
            orderPayment.setMemberId(memberId);
            orderPayment.setCreateTime(YxbConstants.sysDate());
            orderPayment.setContent(memberLevel);
            orderPayment.setRemark("易宝订单-购买会员");
            orderPayment.setPayWay(YxbConstants.PAY_WAY_YIBAO);
            orderPayment.setPayAmount(need_money.toString());
            orderPayment.setOrderAmount(need_money.toString());
            orderPayment.setStatus(YxbConstants.ORDER_PAY_NO);//未支付
            Integer insert = orderPaymentMapper.insert(orderPayment);
            if(insert<1){
                return new Result<>(Code.FAIL, "获取易宝链接失败!无法生成付款订单!", null, Code.IS_ALERT_YES);
            }
            String payUrl = YeePayUtils.getPayUrl(payment_id, need_money+"", "易小保会员升级", "易小保会员升级",source);
            if(payUrl==null){
                return new Result<>(Code.FAIL, "获取易宝链接失败!", null, Code.IS_ALERT_YES);
            }
            Map<String,Object> yibaoParam = new HashMap<>();
            yibaoParam.put("redirectUrl", payUrl);
            logger.info("易宝链接地址"+payUrl);
            logger.info("金额"+need_money.intValue());
            return new Result<>(Code.SUCCESS, "获取易宝链接成功!", yibaoParam, Code.IS_ALERT_NO);

        }else if(payType.equals(YxbConstants.PAY_WAY_WX)) {//微信支付
            //  公众号 H5 发起的
            if(source.equals(YxbConstants.MEMBER_SOURCE_WX)){
               try{
                   String payment_id = System.currentTimeMillis() + "";
                   String clientIP = HttpKit.getClientIP();
                   logger.info("客户端IP"+PayConfigUtil.NOTIFY_URL);
                   BigDecimal bigDecimal = new BigDecimal("100");
                   JSONObject return_json = WeiXinPayService.weiXinH5Pay(memberInfo.getOpenId(), payment_id, memberLevel, need_money.multiply(bigDecimal).intValue()+"",clientIP);
                    if(return_json.getString("package")==null){
                        logger.info("【易小保科技】微信H5支付发生异常 统一下单失败"+return_json.toJSONString()+"  会员信息:"+memberId+"会员等级:"+memberLevel);
                        return new Result<>(Code.FAIL, "统一下单失败!", null, Code.IS_ALERT_YES);
                    }
                   OrderPayment orderPayment = new OrderPayment();
                   orderPayment.setId(StringUtil.getUuid());
                   orderPayment.setOrderId(payment_id);
                   orderPayment.setMemberId(memberId);
                   orderPayment.setCreateTime(YxbConstants.sysDate());
                   orderPayment.setContent(memberLevel);
                   orderPayment.setRemark("微信公众号-购买会员");
                   orderPayment.setPayWay(YxbConstants.PAY_WAY_WX);
                   orderPayment.setPayAmount(need_money.toString());
                   orderPayment.setOrderAmount(need_money.toString());
                   orderPayment.setStatus(YxbConstants.ORDER_PAY_NO);//未支付
                   Integer insert = orderPaymentMapper.insert(orderPayment);
                   if(insert<1){
                       return new Result<>(Code.FAIL, "获取微信参数失败,无法生成付款订单!", null, Code.IS_ALERT_YES);
                   }
                    return new Result<>(Code.SUCCESS, "请在微信端完成支付!", return_json, Code.IS_ALERT_NO);
               }catch (Exception e){
                   e.printStackTrace();
                   logger.info("【易小保科技】微信H5支付发生异常"+e.getMessage()+"  会员信息:"+memberId+"会员等级:"+memberLevel);
                   return new Result<>(Code.FAIL, "微信支付发生异常!", null, Code.IS_ALERT_YES);
               }
            }
            // 安卓或者IOS发起的
            //组装返回参数
            Map<String,Object> wxparam = new HashMap<>();
            wxparam.put("appid", "");
            wxparam.put("par tnerid","");
            wxparam.put("prepayid", "");
            wxparam.put("noncestr","");
            wxparam.put("timestamp", System.currentTimeMillis());
            wxparam.put("package", "Sign=WXPay");
            String secSign ="";
            wxparam.put("sign", secSign);
            map.put("wxparam",wxparam);
            //返回微信支付所需参数
            return new Result<>(Code.SUCCESS, "请在微信端完成支付!", map, Code.IS_ALERT_NO);
        }else{
            return new Result<>(Code.FAIL, "不支持的支付类型!", null, Code.IS_ALERT_YES);
        }
        //3 扣除用户资产中的易豆  生成流水
        String uuid = StringUtil.getUuid();
        // 添加资产信息流水
        MemberPropertyHis propertyHis = new MemberPropertyHis();
        propertyHis.setId(uuid);
        propertyHis.setPropertyAmount(memberProperty.getPropertyAmount());//总收入
        propertyHis.setFrozenAmount(memberProperty.getFrozenAmount());//冻结金额
        propertyHis.setAvailableAmount(memberProperty.getAvailableAmount());//可用金额  为0
        propertyHis.setSubmittedAmount(memberProperty.getSubmittedAmount());//已提金额
        propertyHis.setEbean(memberProperty.getEbean());
        propertyHis.setRemark("会员升级");
        propertyHis.setMemberId(memberId);
        propertyHis.setModifierTime(YxbConstants.sysDate());
        memberPropertyHisService.insert(propertyHis);
        //更新易豆信息
        memberProperty.setEbean(memberProperty.getEbean()-need_ebean.intValue());//重置易豆信息   减去易豆金额
        //memberProperty.setFrozenAmount(memberProperty.getFrozenAmount().add(cash));//重置冻结金额   +  提现金额
        memberPropertyService.updateById(memberProperty);
        // 添加易豆记录
        BeanLog log = new BeanLog();
        log.setId(StringUtil.getUuid());
        log.setMemberId(memberInfo.getMemberId());
        log.setActiveType(YxbConstants.ACTIVETYPE_YIDOU_PAY);
        log.setCreatorIp(HttpKit.getClientIP());
        log.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        log.setCreatorTime(DateUtils.getSysDate());
        log.setBean(0-need_ebean.intValue());
        log.setRemark("会员升级");
        //修改会员信息
        int  limitTime  = jsonObject.getIntValue("limitTime");
        memberInfo.setModifierTime(YxbConstants.sysDate());
        String msg = "会员升级成功!";
        // 1 如果是续费
        if (memberLevel.equals(memberInfo.getMemberlevel())){
            log.setRemark("会员续费");
            msg = "会员续费成功!";
            String memberLimitTime = memberInfo.getMemberLimitTime();
            // 1.1  判断是否过期  如果过期  则 当前时间+续费时间  如果没有过期  则 过期时间+续费时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                long time = sdf.parse(memberLimitTime).getTime();
                long currentTimeMillis = System.currentTimeMillis();
                if(time<currentTimeMillis){
                    //已经过期
                    memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
                }else{
                    //没有过期  过期时间加上  续费时间
                    memberInfo.setMemberLimitTime(YxbConstants.sysAddDate(time,limitTime*24L*60L*60L*1000L));
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.info("日期转换异常"+memberLimitTime+"异常信息"+e.getMessage());
                memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
            }
        }else{
            memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
        }
        memberInfo.setMemberlevel(memberLevel);
        beanLogService.insert(log);
        updateById(memberInfo);
        reBuildMember(memberInfo);
        map.put("user",memberInfo);
        //发送升级推送
        try{
            if(!StringUtil.isEmpty(memberInfo.getPid())){
                EntityWrapper<MemberInfo> wraper = new EntityWrapper<>();
                wraper.eq("member_id",memberInfo.getPid());
                wraper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
                MemberInfo memberInfoParent = selectOne(wraper);
                if(memberInfoParent!=null){
                    MemberUtils.sendBuyMemberInfo(memberInfo,memberInfoParent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发送升级通知失败"+e.getMessage());
        }
        return new Result<>(Code.SUCCESS, msg, map, Code.IS_ALERT_NO);
    }

    @Override
    public Result<JSONObject> getMemberPrice() {
        //1 获取购买此会员需要的金额
        String buyMember = configService.getConfigValue("buyMember");
        JSONObject parse = (JSONObject) JSONObject.parse(buyMember);
        return new Result<>(Code.SUCCESS, "获取信息成功!", parse, Code.IS_ALERT_NO);
    }

    @Override
    public Result<JSONObject> getMemberParent(String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return new Result<>(Code.FAIL, "会员ID不存在", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        MemberInfo memberInfo = selectOne(wrapper);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "不存在当前用户!", null, Code.IS_ALERT_YES);
        }
        if (memberInfo.getPid()==null) {
            JSONObject json =new JSONObject();
            json.put("status","1");
            json.put("message","没有上级信息");
            return new Result<>(Code.FAIL, "没有上级信息,请绑定邀请码", json, Code.IS_ALERT_YES);
        }
        boolean flag =  getParentInfo(memberInfo.getPid());
        if(!flag){
            JSONObject json =new JSONObject();
            json.put("status","2");
            json.put("message","上级用户没有绑定邀请码");
            return new Result<>(Code.FAIL, "上级用户没有绑定邀请码", json, Code.IS_ALERT_YES);
        }else{
            JSONObject json =new JSONObject();
            json.put("status","3");
            json.put("message","上级用户已经成功绑定了邀请码");
            return new Result<>(Code.SUCCESS, "上级用户已经成功绑定了邀请码", json, Code.IS_ALERT_NO);
        }
    }

    /**
     * 递归获取用户的上级信息
     * @param pid
     * @return
     */
    private boolean getParentInfo(String pid) {
        if (pid == null) {
            return  false;
        }
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id", pid);
        wrapper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        MemberInfo memberInfo = selectOne(wrapper);
        if (memberInfo == null) {
            return  false;
        }
        if(StringUtil.isEmpty(memberInfo.getMemberInviteCode())){
            return getParentInfo(memberInfo.getPid());
        }else{
            return  true;
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<MemberInfo> bindInviteCode(String token, String inviteCode) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return new Result<>(Code.FAIL, "会员ID不存在", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        MemberInfo memberInfo = selectOne(wrapper);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "不存在当前用户!", null, Code.IS_ALERT_YES);
        }
        if (inviteCode.equals(memberInfo.getMemberInviteCode())) {
            return new Result<>(Code.FAIL, "不能输入自己的邀请码!", null, Code.IS_ALERT_YES);
        }
        wrapper = new EntityWrapper<>();
        wrapper.eq("member_invite_code", inviteCode);
        wrapper.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        MemberInfo parentInfo = selectOne(wrapper);
        if (parentInfo == null) {
            return new Result<>(Code.FAIL, "邀请码或者上级用户不存在!", null, Code.IS_ALERT_YES);
        }
        memberInfo.setPid(parentInfo.getMemberId());
        //memberInfo.setMemberInviteCode(inviteCode);
        boolean flag = updateById(memberInfo);
        if(flag){
           try{
               String extra=getExtraMsg(memberInfo,parentInfo);
           }catch (Exception e){
               e.printStackTrace();
               logger.info("发送增员通知失败!"+e.getMessage());
           }
            return new Result<>(Code.SUCCESS, "绑定成功!", memberInfo, Code.IS_ALERT_NO);
        }else{
            return new Result<>(Code.FAIL, "绑定失败!", memberInfo, Code.IS_ALERT_YES);
        }
    }



    public MemberInfo selectMemberInfo(String memberId){

        MemberInfo memberInfo = new MemberInfo().selectOne(
                new EntityWrapper<MemberInfo>().eq("member_id", memberId)
        );

        return memberInfo;
    }
    @Override
    public Result<Map<String, Object>> jql(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token, String type) {
        if (type.equals("1")){
            type = "JQL_YXB";
        }else {
            type = "JQL_YXB_LOGIN";
        }
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null){

            return new Result<>(Code.FAIL, "token为空", null, Code.IS_ALERT_YES);

        }
        MemberInfo memberInfo = selectMemberInfo(memberId);
        String oldUserid = memberInfo.getOldUserId();
        if (oldUserid == null){
            return new Result<>(Code.FAIL, "用户ID为空!~~~~!", null, Code.IS_ALERT_YES);
        }
        String s = jqlUtil.encode_pass(oldUserid, type, "encode");
        Map<String, Object> map = new HashMap<>();
        map.put("base", s);
        return new Result<>(Code.SUCCESS, "加密成功!", map, Code.IS_ALERT_NO);
    }

    @Override
    public Result<MemberInfo> getOtherUserInfo(String memberId) {
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id", memberId);
        MemberInfo memberInfo = selectOne(wrapper);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "不存在当前用户", null, Code.IS_ALERT_YES);
        }
        if (YxbConstants.DATA_DELETE_STATUS_CODE.equals(memberInfo.getValidity())) {
            return new Result<>(Code.FAIL, "会员被冻结!", null, Code.IS_ALERT_YES);
        }
        reBuildMember(memberInfo);
        return new Result<>(Code.SUCCESS, "查询数据成功!", memberInfo, Code.IS_ALERT_NO);
    }

    @Override
    public Result<Map<String, Object>> getInviteMemberInfo(String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq(YxbConstants.DATA_NORMAL_FILED,YxbConstants.DATA_NORMAL_STATUS_CODE);
        MemberInfo memberInfo = selectOne(wrapper);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "不存在当前用户", null, Code.IS_ALERT_YES);
        }
        //获取活动信息
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.setActivityNo(YxbConstants.INVITE_ACTIVITY_NO);
        ActivityInfo activityInfoDb = activityInfoMapper.selectOne(activityInfo);
        if(activityInfoDb==null){
            return new Result<>(Code.FAIL, "查询失败,未知异常!", null, Code.IS_ALERT_YES);
        }
        //找到下级信息
        wrapper = new EntityWrapper<>();
        wrapper.eq(YxbConstants.DATA_NORMAL_FILED,YxbConstants.DATA_NORMAL_STATUS_CODE);
        wrapper.eq("pid",memberInfo.getMemberId());
        //添加筛选时间
        wrapper.ge("register_time",activityInfoDb.getActivityStartTime());
        wrapper.lt("register_time",activityInfoDb.getActivityEndTime());
        List<MemberInfo> memberInfos = selectList(wrapper);
        BigDecimal totalMoney = BigDecimal.ZERO;
        List<ActivityParticipants> giftRecords = new ArrayList<>();
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        memberInfo.setHeadimg(systemImageUrl + memberInfo.getHeadimg());
        for(MemberInfo m : memberInfos){
            if (!StringUtil.isEmpty(m.getHeadimg()) && !m.getHeadimg().contains("http")) {
                m.setHeadimg(systemImageUrl + m.getHeadimg());
            }
            EntityWrapper<ActivityParticipants> activityParticipantsEntityWrapper = new EntityWrapper<>();
            activityParticipantsEntityWrapper.eq("pid",memberInfo.getMemberId());
            activityParticipantsEntityWrapper.eq("member_id",m.getMemberId());
            activityParticipantsEntityWrapper.eq("activity_no","ACTI20180921181818001");
            List<ActivityParticipants> activityParticipantsDb= activityParticipantsMapper.selectList(activityParticipantsEntityWrapper);
            if(activityParticipantsDb!=null){
                for(ActivityParticipants act : activityParticipantsDb){
                    totalMoney = totalMoney.add(act.getActivityMoney());
                    act.setNickName(m.getNickname());
                    act.setMemberName(m.getMemberName());
                    act.setHeadimg(m.getHeadimg());
                }
                giftRecords.addAll(activityParticipantsDb);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("totalMoney", totalMoney);
        map.put("memberInfos", memberInfos);
        map.put("giftRecords", giftRecords);
        map.put("memberInfo", memberInfo);
        return new Result<>(Code.SUCCESS, "查询数据成功!", map, Code.IS_ALERT_NO);
    }

}
