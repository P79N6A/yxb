package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.ImgInfo;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

/**
 * <p>
 * 图片管理 服务类
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-03
 */
public interface ImgInfoService extends IService<ImgInfo> {
    Result<Map<String, Object>> imgInfo(String addType);
}
