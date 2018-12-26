package com.yxbkj.yxb.member.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.member.BeanLog;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.module.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <p>
 * 易豆日志表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-08-13
 */
public interface BeanLogService extends IService<BeanLog> {

    Result<Map<String,Object>> memberSignIn(String token,String activeType);

    Result<Boolean> todaySignIn(String token);
   Result<Page<BeanLog>> getBeanLog(String token,String type,Integer offset,Integer limit);
}
