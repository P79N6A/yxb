package com.yxbkj.yxb.feign;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxbkj.yxb.entity.app.Bank;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.FuelRechargeOrder;
import com.yxbkj.yxb.entity.product.FuelDiscount;
import com.yxbkj.yxb.entity.system.*;
import com.yxbkj.yxb.entity.vo.ReceivingInfoVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@FeignClient(value = "systemServer",configuration = FeignAutoConfiguration.class)
public interface ServerSystemFeignClient {
    /**
     * 作者:    唐漆
     * 描述:    获取banner图
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "Sensitive/getSensitive", method = RequestMethod.GET)
    Result<Map<String, Object>> getSensitive(@RequestParam(value="content")String content);
    /**
     * 作者:    唐漆
     * 描述:    获取banner图
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "imgInfo/imgInfo", method = RequestMethod.GET)
    Result<Map<String, Object>> imgInfo(@RequestParam(value="addType")String addType);
    /**
     * 作者:    唐漆
     * 描述:    获取新闻列表
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/news", method = RequestMethod.GET)
    Result<Map<String, Object>> news(@RequestParam(value="limit")Integer limit, @RequestParam(value="offset")Integer offset);
    /**
     * 作者:    唐漆
     * 描述:    获取新闻列表
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/newss", method = RequestMethod.GET)
    Result<Map<String, Object>> newss(@ApiParam(value = "类型")@RequestParam(value = "columnType",required = false)String columnType,
                                      @RequestParam(value="limit")Integer limit, @RequestParam(value="offset")Integer offset);
    /**
     * 作者:    唐漆
     * 描述:    获取阅读量
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/read", method = RequestMethod.GET)
    Result<Map<String, Object>> read(@ApiParam(value = "咨询ID")@RequestParam(value = "newId",required = false)String newId);
    /**
     * 作者:    唐漆
     * 描述:    获取我的收藏
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/coll", method = RequestMethod.GET)
    Result<Map<String, Object>> coll(@ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true,defaultValue = "") String  token,
                                     @RequestParam(value="limit")Integer limit,
                                     @RequestParam(value="offset")Integer offset);
    /**
     * 作者:    唐漆
     * 描述:    获取评论列表
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/comment", method = RequestMethod.GET)
    Result<Map<String, Object>> comment(@ApiParam(value = "被评论ID")@RequestParam(value = "beCommentedId",required = true, defaultValue = "")String beCommentedId,
                                        @ApiParam(value = "令牌")@RequestParam(value = "token",required = false,defaultValue = "") String token,
                                        @RequestParam(value="limit")Integer limit,
                                        @RequestParam(value="offset")Integer offset);
    /**
     * 作者:    唐漆
     * 描述:    添加评论
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "insert/comment", method = RequestMethod.GET)
    Result<Map<String, Object>> getInsertcomment(@ApiParam(value = "被评论ID")@RequestParam(value = "beCommentedId",required = false)String beCommentedId,
                                                 @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true,defaultValue = "") String  token,
                                                 @ApiParam(value = "评论内容")@RequestParam(value="content")String content,
                                                 @ApiParam(value = "评论类型 news/comment的ID")@RequestParam(value="commentType")String commentType);
    /**
     * 作者:    唐漆
     * 描述:    添加评论
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "insert/comment", method = RequestMethod.POST)
    Result<Map<String, Object>> postInsertcomment(@ApiParam(value = "被评论ID")@RequestParam(value = "beCommentedId",required = false)String beCommentedId,
                                                  @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true,defaultValue = "") String  token,
                                                  @ApiParam(value = "评论内容")@RequestParam(value="content")String content,
                                                  @ApiParam(value = "评论类型 news/comment的ID")@RequestParam(value="commentType")String commentType);
    /**
     * 作者:    唐漆
     * 描述:    添加点赞
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "insert/like", method = RequestMethod.GET)
    Result<Map<String, Object>> insertlike(@ApiParam(value = "被点赞ID")@RequestParam(value = "beLikedId")String beLikedId,
                                           @ApiParam(value = "token")@RequestParam(value = "token",required = true,defaultValue = "") String  token,
                                           @ApiParam(value = "点赞类型")@RequestParam(value="likeType")String likeType);
    /**
     * 作者:    唐漆
     * 描述:    收藏点赞
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "insert/collection", method = RequestMethod.GET)
    Result<Map<String, Object>> insertcollection(@ApiParam(value = "被收藏ID")@RequestParam(value = "beCollectedId")String beCollectedId,
                                                 @ApiParam(value = "令牌")@RequestParam(value = "token",required = true,defaultValue = "") String token,
                                                 @ApiParam(value = "收藏类型")@RequestParam(value="collectionType")String collectionType);
    /**
     * 作者:    唐漆
     * 描述:    问题标题添加
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "insert/question", method = RequestMethod.GET)
    Result<Map<String, Object>> insertquestion(@ApiParam(value = "问题标题")@RequestParam(value = "questionContent")String questionContent,
                                               @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true,defaultValue = "")String token);
    /**
     * 作者:    唐漆
     * 描述:    问题回答添加
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "insert/answer", method = RequestMethod.GET)
    Result<Map<String, Object>> getInsertAnswer(@ApiParam(value = "回答问题ID")@RequestParam(value = "questionId")String questionId,
                                                @ApiParam(value = "回答问题内容")@RequestParam(value = "answerContent")String answerContent,
                                                @ApiParam(value = "令牌",required = true)@RequestParam(value = "token")String token
    );
    /**
     * 作者:    唐漆
     * 描述:    问题回答添加
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "insert/answer", method = RequestMethod.POST)
    Result<Map<String, Object>> postInsertAnswer(@ApiParam(value = "回答问题ID")@RequestParam(value = "questionId")String questionId,
                                                 @ApiParam(value = "回答问题内容")@RequestParam(value = "answerContent")String answerContent,
                                                 @ApiParam(value = "令牌",required = true)@RequestParam(value = "token")String token
    );
    /**
     * 作者:    唐漆
     * 描述:    删除点赞
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "delete/like", method = RequestMethod.GET)
    Result<Map<String, Object>> deletelike(@ApiParam(value = "文章ID")@RequestParam(value = "beLikedId")String beLikedId,
                                           @ApiParam(value = "token")@RequestParam(value = "token")String token);
    /**
     * 作者:    唐漆
     * 描述:    删除评论
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "delete/comment", method = RequestMethod.GET)
    Result<Map<String, Object>> deletecomment(@ApiParam(value = "被删除评论ID")@RequestParam(value = "commentId")String commentId);
    /**
     * 作者:    唐漆
     * 描述:    删除收藏
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "delete/collection", method = RequestMethod.GET)
    Result<Map<String, Object>> deletecollection(@ApiParam(value = "文章ID")@RequestParam(value = "beCollectedId")String beCollectedId,
                                                 @ApiParam(value = "token")@RequestParam(value = "token")String token);

    /**
     * 作者:    唐漆
     * 描述:    删除我的提问
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "delete/deleteQuestion", method = RequestMethod.GET)
    Result<Map<String, Object>> deleteQuestion(@ApiParam(value = "提问ID")@RequestParam(value = "questionId")String questionId,
                                               @ApiParam(value = "token")@RequestParam(value = "token")String token);

    /**
     * 作者:    唐漆
     * 描述:    删除我的回答
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "delete/deleteAnswer", method = RequestMethod.GET)
    Result<Map<String, Object>> deleteAnswer(@ApiParam(value = "回答ID")@RequestParam(value = "answerId")String answerId,
                                             @ApiParam(value = "token")@RequestParam(value = "token")String token);

    /**
     * 作者:    唐漆
     * 描述:    获取问答
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "question/page", method = RequestMethod.GET)
    Result<List<Map<String, Object>>> question(@RequestParam(value="limit")Integer limit, @RequestParam(value="offset") Integer offset);

    @RequestMapping(value = "question/myquestion", method = RequestMethod.GET)
    Result<List<Map<String, Object>>> myquestion(@RequestParam(value="token")String token,
                                                 @RequestParam(value="limit")Integer limit,
                                                 @RequestParam(value="offset") Integer offset);

    @RequestMapping(value = "question/questionlist", method = RequestMethod.GET)
    Result<List<Map<String, Object>>> questionList(@RequestParam(value="token")String token,
                                                   @RequestParam(value="limit")Integer limit,
                                                   @RequestParam(value="offset") Integer offset);
    /**
     * 作者:    唐漆
     * 描述:    获取我的回答
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "question/myanswer", method = RequestMethod.GET)
    Result<Map<String, Object>> myanswer(@RequestParam(value="token")String token,
                                         @RequestParam(value="limit")Integer limit,
                                         @RequestParam(value="offset") Integer offset);
    /**
     * 作者:    唐漆
     * 描述:    获取问答详情
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "question/questionDetails", method = RequestMethod.GET)
    Result<List<Map<String, Object>>> questionDetails(@RequestParam(value="questionId")String questionId,
                                                      @RequestParam(value="token")String token,
                                                      @RequestParam(value="limit")Integer limit,
                                                      @RequestParam(value="offset") Integer offset);
    /**
     * 作者:    唐漆
     * 描述:    获取未读回答数量
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "question/getAnswerIsRead", method = RequestMethod.GET)
    Result<Map<String, Object>> getAnswerIsRead(@RequestParam(value="token")String token);
    /**
     * 作者:    唐漆
     * 描述:    获取新闻详情
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/details", method = RequestMethod.GET)
    Result<Map<String, Object>> details(@RequestParam(value="newsId")String newsId);
    /**
     * 作者:    唐漆
     * 描述:    获取新闻详情
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/select", method = RequestMethod.GET)
    Result<List<Map<String, Object>>> selectnews(@RequestParam(value="title")String title);
    /**
     * 作者:    唐漆
     * 描述:    获取新闻详情
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/notlike", method = RequestMethod.GET)
    Result<Boolean> notlike(@RequestParam(value="newsId")String newsId,
                            @RequestParam(value="token")String token);
    /**
     * 作者:    唐漆
     * 描述:    获取新闻详情
     * 备注:
     * @param
     * @return
     */
    @RequestMapping(value = "news/notcoll", method = RequestMethod.GET)
    Result<Boolean> notcoll(@RequestParam(value="newsId")String newsId,
                            @RequestParam(value="token")String token);


    /**
     * 作者: 李明
     * 描述: 获取所有码表类型信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取所有码表类型信息",notes = "获取所有码表类型信息")
    @GetMapping("config/getAllCodeType")
    Result<List<CodeType>> getAllCodeType();


    /**
     * 作者: 李明
     * 描述: 根据码表类型 获取码表值
     * 备注:
     * @param typeCode
     * @return
     */
    @GetMapping("config/getCodeByType")
    Result<Map<String,Object>> getCodeByType(
            @ApiParam(value = "类型  多个类型用逗号分割",required = true)@RequestParam(value = "typeCode",required = true,defaultValue = "") String  typeCode
    );

    /**
     * 作者: 李明
     * 描述: 根据码表值获取码表对象
     * 备注:
     * @param codeValue
     * @return
     */
    @GetMapping("config/getNameByValue")
    Result<CodeInfo> getNameByValue(
            @ApiParam(value = "类型",required = true)@RequestParam(value = "codeValue",required = true,defaultValue = "") String  codeValue
    );

    /**
     * 作者: 李明
     * 描述: 获取所有银行信息
     * 备注:
     * @return
     */
    @GetMapping("bank/getAllBank")
    Result<List<Bank>> getAllBank();


    /**
     * 作者: 李明
     * 描述: 获取分享信息
     * 备注:
     * @return
     */
    @GetMapping("system/getWxShareinfo")
    Result<Map<String, String>> getWxShareinfo(
            @ApiParam(value = "地址",required = true)@RequestParam(value = "url",required = false,defaultValue = "") String  url
    );


    /**
     * 作者: 李明
     * 描述: 获取APP版本信息
     * 备注:
     * @return
     */
    @GetMapping("system/getAppVersion")
    Result<JSONObject> getAppVersion();


    /**
     * 作者: 李明
     * 描述: 支付结果通知
     * 备注:
     * @return
     */
    @PostMapping(value = "system/policyNotify")
    Result<Boolean> policyNotify(@RequestParam(value = "req",required = false,defaultValue = "") String req);



    /**
     * 作者: 李明
     * 描述: 获取奖品列表
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取奖品列表",notes = "获取奖品列表")
    @GetMapping("activity/getPrizeList")
    Result<List<Prize>> getPrizeList(
            @ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
    );
    /**
     * 作者: 李明
     * 描述: 执行抽奖
     * 备注:
     * @return
     */
    @PostMapping("activity/executeDraw")
    Result<Map<String, Object>> executeDraw(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true,defaultValue = "") String  token
           ,@ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
            ,@ApiParam(value = "Ip",required = true)@RequestParam(value = "Ip",required = true,defaultValue = "") String  Ip
    );

    /**
     * 作者: 李明
     * 描述: 添加收货地址
     * 备注:
     * @return
     */
    @PostMapping("activity/addReceivingInfo")
    Result<Map<String, Object>> addReceivingInfo(
            @RequestBody ReceivingInfoVo entity
    );


    /**
     * 作者: 李明
     * 描述: 分页获取最新中奖信息
     * 备注:
     * @param token
     * @param activityNo
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("activity/getPrizeInfoNews")
    Result<Page<PrizeRecord>> getPrizeInfoNews(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = false,defaultValue = "") String  token,
            @ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
            ,@ApiParam(value = "页码",required = false)@RequestParam(value="offset")Integer offset
            ,@ApiParam(value = "条数",required = false)@RequestParam(value="limit")Integer limit
    );

    /**
     * 作者: 李明
     * 描述: 获取今日抽奖次数
     * 备注:
     * @return
     */
    @GetMapping("activity/getTodayDrawCount")
    Result<Integer> getTodayDrawCount(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true,defaultValue = "") String  token
            ,@ApiParam(value = "活动编号",required = true)@RequestParam(value = "activityNo",required = true,defaultValue = "") String  activityNo
    );


    /**
     * 作者: 李明
     * 描述: 获取第三方订单跳转地址
     * 备注:
     * @return
     */
    @GetMapping("system/getOtherReturnUrl")
    Result<String> getOtherReturnUrl(
            @ApiParam(value = "投保单号",required = true)@RequestParam(value = "insur_no",required = false,defaultValue = "") String  insur_no
    );

    /**
     * 作者: zy
     * 描述: 获取活动信息列表
     * 备注:
     * @return
     */
    @GetMapping("activityInfo/getActivityInfoList")
    Result<List<ActivityInfo>> getActivityInfoList();
    /**
     * 添加活动
     * @author zy
     * @desc
     * @since
     */
    @PostMapping(value = "activityInfo/addActivityInfo")
    Result<Map<String, Object>> addActivityInfo(@ApiParam(value = "活动名称", required = true) @RequestParam(value = "activityName", required = true) String activityName
            , @ApiParam(value = "活动简介", required = true) @RequestParam(value = "activityDesc", required = true) String activityDesc
            , @ApiParam(value = "活动金额", required = true) @RequestParam(value = "activityMoney", required = true) String activityMoney
            , @ApiParam(value = "创建人", required = true) @RequestParam(value = "creator", required = true) String creator
            , @ApiParam(value = "活动开始时间", required = true) @RequestParam(value = "activityStartTime", required = true) Date activityStartTime
            , @ApiParam(value = "活动结束时间", required = true) @RequestParam(value = "activityEndTime", required = true) Date activityEndTime
    );
    /**
     * 删除活动
     * @author zy
     * @desc
     * @since
     */
    @PostMapping(value = "activityInfo/deleteByActivityNo")
    Result<Map<String, Object>> deleteByActivityNo(@ApiParam(value = "删除活动", required = true) @RequestParam(value = "activityNo", required = true) String activityNo);
    /**
     * 活动是否在进行中
     * @author zy
     * @desc
     * @since
     */
    @PostMapping(value = "activityInfo/isStart")
    Result<Boolean> isStart(@ApiParam(value = "活动是否正在进行", required = true) @RequestParam(value = "activityNo", required = true) String activityNo);
    /**
     * 获取需要的金额
     * @author zy
     * @desc
     * @since
     */
    @PostMapping(value = "reChargeController/reChargeMoney")
    Result<Map<String,Object>> reChargeMoney(@ApiParam(value = "电话号码", required = true) @RequestParam(value = "phone", required = true) String phone,
                                             @ApiParam(value = "选择的金额", required = true) @RequestParam(value = "cardNum", required = true) Integer cardNum);
    /**
     * 手机直充
     * @author zy
     * @desc
     * @since
     */
    @PostMapping(value = "reOnlineOrderController/onlineOrder")
    Result<Map<String,Object>> OnlineOrder(
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "phone", required = true) String phone,
            @ApiParam(value = "充值金额", required = true) @RequestParam(value = "cardNum", required = true) int cardNum
    );
    /**
     * 查询订单状态
     * @author zy
     * @desc
     * @since
     */
    @PostMapping("reOrderStaController/orderSta")
    Result<Map<String,Object>> orderSta(
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "phone", required = true) String phone);
    /**
     * 加油卡提交充值接口
     * @author zy
     * @desc
     * @since
     */
    @PostMapping("jiaYouCard/onLineOrder")
    Result<Map<String,Object>> onLineOrder(
            @ApiParam(value = "产品id", required = true) @RequestParam(value = "proid", required = true) int proid,
            @ApiParam(value = "充值数量或金额", required = true) @RequestParam(value = "cardNum", required = true) String cardNum,
            @ApiParam(value = "加油卡卡号", required = true) @RequestParam(value = "game_userid", required = true) String game_userid,
            @ApiParam(value = "持卡人手机号码", required = true) @RequestParam(value = "gasCardTel", required = true) String gasCardTel,
            @ApiParam(value = "持卡人姓名", required = false) @RequestParam(value = "gasCardName", required = false) String gasCardName,
            @ApiParam(value = "加油卡类型", required = false) @RequestParam(value = "chargeType", required = false) int chargeType,
            @ApiParam(value = "订单号", required = true) @RequestParam(value = "orderId", required = true) String orderId

    );
    /**
     * 订单状态
     * @author zy
     * @desc
     * @since
     */
    @PostMapping("jiaYouCard/orderSta")
    public Result<Map<String,Object>> orderStaJiaYou(
            @ApiParam(value = "电话号码", required = true) @RequestParam(value = "phone", required = true) String phone
    );
    /**
     * 订单状态
     * @author zy
     * @desc
     * @since
     */
    @PostMapping("jiaYouCard/orderStaOrderId")
    Result<Map<String, Object>> orderStaOrderId(
            @ApiParam(value = "订单号", required = true) @RequestParam(value = "orderId", required = true) String orderId
    );
    /**
     * 回调
     * @author zy
     * @desc
     * @since
     */
    @PostMapping(value = "jiaYouCard/callerBack")
    public void callerBack(@RequestParam("sporder_id") String sporder_id, @RequestParam("orderid") String orderid,
                           @RequestParam("sta") String sta, @RequestParam("sign") String sign, @RequestParam("err_msg") String err_msg);
    /**
     * 通过日期查询订单
     * @author zy
     * @desc
     * @since
     */
    @PostMapping(value = "jiaYouCard/orderByDate")
    Result<Map<String,Object>> orderByDate(
            @ApiParam(value = "当前页", required = false) @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "每页条数", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(value = "开始时间", required = false) @RequestParam(value = "startTime", required = false) @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") String startTime,
            @ApiParam(value = "结束时间", required = false) @RequestParam(value = "endTime", required = false) @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") String endTime
    );
    /**
     * 手机充值回调接口
     * @author zy
     * @desc
     * @since
     */
    @PostMapping(value = "reNotifyController/callerBack")
    String callerBack(@RequestParam("sporder_id") String sporder_id, @RequestParam("orderid") String orderid,
                      @RequestParam("sta") String sta, @RequestParam("sign") String sign);
    /**
     * 查询账户余额
     * @author zy
     * @desc
     * @since
     */
    @GetMapping(value = "jiaYouCardController/yuE")
    Result<Map<String,Object>> yuE();
    /**
     * 绑定加油卡
     * @author zy
     * @desc
     * @since
     */
    @PostMapping("fuelCardController/bindFuelCard")
    Result<Map<String,Object>> bindFuelCard(
            @ApiParam(value = "令牌", required = true) @RequestParam(value = "token", required = true) String token,
            @ApiParam(value = "加油卡号", required = true) @RequestParam(value = "cardNumber", required = true) String cardNumber
    );
    /**
     * 查询加油卡列表
     * @author zy
     * @desc
     * @since
     */
    @GetMapping("fuelCardController/getFuelCardList")
    Result<Map<String,Object>> getFuelCardList(
            @ApiParam(value = "令牌", required = true) @RequestParam(value = "token", required = true) String token
    );
    /**
     * <p>
     * 加油卡解绑
     * </p>
     *
     * @author ZY
     * @since 2018-12-14
     */
    @PostMapping("fuelCardController/unbindCard")
    Result<Map<String,Object>> unbindCard(
            @ApiParam(value = "令牌", required = true) @RequestParam(value = "token", required = true) String token,
            @ApiParam(value = "加油卡号", required = true) @RequestParam(value = "cardNumber", required = true) String cardNumber
    );
    /**
     * <p>
     * 获取折扣列表
     * </p>
     *
     * @author ZY
     * @since 2018-12-14
     */
    @GetMapping("fuelDiscount/getDiscountList")
    Result<List<FuelDiscount>> getDiscountList(
            @ApiParam(value = "充值类型", required = true) @RequestParam(value = "disCountType", required = true) String disCountType
    );
    /**
     * <p>
     * 添加折扣
     * </p>
     *
     * @author ZY
     * @since 2018-12-14
     */
    @PostMapping("fuelDiscount/incrDiscount")
    Result<Map<String,Object>> incrDiscount(
            @ApiParam(value = "折扣类型", required = true) @RequestParam(value = "discountType", required = true) String discountType,
            @ApiParam(value = "到账总月数", required = true) @RequestParam(value = "months", required = true) Integer months,
            @ApiParam(value = "折扣大小", required = true) @RequestParam(value = "discountNum", required = true) Double discountNum,
            @ApiParam(value = "成本比例",required = true)@RequestParam(value = "costRate",required = true)Double costRate,
            @ApiParam(value = "佣金比例",required = true)@RequestParam(value = "commissionRate",required = true)Double commissionRate,
            @ApiParam(value = "犹豫期",required = true)@RequestParam(value = "hesiPeriod",required = true)Integer hesiPeriod
    );
    /**
     * <p>
     * 切换加油卡
     * </p>
     *
     * @author ZY
     * @since 2018-12-17
     */
    @PostMapping("fuelCardController/exChangeCard")
    Result<Map<String,Object>> exChangeCard(
            @ApiParam(value = "令牌", required = true) @RequestParam(value = "token", required = true) String token,
            @ApiParam(value = "加油卡号", required = true) @RequestParam(value = "cardNumber", required = true) String cardNumber
    );
    /**
     * <p>
     * 生成支付订单
     * </p>
     *
     * @author ZY
     * @since 2018-12-17
     */
    @PostMapping("fuelPayOrder/addPayOrder")
    Result<Map<String,Object>> addPayOrder(
            @ApiParam(value = "折扣大小",required = true)@RequestParam(value = "discountNum",required = true)Double discountNum,
            @ApiParam(value = "到账总月数",required = true)@RequestParam(value = "months",required = true)Integer months,
            @ApiParam(value = "支付总金额",required = true)@RequestParam(value = "amount",required = true)Double amount,
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "支付类型",required = true)@RequestParam(value = "payType",required = true)String payType,
            @ApiParam(value = "订单类型",required = true)@RequestParam(value = "orderType",required = true)String orderType,
            @ApiParam(value = "选中加油卡卡号",required = true)@RequestParam(value = "fuelNumber",required = true)String fuelNumber,
            @ApiParam(value = "每月到账金额",required = true)@RequestParam(value = "monthAmount",required = true)Double monthAmount,
            @ApiParam(value = "订单来源(APP/微信)",required = true)@RequestParam(value = "orderSource",required = true)String orderSource
    );
    /**
     * <p>
     * 获取支付订单列表
     * </p>
     *
     * @author ZY
     * @since 2018-12-17
     */
    @PostMapping("fuelPayOrder/payOrderList")
    Result<Page<Map<String,Object>>> payOrderList(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "页面条数",required = true)@RequestParam(value = "limit",required = true)Integer limit,
            @ApiParam(value = "当前页",required = true)@RequestParam(value = "offset",required = true)Integer offset,
            @ApiParam(value = "支付状态",required = true)@RequestParam(value = "payStatus",required = true)String payStatus
    );
    @PostMapping("fuelPayController/payFuel")
    Result<Map<String,Object>> payFuel(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId,
            @ApiParam(value = "source",required = true)@RequestParam(value = "source",required = true)String source
    );
    @PostMapping("fuelPayOrder/updatePayOrderStatus")
    Result<Map<String,Object>> updatePayOrderStatus(
            @ApiParam(value = "订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    );
    /**
     * <p>
     * 生成支付订单明细表
     * </p>
     *
     * @author ZY
     * @since 2018-12-17
     */
    @PostMapping("fuelRechargeOrder/addFuelRechargeOrders")
    Result<Map<String,Object>> addFuelRechargeOrders(
            @ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    );
    @PostMapping("fuelRechargeOrder/getRechargeOrderList")
    Result<Map<String,Object>> getRechargeOrderList(
            @ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    );
    @PostMapping("fuelRechargeOrder/updateStatus")
    Result<Map<String,Object>> updateStatus(@RequestParam(value = "rechargeOrderId",required = true)String rechargeOrderId);
    @PostMapping("fuelRechargeOrder/getOrderCharge")
    Result<Map<String, FuelRechargeOrder>> getOrderCharge(@RequestParam(value = "rechargeOrderId",required = true)String rechargeOrderId);
}
