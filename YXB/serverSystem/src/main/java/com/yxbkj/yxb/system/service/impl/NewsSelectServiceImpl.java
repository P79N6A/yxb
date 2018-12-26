package com.yxbkj.yxb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.*;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.system.CodeInfo;
import com.yxbkj.yxb.system.mapper.*;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.NewsSelectService;
import com.yxbkj.yxb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewsSelectServiceImpl extends ServiceImpl<NewsSelectMapper, Newss> implements NewsSelectService {
    @Autowired
    private NewsSelectMapper newsSelectMapper;
    @Autowired
    private QuestionSelectMapper questionSelectMapper;
    @Autowired
    private NewsSortMapper newsSortMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private CodeInfoMapper codeInfoMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private StaffInfoMapper staffInfoMapper;

    public List<Newss> selectContent (String content){
        List<Newss> content1 = newsSelectMapper.selectContent(content);
        return content1;
    }
    public List<Questions> selectQuestion (String title){
        List<Questions> content2 = questionSelectMapper.selectQuestion(title);
        return content2;
    }

    @Override
    public Result<List<Map<String, Object>>> selectNews(String title) {
        String systemImageUrl = configService.getConfigValue("systemImageUrl");
        CodeInfo codeInfo = new CodeInfo();
        List<Questions> questions = selectQuestion(title);
        List<Map<String, Object>> listMap = new ArrayList<>();
        List<Newss> list = newsSelectMapper.selectNews(title);
        Set<String> set = new HashSet<>();
        StaffInfo staffInfo = new StaffInfo();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap();
            String newsId = list.get(i).getNewsId();
            Integer likeNum = likeMapper.selectCount(new EntityWrapper<Like>().eq("be_liked_id", newsId).eq("validity", 10000001));
            list.get(i).setPraise(likeNum);
            if (StringUtil.isEmpty(list.get(i).getImg())){
                list.get(i).setHeadimg(systemImageUrl + list.get(i).getHeadimg());
            }
            if (StringUtil.isEmpty(list.get(i).getHeadimg())) {
                list.get(i).setImg(systemImageUrl + list.get(i).getImg());
            }
            if (list.size() > 0){
                String creatorId = list.get(i).getCreatorId();
                staffInfo.setStaffId(creatorId);
                StaffInfo staffInfo1 = staffInfoMapper.selectOne(staffInfo);
                if (staffInfo1 != null){
                    String nickName = staffInfo1.getNickName();
                    if (!StringUtil.isEmpty(nickName)){
                        list.get(i).setCreator(nickName);
                    }else {
                        String staffName = staffInfo1.getStaffName();
                        list.get(i).setCreator(staffName);
                    }
                }
            }
            List<NewsSort> newsSorts = newsSortMapper.selectList(new EntityWrapper<NewsSort>().eq("news_id", newsId).eq("validity", 10000001).orderBy("sort desc"));

            for (int s = 0; s < newsSorts.size(); s++) {
                String columnType = newsSorts.get(s).getColumnType();
                codeInfo.setCodeValue(columnType);
                CodeInfo codeInfo1 = codeInfoMapper.selectOne(codeInfo);
                List<Newss> contenList = new ArrayList();
                contenList.add(list.get(i));
                Map<String, Object> classMap = new HashMap();
                classMap.put("title",codeInfo1.getCodeName());
                classMap.put("contentList",contenList);
                listMap.add(classMap);
                set.add(codeInfo1.getCodeName());
            }
        }
        List<Map<String, Object>> resListMap = new ArrayList<>();
        for (String setStr : set) {
            Map<String, Object> resClassMap = new HashMap();
            resClassMap.put("title",setStr);
            List resContenList = new ArrayList();
            for (int k = 0; k < listMap.size(); k++) {
                if (setStr.equals(listMap.get(k).get("title"))) {
                    resContenList.addAll((List<Newss>)listMap.get(k).get("contentList"));
                }
            }
            resClassMap.put("contentList",resContenList);
            resListMap.add(resClassMap);
        }
        if (questions.size() > 0){
            for (int i = 0; i < questions.size(); i++) {
                String questionId = questions.get(i).getQuestionId();
                Integer total = answerMapper.selectCount(new EntityWrapper<Answer>().eq("question_id", questionId));
                questions.get(i).setTotal(total);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("title", "问答");
            map.put("contentList", questions);
            resListMap.add(map);
        }

        return new Result<>(Code.SUCCESS, "查询成功!", resListMap, Code.IS_ALERT_NO);
    }

}
