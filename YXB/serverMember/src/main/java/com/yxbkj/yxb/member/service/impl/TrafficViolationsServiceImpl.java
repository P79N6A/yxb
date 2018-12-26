package com.yxbkj.yxb.member.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.member.TrafficViolations;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.member.mapper.TrafficViolationsMapper;
import com.yxbkj.yxb.member.service.TrafficViolationsService;
import com.yxbkj.yxb.util.DateUtils;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.car.CarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class TrafficViolationsServiceImpl extends ServiceImpl<TrafficViolationsMapper, TrafficViolations> implements TrafficViolationsService {

    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> searchCarInfo(String token, String vin, String license, String engineNo) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<Map<String, Object>>(Code.FAIL,"会员ID不存在!",null,Code.IS_ALERT_YES);
        }
        String createTime = DateUtils.getSysDate();
        logger.info("【易小保科技入参】token{}  vin{} license{} engineNo{} ",token,vin,license,engineNo);
        //{"code":"000000","msg":"获取违章信息成功","data":{"fen":3,"carplate":"川A8XV66","money":100,"illegals":1,"list":[{"date":"2018-07-06 18:39:12","area":"嘉陵江路","act":"机动车违反禁止标线指示的","code":"1345","fen":"3","wzcity":"","money":"100","handled":"0","archiveno":"510104A400280444"}]}}
        String res = CarUtils.searchCarInfo(vin, license, engineNo);
        logger.info("【易小保科技车辆信息响应】res{}  ",res);
        JSONObject jsonInfo = JSONObject.parseObject(res);
        List<TrafficViolations> list = new ArrayList<>();
        if("000000".equals(jsonInfo.getString("code"))){
            //把当前车牌号码内的所有记录设置为 已处理 1
            {
                EntityWrapper<TrafficViolations> wrapper = new EntityWrapper<>();
                wrapper.eq("member_id",memberId);
                wrapper.eq("carplate",license);
                List<TrafficViolations> trafficViolations = selectList(wrapper);
                for(TrafficViolations car  : trafficViolations){
                    car.setHandled(YxbConstants.CAR_HANDED);
                    car.setCreatorTime(createTime);
                    updateById(car);
                }
            }
            JSONObject jsonObject = jsonInfo.getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for(int i = 0 ; i < jsonArray.size();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                //保存 or 更新入库
                TrafficViolations trafficViolations = new TrafficViolations();
                trafficViolations.setAct(json.getString("act"));
                trafficViolations.setArchiveno(json.getString("archiveno"));
                trafficViolations.setArea(json.getString("area"));
                trafficViolations.setCode(json.getString("code"));
                //trafficViolations.setHandled(json.getString("handled"));
                trafficViolations.setHandled(YxbConstants.CAR_NO_HANDED);
                trafficViolations.setWzcity(json.getString("wzcity"));
                trafficViolations.setDate(json.getString("date"));
                trafficViolations.setCarplate(jsonObject.getString("carplate"));
                trafficViolations.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
                trafficViolations.setVin(vin);
                trafficViolations.setEngineNo(engineNo);
                trafficViolations.setMemberId(memberId);
                trafficViolations.setCreatorTime(createTime);
                trafficViolations.setId(StringUtil.getUuid());
                try{
                    trafficViolations.setFen(json.getIntValue("fen"));
                    trafficViolations.setMoney(json.getBigDecimal("money"));
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("【易小保科技】参数转换异常  res{}  ",res);
                }
                EntityWrapper<TrafficViolations> wrapper = new EntityWrapper<>();
                wrapper.eq("carplate",trafficViolations.getCarplate());
                wrapper.eq("date",trafficViolations.getDate());
                wrapper.eq("member_id",memberId);
                if(selectCount(wrapper)>0){
                    //修改
                    updateById(trafficViolations);
                }else{
                    insert(trafficViolations);
                }
                list.add(trafficViolations);
            }
        }else{
            return new Result<Map<String, Object>>(Code.FAIL,jsonInfo.getString("msg"),null,Code.IS_ALERT_YES);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("carInfo",jsonInfo.getJSONObject("data"));
        return new Result<Map<String, Object>>(Code.SUCCESS,"查询数据成功!",map,Code.IS_ALERT_NO);
    }

    @Override
    public Result<Page<TrafficViolations>> searchCarInfoHis(String token, String type, Integer offset, Integer limit) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<Page<TrafficViolations>>(Code.FAIL,"会员ID不存在!",null,Code.IS_ALERT_YES);
        }

        Page<TrafficViolations> page = new Page(offset,limit);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("member_id",memberId);
        if(!StringUtil.isEmpty(type)){
            wrapper.eq("handled",type);
        }
        page.setOrderByField("date"); // 排序参数
        page.setAsc(false); // 为true表示顺序排列，false为倒序排列
        Page page_res = selectPage(page, wrapper);
        return new Result<Page<TrafficViolations>>(Code.SUCCESS,"查询数据成功!",page_res,Code.IS_ALERT_NO);
    }

    @Override
    public Result<List<Map<String, Object>>> carInfoStatistics(String token,String type) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<List<Map<String, Object>>>(Code.FAIL,"会员ID不存在!",null,Code.IS_ALERT_YES);
        }
        Wrapper<TrafficViolations> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("carplate,creator_time,SUM(fen) fen,SUM(money) money,count(carplate) totalCount");
        wrapper.eq("member_id",memberId);
        if(!StringUtil.isEmpty(type)){
            wrapper.eq("handled",type);
        }
        wrapper.groupBy("carplate");
        wrapper.groupBy("creator_time");
        return new Result<List<Map<String, Object>>>(Code.SUCCESS,"数据统计成功!",selectMaps(wrapper),Code.IS_ALERT_NO);
    }

    @Override
    public Result<Map<String,Object>> searchCarInfoByCarplate(String token, String type, String carplate) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<Map<String,Object>>(Code.FAIL,"会员ID不存在!",null,Code.IS_ALERT_YES);
        }
        Map<String,Object> map = new HashMap<>();
        Wrapper<TrafficViolations> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("carplate",carplate);
        if(!StringUtil.isEmpty(type)){
            wrapper.eq("handled",type);
        }
        List<TrafficViolations> trafficViolations = selectList(wrapper);
        map.put("carInfos",trafficViolations);
        // 再次构建 统计信息
        wrapper.setSqlSelect("carplate,creator_time,SUM(fen) fen,SUM(money) money,count(carplate) totalCount");
        wrapper.groupBy("carplate");
        wrapper.groupBy("creator_time");
        map.put("statistics",selectMaps(wrapper));
        return new Result<Map<String,Object>>(Code.SUCCESS,"数据查询成功!",map,Code.IS_ALERT_NO);
    }


}
