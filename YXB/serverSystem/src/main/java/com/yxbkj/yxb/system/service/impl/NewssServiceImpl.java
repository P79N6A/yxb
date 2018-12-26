package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.*;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.mapper.*;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.NewsService;
import com.yxbkj.yxb.system.service.NewssService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewssServiceImpl extends ServiceImpl<NewssMapper, Newss> implements NewssService {
    @Autowired
    private NewssMapper newssMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private CollMapper collMapper;
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    public int count (String newsId){
        int countnews = newssMapper.countnews(newsId);
        return countnews;
    }
    public MemberInfo memberInfo(String memberId){
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(memberId);
        MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
        return memberInfo1;
    }
    @Override
    public Result<Map<String, Object>> newss(String columnType, Integer limit, Integer offset) {
        limit = (limit-1)*offset;
        List<Newss> newss = newssMapper.newss(columnType, limit, offset);
        Map<String, Object> map = new HashMap<>();
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        for (int i = 0; i < newss.size(); i++) {
            String newsId = newss.get(i).getNewsId();
            int count = count(newsId);
            newss.get(i).setPraise(count);
            if (!StringUtil.isEmpty(newss.get(i).getFilePath())){
                newss.get(i).setFilePath(systemImageUrl+newss.get(i).getFilePath());
            }else {
                newss.get(i).setFilePath(newss.get(i).getFilePath());
            }
            if (!StringUtil.isEmpty(newss.get(i).getImg())){
                newss.get(i).setImg(systemImageUrl+newss.get(i).getImg());
            }else {
                newss.get(i).setImg(newss.get(i).getImg());
            }
            String creatorId = newss.get(i).getCreatorId();
            StaffInfo staffInfo = new StaffInfo();
            staffInfo.setStaffId(creatorId);
            StaffInfo staffInfoHeadImg = staffInfoMapper.selectOne(staffInfo);
            if (!StringUtil.isEmpty(staffInfoHeadImg.getHeadImg())){
                newss.get(i).setHeadimg(systemImageUrl + staffInfoHeadImg.getHeadImg());
                if (!StringUtil.isEmpty(staffInfoHeadImg.getNickName())){
                    newss.get(i).setCreator(staffInfoHeadImg.getNickName());
                }else {
                    newss.get(i).setCreator(staffInfoHeadImg.getStaffName());
                }
            }else {
                newss.get(i).setHeadimg(staffInfoHeadImg.getHeadImg());
                if (!StringUtil.isEmpty(staffInfoHeadImg.getNickName())){
                    newss.get(i).setCreator(staffInfoHeadImg.getNickName());
                }else {
                    newss.get(i).setCreator(staffInfoHeadImg.getStaffName());
                }
            }
        }
        map.put("news", newss);
        return new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
    }

    @Override
    public Result<Map<String, Object>> read(String newId) {
        Newss newss = new Newss();

        newss.setNewsId(newId);
        newss.setValidity("10000001");
        List<Newss> newslist = newssMapper.selectList(new EntityWrapper<Newss>().eq("news_id", newId).eq("validity", 10000001));
        if (newslist != null && newslist.size() != 0) {
            String systemImageUrl = configService.getConfigValue("systemImageUrl");
            for (Newss news1 : newslist){
                String creatorId = news1.getCreatorId();
                MemberInfo memberInfo = memberInfo(creatorId);
                if (memberInfo != null && !"".equals(memberInfo)) {
                    String headimg = memberInfo.getHeadimg();
                    if (!StringUtil.isEmpty(headimg)) {
                        news1.setHeadimg(systemImageUrl + headimg);
                    }
                }else {
                    StaffInfo staffInfo = new StaffInfo();
                    staffInfo.setStaffId(creatorId);
                    StaffInfo staffInfoHeadImg = staffInfoMapper.selectOne(staffInfo);
                    if (!StringUtil.isEmpty(staffInfoHeadImg.getHeadImg())){
                        news1.setHeadimg(systemImageUrl + staffInfoHeadImg.getHeadImg());
                        if (!StringUtil.isEmpty(staffInfoHeadImg.getNickName())){
                            news1.setCreator(staffInfoHeadImg.getNickName());
                        }else {
                            news1.setCreator(staffInfoHeadImg.getStaffName());
                        }
                    }else {
                        news1.setHeadimg(staffInfoHeadImg.getHeadImg());
                        if (!StringUtil.isEmpty(staffInfoHeadImg.getNickName())){
                            news1.setCreator(staffInfoHeadImg.getNickName());
                        }else {
                            news1.setCreator(staffInfoHeadImg.getStaffName());
                        }
                    }
                }
                    if (news1.getImg() != null && !news1.getImg().equals("")) {
                        news1.setImg(systemImageUrl + news1.getImg());
                    }

                    if (news1.getFilePath() != null && !news1.getFilePath().equals("")) {
                        news1.setFilePath(systemImageUrl + news1.getFilePath());
                    }
                    news1.setFilePath(news1.getFilePath());
                    news1.setImg(news1.getImg());
            }
            for (int i = 0; i < newslist.size(); i++) {
                String newsId = newslist.get(i).getNewsId();
                int count = count(newsId);
                newslist.get(i).setPraise(count);
            }
            Newss nes = newssMapper.selectOne(newss);
            Integer readNum = nes.getReadNum();
            int i = readNum + 1;
            nes.setReadNum(i);
            Integer integer = newssMapper.updateById(nes);
            Map<String, Object> map = new HashMap<>();
            map.put("details", newslist);

            return new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
        }
        Map<String, Object> map = new HashMap<>();
        return new Result<>(Code.SUCCESS, "此ID暂无数据!", map, Code.IS_ALERT_NO);
    }

    //查询该用户是否对该文章点赞
    @Override
    public Result<Boolean> notnews(String newsId, String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
        Like like = new Like();
        like.setBeLikedId(newsId);
        like.setMemberId(memberId);
        Like like1 = likeMapper.selectOne(like);
        if (like1 != null){
            return new Result<>(Code.SUCCESS, "已经点过赞", true, Code.IS_ALERT_NO);
        }
        return new Result<>(Code.SUCCESS, "没有点过赞", false, Code.IS_ALERT_NO);
    }

    @Override
    public Result<Boolean> notcoll(String newsId, String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
        Collection coll = new Collection();
        coll.setBeCollectedId(newsId);
        coll.setMemberId(memberId);
        Collection collection = collMapper.selectOne(coll);
        if (collection != null){
            return new Result<>(Code.SUCCESS, "已经收藏", true, Code.IS_ALERT_NO);
        }
        return new Result<>(Code.SUCCESS, "没有收藏", false, Code.IS_ALERT_NO);
    }
}
