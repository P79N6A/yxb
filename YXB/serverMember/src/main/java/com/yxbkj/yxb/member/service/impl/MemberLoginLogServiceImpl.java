package com.yxbkj.yxb.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberLoginLog;
import com.yxbkj.yxb.member.mapper.MemberLoginLogMapper;
import com.yxbkj.yxb.member.service.MemberLoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@Service
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogMapper, MemberLoginLog> implements MemberLoginLogService {
	
}
