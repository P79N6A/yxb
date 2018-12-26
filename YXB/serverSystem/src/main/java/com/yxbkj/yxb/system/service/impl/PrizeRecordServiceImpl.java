package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.system.PrizeRecord;
import com.yxbkj.yxb.system.mapper.PrizeRecordMapper;
import com.yxbkj.yxb.system.service.PrizeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-10-29
 */
@Service
public class PrizeRecordServiceImpl extends ServiceImpl<PrizeRecordMapper, PrizeRecord> implements PrizeRecordService {

    @Autowired
    private PrizeRecordMapper prizeRecordMapper;

    @Override
    public List<PrizeRecord> getPrizeRecordByPage(Map<String, Object> map) {
        return prizeRecordMapper.getPrizeRecordByPage(map);
    }
}
