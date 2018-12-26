package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.BeanLog;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.member.MemberProperty;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.*;
import com.yxbkj.yxb.system.mapper.*;
import com.yxbkj.yxb.system.service.*;
import com.yxbkj.yxb.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 奖品表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-10-29
 */
@Service
public class PrizeServiceImpl extends ServiceImpl<PrizeMapper, Prize> implements PrizeService {


    @Autowired
    private BeanLogMapper beanLogMapper;
    @Autowired
    private MemberPropertyMapper memberPropertyMapper;
    @Autowired
    private MemberPropertyHisMapper memberPropertyHisMapper;
    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private ConfigService sonfigService;
    @Autowired
    private PrizeRecordService prizeRecordService;
    @Autowired
    private PrizeConfService prizeConfService;
    @Autowired
    private PrizeMemberConfService prizeMemberConfService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private ActivityInfoMapper activityInfoMapper;
    private Logger logger = LoggerFactory.getLogger(getClass());



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> executeDraw(String token,String activityNo,String ip) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return new Result<>(Code.FAIL, "会员ID不存在", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(activityNo)){
            return new Result<>(Code.FAIL, "活动编号不能为空", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<Prize> wrapper = new EntityWrapper<>();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        wrapper.eq("activity_no",activityNo);
        wrapper.orderBy("prize_sort",false);
        List<Prize> prizes =    selectList(wrapper);
        if(prizes.size()<=0){
            return new Result<Map<String, Object>>(Code.FAIL,"抽奖失败，奖池无奖品",null,Code.IS_ALERT_YES);
        }
        int todayDrawCount = getTodayDrawCountInner(memberId,activityNo);
        if(todayDrawCount>=10){
            if(YxbConfig.active.equals("prod")){
                return new Result<Map<String, Object>>(Code.FAIL,"当日累计抽奖已超过10次!请明天再来!",null,Code.IS_ALERT_YES);
            }
           // return new Result<Map<String, Object>>(Code.FAIL,"当日累计抽奖已超过10次!请明天再来!",null,Code.IS_ALERT_YES);
        }
        ActivityInfo act = new ActivityInfo();
        act.setActivityNo(activityNo);
        ActivityInfo activityInfo = activityInfoMapper.selectOne(act);
        int useYeban = activityInfo.getExt2().intValue();
        MemberProperty memberPropertyEx = new MemberProperty();
        memberPropertyEx.setMemberId(memberId);
        MemberProperty memberProperty = memberPropertyMapper.selectOne(memberPropertyEx);
        if(memberProperty.getEbean()<useYeban){
            return new Result<Map<String, Object>>(Code.FAIL,"抽奖失败，易豆不足",null,Code.IS_ALERT_YES);
        }
        String systemImageUrl = sonfigService.getConfigValue("systemImageUrl");
        //定义默认奖项   降序排序  取最后一个
        Prize prize = prizes.get(prizes.size()-1);
        //根据中奖算法计算出中奖信息
        Prize prizeFinal = calcDraw(memberId, prizes);
        //抽奖完成  判断更新库存
        if(prizeFinal!=null){
            prizeFinal.setRealTotal(prizeFinal.getRealTotal()-1);
            updateById(prizeFinal);
            prize = prizeFinal;
        }
        //1  扣除易豆 变更资产记录
        createYiBean(memberId,memberProperty,ip,useYeban);
       //2  生成抽奖记录
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        memberInfo.setMemberId(memberId);
        MemberInfo memberInfoDb = memberInfoMapper.selectOne(memberInfo);
        PrizeRecord record = new PrizeRecord();
        String prizeRecordId = StringUtil.getUuid();
        record.setId(prizeRecordId);
        record.setMemberId(memberId);
        record.setActivityNo(prize.getActivityNo());
        record.setPrizeId(prize.getPrizeId());
        record.setCreatorIp(ip);
        record.setPrizeNum(1);
        record.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        record.setRemark("恭喜"+memberId+"抽中"+prize.getPrizeName());
        String time = YxbConstants.sysDate();
        record.setCreatorTime(time);
        record.setPrizeTime(time);
        record.setExt1(memberInfoDb.getPhone().replace(memberInfoDb.getPhone().substring(3,7),"****"));
        record.setExt4(prize.getPrizeName());
        prizeRecordService.insert(record);
        //如果是易豆或者金额
        if("10000671".equals(prize.getPrizeType())){
            //易豆
            memberPropertyEx = new MemberProperty();
            memberPropertyEx.setMemberId(memberId);
            memberProperty = memberPropertyMapper.selectOne(memberPropertyEx);
            sendYiBean(memberId,memberProperty,ip,prize.getPrizeVal().intValue());
        }
        if("10000672".equals(prize.getPrizeType())){
            //现金
            memberPropertyEx = new MemberProperty();
            memberPropertyEx.setMemberId(memberId);
            memberProperty = memberPropertyMapper.selectOne(memberPropertyEx);
            sendGift(memberId,memberProperty,ip,prize.getPrizeVal().intValue());
        }
        //最后一步  组装数据返回
        if(!StringUtil.isEmpty(prize.getPrizeImg())){
            prize.setPrizeImg(systemImageUrl+prize.getPrizeImg());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("gift",prize);
        map.put("prizeRecordId",prizeRecordId);
        return new Result<Map<String, Object>>(Code.SUCCESS,"抽奖成功",map,Code.IS_ALERT_NO);
    }

    /**
     * 计算中奖信息
     * @param memberId
     * @return
     */
    private Prize calcDraw(String memberId,List<Prize> prizes) {
        //检测是否为预设人员
        EntityWrapper<PrizeMemberConf> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        wrapper.eq("is_prize","10000562");  //未中奖的
        int count = prizeMemberConfService.selectCount(wrapper);
        //统计抽奖的总次数
        EntityWrapper<PrizeRecord> wrapper_record = new EntityWrapper<>();
        if(prizes.size()>0){
            wrapper_record.eq("activity_no",prizes.get(0).getActivityNo());
        }
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        int total_record_count = prizeRecordService.selectCount(wrapper_record);
        if(count>0){
            //执行预设人员算法
            logger.info("执行预设人员算法"+memberId);
            return haveMember(memberId,prizes,total_record_count);
        }else{
            //执行非预设人员算法
            logger.info("执行非预设人员算法"+memberId);
            return noMember(memberId,prizes,total_record_count);
        }
    }

    /**
     * 计算没有预设人员的情况
     * @param memberId
     * @return
     */
    private Prize noMember(String memberId,List<Prize> prizes,int total_record_count) {
        EntityWrapper<PrizeRecord> wrapper_record = new EntityWrapper<>();
        for(Prize bean : prizes){
            //获取奖品基数
            EntityWrapper<PrizeConf> wrapper_conf = new EntityWrapper<>();
            wrapper_conf.eq("prize_id",bean.getPrizeId());
            PrizeConf prizeConf = prizeConfService.selectOne(wrapper_conf);
            if(prizeConf==null){
                continue;
            }
            //中奖基数
            Integer winNum = prizeConf.getWinNum();
            wrapper_record = new EntityWrapper<>();
            wrapper_record.eq("prize_id",bean.getPrizeId());
            wrapper_record.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
            if(prizes.size()>0){
                wrapper_record.eq("activity_no",prizes.get(0).getActivityNo());
            }
            //中奖个数
            int irecord_count = prizeRecordService.selectCount(wrapper_record);
            logger.info("奖品"+bean.getPrizeId()+"中奖参数 总数"+total_record_count+"中奖基数"+winNum+"中奖个数"+irecord_count+"奖品库存"+bean.getRealTotal());
            int res = total_record_count/winNum-irecord_count;
            if(res>0){
                //库存大于0
                if(bean.getRealTotal()>0){
                    return bean;
                }
            }
        }
        return null;
    }
    /**
     * 计算有预设人员的情况
     * @param memberId
     * @return
     */
    private Prize haveMember(String memberId,List<Prize> prizes,int total_record_count) {
        for(Prize bean : prizes){
            EntityWrapper<PrizeMemberConf> wrapper = new EntityWrapper<>();
            wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
            wrapper.eq("prize_id",bean.getPrizeId());
            wrapper.eq("member_id",memberId);
            wrapper.eq("is_prize","10000562");  //未中奖的
            PrizeMemberConf prizeMemberConf = prizeMemberConfService.selectOne(wrapper);
            //没有设置 该会员该奖励 的预设
            if(prizeMemberConf==null){
                continue;
            }
            //总的抽奖次数 小于  设置的总次数
            if(total_record_count<prizeMemberConf.getMinTotalNum()){
                continue;
            }
            //获取自己的抽奖次数
            EntityWrapper<PrizeRecord> wrapper_member = new EntityWrapper<>();
            wrapper_member.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
            wrapper_member.eq("member_id",memberId);
            if(prizes.size()>0){
                wrapper_member.eq("activity_no",prizes.get(0).getActivityNo());
            }
            int selfCount = prizeRecordService.selectCount(wrapper_member);
            //如果自己的抽奖次数 > 设置的自己需要次数
            if(selfCount>=prizeMemberConf.getMinSelfNum()){
                if(bean.getRealTotal()>0){
                    //更新该会员为已经中奖
                    prizeMemberConf.setIsPrize("10000561");//设为已经中奖
                    prizeMemberConfService.updateById(prizeMemberConf);
                    return bean;
                }else{
                    continue;
                }
            }else{
                continue;
            }

        }
        return null;
    }



    /**
     * 创建日志消费记录
     * @param memberId
     * @param memberProperty
     */
    private void createYiBean(String memberId,MemberProperty memberProperty,String ip,int bean) {
        //int bean = 20;
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
        propertyHis.setRemark("易豆抽奖");
        propertyHis.setMemberId(memberId);
        propertyHis.setModifierTime(YxbConstants.sysDate());
        memberPropertyHisMapper.insert(propertyHis);
        //更新易豆信息
        memberProperty.setEbean(memberProperty.getEbean()-bean);//重置易豆信息   减去易豆金额
        //memberProperty.setFrozenAmount(memberProperty.getFrozenAmount().add(cash));//重置冻结金额   +  提现金额
        memberPropertyMapper.updateById(memberProperty);
        // 添加易豆记录
        BeanLog log = new BeanLog();
        log.setId(StringUtil.getUuid());
        log.setMemberId(memberId);
        log.setActiveType(YxbConstants.ACTIVETYPE_YIDOU_PAY);
        log.setCreatorIp(ip);
        log.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        log.setCreatorTime(DateUtils.getSysDate());
        log.setBean(0-bean);
        log.setRemark("易豆抽奖");
        beanLogMapper.insert(log);
    }

    /**
     * 发放易豆
     * @param memberId
     * @param memberProperty
     */
    private void sendYiBean(String memberId,MemberProperty memberProperty,String ip,int bean ) {
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
        propertyHis.setRemark("活动中奖");
        propertyHis.setMemberId(memberId);
        propertyHis.setModifierTime(YxbConstants.sysDate());
        memberPropertyHisMapper.insert(propertyHis);
        //更新易豆信息
        memberProperty.setEbean(memberProperty.getEbean()+bean);//重置易豆信息   减去易豆金额
        //memberProperty.setFrozenAmount(memberProperty.getFrozenAmount().add(cash));//重置冻结金额   +  提现金额
        memberPropertyMapper.updateById(memberProperty);
        // 添加易豆记录
        BeanLog log = new BeanLog();
        log.setId(StringUtil.getUuid());
        log.setMemberId(memberId);
        log.setActiveType(YxbConstants.ACTIVETYPE_YIDOU_PAY);
        log.setCreatorIp(ip);
        log.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        log.setCreatorTime(DateUtils.getSysDate());
        log.setBean(bean);
        log.setRemark("活动中奖");
        beanLogMapper.insert(log);
    }

    /**
     * 发放易豆
     * @param memberId
     * @param memberProperty
     */
    private void sendGift(String memberId,MemberProperty memberProperty,String ip,int money ) {
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
        propertyHis.setRemark("活动中奖");
        propertyHis.setMemberId(memberId);
        propertyHis.setModifierTime(YxbConstants.sysDate());
        memberPropertyHisMapper.insert(propertyHis);
        //更新可用金额
        memberProperty.setAvailableAmount(memberProperty.getAvailableAmount().add(new BigDecimal(money)));
        memberProperty.setPropertyAmount(memberProperty.getPropertyAmount().add(new BigDecimal(money)));//添加总收入
        memberPropertyMapper.updateById(memberProperty);

    }

    @Override
    public Result<List<Prize>> getPrizeList(String activityNo) {
        if(StringUtil.isEmpty(activityNo)){
            return new Result<>(Code.FAIL,"活动编号不能为空!",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<Prize> wrapper = new EntityWrapper<>();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        wrapper.eq("activity_no",activityNo);
        wrapper.orderBy("prize_sort",false);
        List<Prize> prizes = selectList(wrapper);
        String systemImageUrl = sonfigService.getConfigValue("systemImageUrl");
        for(Prize bean :prizes){
            if(!StringUtil.isEmpty(bean.getPrizeImg())){
                bean.setPrizeImg(systemImageUrl+bean.getPrizeImg());
            }
        }
        return new Result<>(Code.SUCCESS,"获取奖品列表成功!",prizes,Code.IS_ALERT_NO);
    }

    @Override
    public Result<Integer> getTodayDrawCount(String token,String activityNo) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return new Result<>(Code.FAIL, "会员ID不存在", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(activityNo)){
            return new Result<>(Code.FAIL, "活动编号不能为空!", null, Code.IS_ALERT_YES);
        }
        Integer todayDrawCountInner = getTodayDrawCountInner(memberId,activityNo);
        return new Result<>(Code.SUCCESS,"获取成功!",todayDrawCountInner,Code.IS_ALERT_NO);

    }

    private Integer getTodayDrawCountInner(String memberId,String activityNo){
        EntityWrapper<PrizeRecord> logWrapper = new EntityWrapper<>();
        logWrapper.eq("activity_no",activityNo);
        logWrapper.eq("member_id",memberId);
        logWrapper.ge("creator_time",LocalDate.now().toString()+" 00:00:00");
        return prizeRecordService.selectCount(logWrapper);
    }

}
