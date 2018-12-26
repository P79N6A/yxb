package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.News;
import com.yxbkj.yxb.entity.app.Newss;
import com.yxbkj.yxb.entity.module.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 咨询 接口
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-21
 */
public interface NewsSelectMapper extends BaseMapper<Newss> {
    List<Newss> selectNews(@Param("title") String title);
    List<Newss> selectContent(@Param("content") String content);
}
