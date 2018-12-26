package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.Newss;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 咨询 接口
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-21
 */
public interface NewssMapper extends BaseMapper<Newss> {
    List<Newss> newss(@Param("columnType") String columnType,
                    @Param("limit") Integer limit,
                    @Param("offset") Integer offset);
    Integer countnews(@Param("newsId") String newsId);
}
