package com.yxbkj.yxb.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.app.Like;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论 接口
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-21
 */
public interface LikeMapper extends BaseMapper<Like> {
    List<Like> insertLike(@Param("beLikedId") String beLikedId,
                          @Param("memberId") String memberId,
                          @Param("likeType") String likeType);
}
