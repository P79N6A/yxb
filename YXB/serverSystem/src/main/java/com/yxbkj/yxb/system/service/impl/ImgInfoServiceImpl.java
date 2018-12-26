package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.ImgInfo;
import com.yxbkj.yxb.entity.member.MemberPropertyHis;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.ImgInfoMapper;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.ImgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.sql.Clob;
import java.util.*;

/**
 * <p>
 * 图片管理 服务实现类
 * </p>
 *
 * @author 唐漆
 * @since 2018-08-03
 */
@Service
public class ImgInfoServiceImpl extends ServiceImpl<ImgInfoMapper, ImgInfo> implements ImgInfoService {
    @Autowired
	private ImgInfoMapper imgInfoMapper;
    @Autowired
    private ConfigService sonfigService;

    public List<ImgInfo> selectImgType(String addType) {
            List<ImgInfo> imgList = imgInfoMapper.selectList(
                    new EntityWrapper<ImgInfo>().eq("validity", 10000001).in("add_type", addType).orderBy("sort")
            );

            return imgList;

    }

    public Result<Map<String, Object>> imgInfo(String addType){

        Result<Map<String, Object>> result = null;
        List<ImgInfo> selectType = selectImgType(addType);

        Map<String, Object> map = new HashMap<>();

        String systemImageUrl = sonfigService.getConfigValue("systemImageUrl");

        Set<String> set = new HashSet<>();

            for (int i = 0; i < selectType.size(); i++) {
                String addType1 = selectType.get(i).getAddType();
                set.add(addType1);
            }

            for (String s : set){
                List<ImgInfo> list = new ArrayList<>();
                for (int r = 0; r < selectType.size(); r++){
                    String addType2 = selectType.get(r).getAddType();
                    if (addType2.equals(s)){
                        list.add(selectType.get(r));
                    }
                }
                map.put(s,list);
            }
            for (ImgInfo imgInfo : selectType){
                imgInfo.setImgUrl(systemImageUrl+imgInfo.getImgUrl());
            }

        result = new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
        return result;
    }


}
