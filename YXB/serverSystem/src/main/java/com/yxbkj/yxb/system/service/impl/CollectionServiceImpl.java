package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.*;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.ProductAnalysis;
import com.yxbkj.yxb.entity.product.ProductRepertory;
import com.yxbkj.yxb.system.mapper.*;
import com.yxbkj.yxb.system.service.CollectionService;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private InsertCommentMapper insertCommentMapper;
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private NewssMapper newssMapper;
    @Autowired
    private AnalysisMapper analysisMapper;
    @Autowired
    private ProductRepertoryMapper productRepertoryMapper;
    @Autowired
    private LikeMapper likeMapper;

    @Override
    public Result<Map<String, Object>> myCollection(String token, Integer limit, Integer offset) {
//        limit = (limit - 1) * offset;
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;
        if (StringUtil.isEmpty(memberId)) {
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }

        ProductAnalysis productAnalysis = new ProductAnalysis();
        ProductRepertory productRepertory = new ProductRepertory();
        MemberInfo memberInfo = new MemberInfo();
        Newss newss = new Newss();
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        Page<Collection> page = new Page<>(limit, offset);
        Page<Collection> page1 = selectPage(page, new EntityWrapper<Collection>().eq("member_id", memberId).eq("validity", 10000001).orderBy("collection_time desc"));
        List<Collection> collectionList = page1.getRecords();
        Integer collCount = collectionMapper.selectCount(new EntityWrapper<Collection>().eq("member_id", memberId).eq("validity", 10000001));
        for (int i = 0; i < collectionList.size(); i++) {
            String beCollectedId = collectionList.get(i).getBeCollectedId();
            Integer countComment = insertCommentMapper.selectCount(new EntityWrapper<InsertComment>().eq("be_commented_id", beCollectedId).eq("validity", 10000001));
            collectionList.get(i).setComNum(countComment);
            collectionList.get(i).setCollNum(collCount);
            String collectionType = collectionList.get(i).getCollectionType();
            if (YxbConstants.ARTICLE_TYPE_NEWS.equals(collectionType)){
                collectionList.get(i).setCheckLike(false);
                    if (memberId != null && !"".equals(memberId)) {
                        Like like = new Like();
                        like.setMemberId(memberId);
                        like.setBeLikedId(beCollectedId);
                        Like like1 = likeMapper.selectOne(like);
                        if (like1 != null && !"".equals(like1)) {
                            collectionList.get(i).setCheckLike(true);
                        }
                    }
                //获取创建人ID
                newss.setNewsId(beCollectedId);
                Newss newss1 = newssMapper.selectOne(newss);
                String creatorId = newss1.getCreatorId();
                String content = newss1.getContent();
                String title = newss1.getTitle();
                String creatorTime = newss1.getCreatorTime();
                Integer readNum = newss1.getReadNum();
                String ext4 = newss1.getExt4();
                String newsId = newss1.getNewsId();
                String img = newss1.getImg();
                collectionList.get(i).setContent(content);
                collectionList.get(i).setTitle(title);
                collectionList.get(i).setCreatorTime(creatorTime);
                collectionList.get(i).setReadNum(readNum);
                collectionList.get(i).setExt4(ext4);
                collectionList.get(i).setCreatorId(creatorId);
                collectionList.get(i).setNewsId(newsId);
                if (StringUtil.isEmpty(img)){
                    collectionList.get(i).setImg(img);
                }else {
                    collectionList.get(i).setImg(systemImageUrl + img);
                }
                //获取创建人头像
                memberInfo.setMemberId(creatorId);
                MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
                    if (memberInfo1 != null && !StringUtil.isEmpty(memberInfo1.getHeadimg())) {
                        collectionList.get(i).setNickName(memberInfo1.getNickname());
                        collectionList.get(i).setHeadimg(systemImageUrl + memberInfo1.getHeadimg());
                    } else {
                        StaffInfo staffInfo = new StaffInfo();
                        staffInfo.setStaffId(creatorId);
                        StaffInfo staffInfoHeadImg = staffInfoMapper.selectOne(staffInfo);
                        if (!StringUtil.isEmpty(staffInfoHeadImg.getHeadImg())) {
                            if (!StringUtil.isEmpty(staffInfoHeadImg.getNickName())){
                                collectionList.get(i).setNickName(staffInfoHeadImg.getNickName());
                            }else {
                                collectionList.get(i).setNickName(staffInfoHeadImg.getStaffName());
                            }
                            collectionList.get(i).setHeadimg(systemImageUrl + staffInfoHeadImg.getHeadImg());
                        } else {
                            if (!StringUtil.isEmpty(staffInfoHeadImg.getNickName())){
                                collectionList.get(i).setNickName(staffInfoHeadImg.getNickName());
                            }else {
                                collectionList.get(i).setNickName(staffInfoHeadImg.getStaffName());
                            }
                            collectionList.get(i).setHeadimg(staffInfoHeadImg.getHeadImg());
                        }
                }
            } else if (YxbConstants.ARTICLE_TYPE_PRODUCT.equals(collectionType)){

                collectionList.get(i).setCheckLike(false);
                if (memberId != null && !"".equals(memberId)) {
                    Like like = new Like();
                    like.setMemberId(memberId);
                    like.setBeLikedId(beCollectedId);
                    Like like1 = likeMapper.selectOne(like);
                    if (like1 != null && !"".equals(like1)) {
                        collectionList.get(i).setCheckLike(true);
                    }
                }
                productAnalysis.setProductId(beCollectedId);
                productRepertory.setProductId(beCollectedId);
                productRepertory.setValidity("10000001");
                ProductRepertory productRepertory1 = productRepertoryMapper.selectOne(productRepertory);
                if (productRepertory1 != null) {
                    String productDesc = productRepertory1.getProductDesc();
                    String title = productRepertory1.getProductName();
                    String creatorTime = productRepertory1.getCreatorTime();
                    String productId = productRepertory1.getProductId();
                    String readNum = productRepertory1.getReadNum();
                    String creatorId = productRepertory1.getCreatorId();
                    String img = productRepertory1.getProductImg();
                    collectionList.get(i).setTitle(title);
                    collectionList.get(i).setCreatorTime(creatorTime);
                    collectionList.get(i).setProductId(productId);
                    collectionList.get(i).setReadNum(Integer.parseInt(readNum));
                    collectionList.get(i).setCreatorId(creatorId);
                    if (StringUtil.isEmpty(img)) {
                        collectionList.get(i).setImg(img);
                    } else {
                        collectionList.get(i).setImg(systemImageUrl + img);
                    }
                    collectionList.get(i).setExt4(productDesc);
                //获取创建人头像
                memberInfo.setMemberId(creatorId);
                MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
                    if (memberInfo1 != null && !StringUtil.isEmpty(memberInfo1.getHeadimg())) {
                        collectionList.get(i).setNickName(memberInfo1.getNickname());
                        collectionList.get(i).setHeadimg(systemImageUrl + memberInfo1.getHeadimg());
                    } else {
                        StaffInfo staffInfo = new StaffInfo();
                        staffInfo.setStaffId(creatorId);
                        StaffInfo staffInfoHeadImg = staffInfoMapper.selectOne(staffInfo);
                        if (!StringUtil.isEmpty(staffInfoHeadImg.getHeadImg())) {
                            if (!StringUtil.isEmpty(staffInfoHeadImg.getNickName())){
                                collectionList.get(i).setNickName(staffInfoHeadImg.getNickName());
                            }else {
                                collectionList.get(i).setNickName(staffInfoHeadImg.getStaffName());
                            }
                            collectionList.get(i).setHeadimg(systemImageUrl + staffInfoHeadImg.getHeadImg());
                        } else {
                            if (!StringUtil.isEmpty(staffInfoHeadImg.getNickName())){
                                collectionList.get(i).setNickName(staffInfoHeadImg.getNickName());
                            }else {
                                collectionList.get(i).setNickName(staffInfoHeadImg.getStaffName());
                            }
                            collectionList.get(i).setHeadimg(staffInfoHeadImg.getHeadImg());
                        }
                    }

                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("collection", collectionList);
        map.put("collCount", collCount);
        return new Result<>(Code.SUCCESS, "我的收藏", map, Code.IS_ALERT_NO);
    }
}


