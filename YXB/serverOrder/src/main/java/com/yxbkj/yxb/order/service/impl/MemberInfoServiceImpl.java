package com.yxbkj.yxb.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.member.MemberLoginLog;
import com.yxbkj.yxb.entity.member.MemberProperty;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Constants;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.vo.LoginLogVo;
import com.yxbkj.yxb.order.mapper.MemberInfoMapper;
import com.yxbkj.yxb.order.service.MemberInfoService;
import com.yxbkj.yxb.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员信息表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
@Service
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements MemberInfoService {

}
