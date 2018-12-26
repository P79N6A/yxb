package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
import com.yxbkj.yxb.system.service.CommentService;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, InsertComment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private NewssMapper newssMapper;
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private AnalysisMapper analysisMapper;
    @Autowired
    private ProductRepertoryMapper productRepertoryMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @Override
    public Result<Map<String, Object>> comment(String beCommentedId, String token, Integer limit, Integer offset) {
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        String memberId = redisTemplateUtils.getStringValue(token);
//        String memberId = token;

        ProductAnalysis productAnalysis = new ProductAnalysis();
        ProductRepertory productRepertory = new ProductRepertory();
        MemberInfo memberInfo = new MemberInfo();
        Newss newss = new Newss();
        if (StringUtil.isEmpty(beCommentedId)) {
            Page<InsertComment> page = new Page<>(limit, offset);
            Page<InsertComment> page1 = selectPage(page, new EntityWrapper<InsertComment>().eq("member_id", memberId).eq("validity", 10000001).orderBy("comment_time desc"));
            List<InsertComment> commnetList = page1.getRecords();
            Integer commentCount = commentMapper.selectCount(new EntityWrapper<InsertComment>().eq("member_id", memberId).eq("validity", 10000001));
            for (int i = 0; i < commnetList.size(); i++) {
                String beCommentedId1 = commnetList.get(i).getBeCommentedId();
                String commentId = commnetList.get(i).getCommentId();
                String commentType = commnetList.get(i).getCommentType();
                commnetList.get(i).setCheckLike(false);
                if (memberId != null && !"".equals(memberId)) {
                    Like like = new Like();
                    like.setMemberId(memberId);
                    like.setBeLikedId(commentId);
                    Like like1 = likeMapper.selectOne(like);
                    if (like1 != null && !"".equals(like1)) {
                        commnetList.get(i).setCheckLike(true);
                    }
                }
                if (YxbConstants.ARTICLE_TYPE_NEWS.equals(commentType)) {
                    newss.setNewsId(beCommentedId1);
                    Newss newss1 = newssMapper.selectOne(newss);
                    String title = newss1.getTitle();
                    String ext4 = newss1.getExt4();
                    String img = newss1.getImg();
                    String newsId = newss1.getNewsId();
                    commnetList.get(i).setTitle(title);
                    commnetList.get(i).setExt4(ext4);
                    commnetList.get(i).setNewsId(newsId);
                    if (StringUtil.isEmpty(img)) {
                        commnetList.get(i).setImg(img);
                    } else {
                        commnetList.get(i).setImg(systemImageUrl + img);
                    }
                    //获取创建人头像
                    memberInfo.setMemberId(memberId);
                    MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
                    if (memberInfo1 != null && !StringUtil.isEmpty(memberInfo1.getNickname())) {
                        commnetList.get(i).setNickName(memberInfo1.getNickname());
                    }
                    if (memberInfo1 != null && !StringUtil.isEmpty(memberInfo1.getHeadimg())) {
                        commnetList.get(i).setHeadImg(systemImageUrl + memberInfo1.getHeadimg());
                    } else {
                        StaffInfo staffInfo = new StaffInfo();
                        staffInfo.setStaffId(memberId);
                        StaffInfo staffInfoHeadImg = staffInfoMapper.selectOne(staffInfo);
                        if (!StringUtil.isEmpty(staffInfoHeadImg.getHeadImg())) {
                            commnetList.get(i).setHeadImg(systemImageUrl + staffInfoHeadImg.getHeadImg());
                        } else {
                            commnetList.get(i).setHeadImg(staffInfoHeadImg.getHeadImg());
                        }
                    }
                } else if (YxbConstants.ARTICLE_TYPE_PRODUCT.equals(commentType)) {
                    productAnalysis.setProductId(beCommentedId1);
                    ProductAnalysis productAnalysis1 = analysisMapper.selectOne(productAnalysis);

                    productRepertory.setProductId(beCommentedId1);
                    ProductRepertory productRepertory1 = productRepertoryMapper.selectOne(productRepertory);

                    String productDesc = productRepertory1.getProductDesc();
                    String title = productAnalysis1.getTitle();
                    String productId = productAnalysis1.getProductId();
                    String analysisId = productAnalysis1.getAnalysisId();
                    String img = productAnalysis1.getImg();
                    commnetList.get(i).setTitle(title);
                    commnetList.get(i).setProductId(productId);
                    commnetList.get(i).setAnalysisId(analysisId);
                    if (StringUtil.isEmpty(img)) {
                        commnetList.get(i).setImg(img);
                    } else {
                        commnetList.get(i).setImg(systemImageUrl + img);
                    }
                    commnetList.get(i).setExt4(productDesc);

                    //获取创建人头像
                    memberInfo.setMemberId(memberId);
                    MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
                    if (memberInfo1 != null && !StringUtil.isEmpty(memberInfo1.getNickname())) {
                        commnetList.get(i).setNickName(memberInfo1.getNickname());
                    }
                    if (memberInfo1 != null && !StringUtil.isEmpty(memberInfo1.getHeadimg())) {
                        commnetList.get(i).setHeadImg(systemImageUrl + memberInfo1.getHeadimg());
                        commnetList.get(i).setNickName(memberInfo1.getNickname());
                    } else {
                        StaffInfo staffInfo = new StaffInfo();
                        staffInfo.setStaffId(memberId);
                        StaffInfo staffInfoHeadImg = staffInfoMapper.selectOne(staffInfo);
                        if (!StringUtil.isEmpty(staffInfoHeadImg.getHeadImg())) {
                            commnetList.get(i).setHeadImg(systemImageUrl + staffInfoHeadImg.getHeadImg());
                            commnetList.get(i).setNickName(staffInfoHeadImg.getNickName());
                        } else {
                            commnetList.get(i).setHeadImg(staffInfoHeadImg.getHeadImg());
                            commnetList.get(i).setNickName(staffInfoHeadImg.getStaffName());
                        }
                    }
                }

                Integer countComment = likeMapper.selectCount(new EntityWrapper<Like>().eq("be_liked_id", commentId).eq("validity", 10000001));
                commnetList.get(i).setLikeNum(countComment);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("comment", commnetList);
            map.put("comCount", commentCount);
            return new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
        }
        Page<InsertComment> page = new Page<>(limit, offset);
        Page<InsertComment> page1 = selectPage(page, new EntityWrapper<InsertComment>().eq("be_commented_id", beCommentedId).eq("validity", 10000001).orderBy("comment_time desc"));
        List<InsertComment> commnetList = page1.getRecords();
        Integer commentCount = commentMapper.selectCount(new EntityWrapper<InsertComment>().eq("be_commented_id", beCommentedId).eq("validity", 10000001));
        for (int i = 0; i < commnetList.size(); i++) {
            String commentType = commnetList.get(i).getCommentType();
            String commentId = commnetList.get(i).getCommentId();
            commnetList.get(i).setCheckLike(false);
            if (memberId != null && !"".equals(memberId)) {
                Like like = new Like();
                like.setMemberId(memberId);
                like.setBeLikedId(commentId);
                Like like1 = likeMapper.selectOne(like);
                if (like1 != null && !"".equals(like1)) {
                    commnetList.get(i).setCheckLike(true);
                }
            }
            if (YxbConstants.ARTICLE_TYPE_NEWS.equals(commentType)) {
                newss.setNewsId(beCommentedId);
                Newss newss1 = newssMapper.selectOne(newss);
                String title = newss1.getTitle();
                String ext4 = newss1.getExt4();
                String img = newss1.getImg();
                String newsId = newss1.getNewsId();
                commnetList.get(i).setTitle(title);
                commnetList.get(i).setExt4(ext4);
                commnetList.get(i).setNewsId(newsId);
                if (StringUtil.isEmpty(img)) {
                    commnetList.get(i).setImg(img);
                } else {
                    commnetList.get(i).setImg(systemImageUrl + img);
                }
                //获取创建人头像
                String memberId1 = commnetList.get(i).getMemberId();
                List<MemberInfo> memberInfos = memberInfoMapper.selectList(new EntityWrapper<MemberInfo>().eq("member_id", memberId1).eq("validity", 10000001));
                if (memberInfos.size() > 0) {
                    for (int s = 0; s < memberInfos.size(); s++) {
                        String nickname = memberInfos.get(s).getNickname();
                        String headimg = memberInfos.get(s).getHeadimg();
                        if (!StringUtil.isEmpty(nickname)) {
                            commnetList.get(i).setNickName(nickname);
                        }
                        if (!StringUtil.isEmpty(headimg)) {
                            commnetList.get(i).setHeadImg(systemImageUrl + headimg);
                        }
                    }
                }else {
                    StaffInfo staffInfo = new StaffInfo();
                    staffInfo.setStaffId(memberId1);
                    StaffInfo staffInfoHeadImg = staffInfoMapper.selectOne(staffInfo);
                    if (!StringUtil.isEmpty(staffInfoHeadImg.getHeadImg())) {
                        commnetList.get(i).setNickName(staffInfoHeadImg.getNickName());
                        commnetList.get(i).setHeadImg(systemImageUrl + staffInfoHeadImg.getHeadImg());
                    } else {
                        commnetList.get(i).setHeadImg(staffInfoHeadImg.getHeadImg());
                        commnetList.get(i).setNickName(staffInfoHeadImg.getStaffName());
                    }
                }


            } else if (YxbConstants.ARTICLE_TYPE_PRODUCT.equals(commentType)) {
                productAnalysis.setProductId(beCommentedId);
                ProductAnalysis productAnalysis1 = analysisMapper.selectOne(productAnalysis);

                productRepertory.setProductId(beCommentedId);
                ProductRepertory productRepertory1 = productRepertoryMapper.selectOne(productRepertory);

                String productDesc = productRepertory1.getProductDesc();
                String title = productAnalysis1.getTitle();
                String productId = productAnalysis1.getProductId();
                String analysisId = productAnalysis1.getAnalysisId();
                String img = productAnalysis1.getImg();
                commnetList.get(i).setTitle(title);
                commnetList.get(i).setProductId(productId);
                commnetList.get(i).setAnalysisId(analysisId);
                if (StringUtil.isEmpty(img)) {
                    commnetList.get(i).setImg(img);
                } else {
                    commnetList.get(i).setImg(systemImageUrl + img);
                }
                commnetList.get(i).setExt4(productDesc);

                //获取创建人头像
                String memberId1 = commnetList.get(i).getMemberId();
                List<MemberInfo> memberInfos = memberInfoMapper.selectList(new EntityWrapper<MemberInfo>().eq("member_id", memberId1).eq("validity", 10000001));
                for (int s = 0; s < memberInfos.size(); s++) {
                    String nickname = memberInfos.get(s).getNickname();
                    String headimg = memberInfos.get(s).getHeadimg();
                    if (StringUtil.isEmpty(nickname)){
                        commnetList.get(i).setNickName(nickname);
                    }else {
                        commnetList.get(i).setNickName(nickname);
                    }
                    if (!StringUtil.isEmpty(headimg)) {
                        commnetList.get(i).setHeadImg(systemImageUrl + headimg);
                    } else {
                        StaffInfo staffInfo = new StaffInfo();
                        staffInfo.setStaffId(memberId1);
                        StaffInfo staffInfoHeadImg = staffInfoMapper.selectOne(staffInfo);
                        if (!StringUtil.isEmpty(staffInfoHeadImg.getHeadImg())) {
                            commnetList.get(i).setNickName(staffInfoHeadImg.getNickName());
                            commnetList.get(i).setHeadImg(systemImageUrl + staffInfoHeadImg.getHeadImg());
                        } else {
                            commnetList.get(i).setHeadImg(staffInfoHeadImg.getHeadImg());
                            commnetList.get(i).setNickName(staffInfoHeadImg.getStaffName());
                        }
                    }
                }
            }
            Integer countComment = likeMapper.selectCount(new EntityWrapper<Like>().eq("be_liked_id", commentId).eq("validity", 10000001));
            commnetList.get(i).setLikeNum(countComment);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("comment", commnetList);
        map.put("comCount", commentCount);
        return new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
    }
}
