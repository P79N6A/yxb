package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.system.ActivityInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public interface ActivityInfoService extends IService<ActivityInfo> {
    Result<Map<String, Object>> addActivityInfo(String activityNo, String activityDesc, String activityMoney, String creator, Date activityStartTime, Date activityEndTime, HttpServletRequest request);
    Result<Boolean> isStart(String activityNo);
    Result<Map<String, Object>> deleteByActivityNo(String activityNo);
}
