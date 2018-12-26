package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Bank;
import com.yxbkj.yxb.system.mapper.BankMapper;
import com.yxbkj.yxb.system.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 银行表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-21
 */
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank> implements BankService {

}
