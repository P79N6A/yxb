package com.yxbkj.yxb.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberAccount;
import com.yxbkj.yxb.member.mapper.MemberAccountMapper;
import com.yxbkj.yxb.member.service.MemberAccountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员账户信息表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-02
 */
@Service
public class MemberAccountServiceImpl extends ServiceImpl<MemberAccountMapper, MemberAccount> implements MemberAccountService {
	
}
