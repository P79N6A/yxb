package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.ActivityInfo;
import com.yxbkj.yxb.system.mapper.ActivityInfoMapper;
import com.yxbkj.yxb.system.service.ActivityInfoService;
import com.yxbkj.yxb.util.HttpKit;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动信息管理实现类
 * @author zy
 * @desc
 * @since
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper,ActivityInfo> implements ActivityInfoService {
    @Autowired
    private ActivityInfoMapper activityInfoMapper;
    /**
     * 活动添加
     * @author zy
     * @desc
     * @since
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> addActivityInfo(String activityName, String activityDesc, String activityMoney, String creator, Date activityStartTime, Date activityEndTime, HttpServletRequest request) {
        ActivityInfo activityInfo = new ActivityInfo();
        String clientIP = HttpKit.getClientIP(request);
        String timeString = StringUtil.dateCastToStringS(new Date());
        activityInfo.setActivityNo("ACTI" + timeString);
        BigDecimal activity_Money = new BigDecimal(activityMoney);
        activityInfo.setActivityMoney(activity_Money);
        activityInfo.setCreatorIp(clientIP);
        activityInfo.setCreatorTime(StringUtil.getCurrentDateStr());
        activityInfo.setCreator(creator);
        activityInfo.setActivityDesc(activityDesc);
        activityInfo.setActivityEndTime(StringUtil.dateCastToString(activityEndTime));
        activityInfo.setActivityStartTime(StringUtil.dateCastToString(activityStartTime));
        activityInfo.setId(StringUtil.getUuid());
        activityInfo.setValidity("10000001");
        activityInfo.setActivityName(activityName);
        Integer insert = activityInfoMapper.insert(activityInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("activityInfo", activityInfo);
        if(insert < 0) {
            return new Result<>(Code.SUCCESS, "添加失败", map, Code.IS_ALERT_NO);
        }
        return new Result<>(Code.SUCCESS, "添加成功", map, Code.IS_ALERT_NO);
    }
    /**
     * 活动是否在进行中
     * @author zy
     * @desc
     * @since
     */
    @Override
    public Result<Boolean> isStart(String activityNo) {
        Result<Boolean> result = null;
        EntityWrapper<ActivityInfo> aw = new EntityWrapper<>();
        aw.eq("activity_no",activityNo);
        aw.eq("validity", YxbConstants.DATA_NORMAL_STATUS_CODE);
        ActivityInfo activityInfo = selectOne(aw);
        if(activityInfo == null) {
            return result = new Result<>(Code.FAIL, "活动结束或者不存在", false, Code.IS_ALERT_NO);
        }
        String startTime = activityInfo.getActivityStartTime();
        Date startDate = StringUtil.stringCastToDate(startTime);
        String endTime = activityInfo.getActivityEndTime();
        Date endDate = StringUtil.stringCastToDate(endTime);
        boolean flag = StringUtil.isEffectiveDate(new Date(), startDate, endDate);
        if(!flag) {
            return result = new Result<>(Code.FAIL,"活动还没开始或者已结束",false,Code.IS_ALERT_NO);
        } else {
            return result = new Result<>(Code.SUCCESS,"活动正在进行中",true,Code.IS_ALERT_YES);
        }
    }
    /**
     * 删除活动
     * @author zy
     * @desc
     * @since
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> deleteByActivityNo(String activityNo) {
        Result<Map<String, Object>> result = null;
        EntityWrapper<ActivityInfo> aw = new EntityWrapper<>();
        aw.eq("activity_no",activityNo);
        aw.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        ActivityInfo activityInfo = selectOne(aw);
        Map<String, Object> map = new HashMap<>();
        if(activityInfo == null) {
            return result = new Result<>(Code.FAIL,"活动不存在",Code.IS_ALERT_NO);
        }
        activityInfo.setValidity(YxbConstants.DATA_DELETE_STATUS_CODE);
        boolean flag = updateById(activityInfo);
        if(!flag) {
            return result = new Result<>(Code.FAIL,"活动删除失败",Code.IS_ALERT_NO);
        } else {
            return result = new Result<>(Code.SUCCESS,"活动删除成功",Code.IS_ALERT_NO);
        }
    }
}
