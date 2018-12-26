package com.yxbkj.yxb.member.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.member.TrafficViolations;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.ZrpxParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理人认证 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-08-17
 */
public interface ZhongruiService  {
    Result<Map<String,Object>> baseAuth(ZrpxParam param);
}
