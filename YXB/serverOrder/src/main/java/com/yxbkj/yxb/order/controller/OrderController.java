package com.yxbkj.yxb.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Constants;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.vo.*;
import com.yxbkj.yxb.order.service.CommissionService;
import com.yxbkj.yxb.order.service.OrderService;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.ValidateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 订单信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-15
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommissionService commissionService;



    /**
     * 作者: 李明
     * 描述: 安心订单回调
     * 备注:
     * @param str
     * @return
     */
    @ApiOperation(value = "安心订单回调",notes = "安心订单回调")
    @PostMapping("/axOrderCallBack")
    public Result<Boolean> axOrderCallBack(@ApiParam(value = "密文",required = true)@RequestParam(value = "str",defaultValue = "") String  str){
        return orderService.axOrderCallBack(str);
    }

    /**
     * 作者: 李明
     * 描述: 安心订单回调
     * 备注:
     * @param str
     * @return
     */
    @ApiOperation(value = "安心保险保单回调",notes = "安心保险保单回调")
    @PostMapping("/axPolicyCallBack")
    public Result<Boolean> axPolicyCallBack(@ApiParam(value = "密文",required = true)@RequestParam(value = "str",defaultValue = "") String  str){
        return orderService.axPolicyCallBack(str);
    }

    /**
     * 作者: 李明
     * 描述: 易安保险回调
     * 备注:
     * @param jsonStr
     * @return
     */
    @ApiOperation(value = "易安保险回调",notes = "易安保险回调")
    @PostMapping("/yiAnNotify")
    public Result<JSONObject> yiAnNotify(@ApiParam(value = "JSON串",required = true)@RequestParam(value = "jsonStr",defaultValue = "") String  jsonStr){
        return orderService.yiAnNotify(jsonStr);
    }

    /**
     * 作者: 李明
     * 描述: 易安保险下单
     * 备注:
     * @param jsonStr
     * @return
     */
    @ApiOperation(value = "易安保险下单",notes = "易安保险下单")
    @PostMapping("/yiAnCreateOrder")
    public Result<JSONObject> yiAnCreateOrder(@ApiParam(value = "JSON串",required = true)@RequestParam(value = "jsonStr",defaultValue = "") String  jsonStr){
        return orderService.yiAnCreateOrder(jsonStr);
    }

    /**
     * 作者: 李明
     * 描述: 获取易安保险支付信息
     * 备注:
     * @param jsonStr
     * @return
     */
    @ApiOperation(value = "获取易安保险支付信息",notes = "获取易安保险支付信息")
    @GetMapping("/yiAnOrderPay")
    public Result<JSONObject> yiAnOrderPay(@ApiParam(value = "JSON串",required = true)@RequestParam(value = "jsonStr",defaultValue = "") String  jsonStr){
        return orderService.yiAnOrderPay(jsonStr);
    }

    /**
     * 作者: 李明
     * 描述: 保险下单
     * 备注:
     * @param orderParam
     * @return
     */
    @ApiOperation(value = "保险下单",notes = "保险下单")
    @PostMapping("/createOrder")
    public Result<Map<String, Object>> createOrder(@RequestBody OrderParam orderParam){
        return orderService.createOrder(orderParam);
    }

    /**
     * 作者: 李明
     * 描述: 保费试算
     * 备注:
     * @param calculatePremiumParam
     * @return
     */
    @ApiOperation(value = "保费试算",notes = "保费试算")
    @PostMapping("/calculatePremium")
    public Result<Map<String, Object>> calculatePremium(@RequestBody CalculatePremiumParam calculatePremiumParam){
        return orderService.calculatePremium(calculatePremiumParam);
    }
    /**
     * 作者: 李明
     * 描述: 订单支付
     * 备注:
     * @param orderId
     * @return
     */
    @ApiOperation(value = "订单支付",notes = "订单支付")
    @PostMapping("/orderPay")
    public Result<Map<String, Object>> orderPay(@ApiParam(value = "订单ID",required = true)@RequestParam(value = "orderId") String  orderId){
        return orderService.orderPay(orderId);
    }

    /**
     * 作者: 李明
     * 描述: 中瑞产品跳转
     * 备注:
     * @param zrProductParam
     * @return
     */
    @ApiOperation(value = "中瑞产品跳转",notes = "中瑞产品跳转")
    @PostMapping("/zrProductRediect")
    public Result<Map<String, Object>> zrProductRediect(@RequestBody ZrProductParam zrProductParam){
        return orderService.zrProductRediect(zrProductParam);
    }

    /**
     * 作者: 李明
     * 描述: 纵安产品跳转
     * 备注:
     * @param znProductParam
     * @return
     */
    @ApiOperation(value = "纵安产品跳转",notes = "纵安产品跳转")
    @PostMapping("/znProductRediect")
    public Result<Map<String, Object>> znProductRediect(@RequestBody ZnProductParam znProductParam){
        return orderService.znProductRediect(znProductParam);
    }

    /**
     * 作者: 李明
     * 描述: 纵安回调业务处理
     * 备注:
     * @param result
     * @return
     */
    @ApiOperation(value = "纵安回调业务处理",notes = "纵安回调业务处理")
    @PostMapping("/znProductNotify")
    public Result<Map<String, Object>> znProductNotify(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result){
        return orderService.znProductNotify(result);
    }

    /**
     * 作者: 李明
     * 描述: 中瑞回调处理
     * 备注:
     * @return
     */
    @ApiOperation(value = "中瑞回调处理",notes = "中瑞回调处理")
    @PostMapping("/zrProductNotify")
    public Result<Map<String, Object>> zrProductNotify(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result){
        return orderService.zrProductNotify(result);
    }

    /**
     * 作者: 李明
     * 描述: E生宝回调处理
     * 备注:
     * @return
     */
    @ApiOperation(value = "E生宝回调处理",notes = "E生宝回调处理")
    @PostMapping("/acceptOrder")
    public Result<Map<String, Object>> acceptOrder(@RequestBody AcceptParam acceptParam){
        return orderService.acceptOrder(acceptParam);
    }

    /**
     * 作者: 李明
     * 描述: 会员升级回调--> 易宝
     * 备注:
     * @return
     */
    @ApiOperation(value = "会员升级回调--> 易宝",notes = "会员升级回调--> 易宝")
    @PostMapping("/buyMemberNotifyForYiBao")
    public Result<Map<String, Object>> buyMemberNotifyForYiBao(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result){
        return orderService.buyMemberNotifyForYiBao(result);
    }

    /**
     * 作者: 李明
     * 描述: 会员升级回调--> 微信H5
     * 备注:
     * @return
     */
    @ApiOperation(value = "会员升级回调--> 微信H5",notes = "会员升级回调--> 微信H5")
    @PostMapping("/buyMemberNotifyForWxH5")
    public Result<Map<String, Object>> buyMemberNotifyForWxH5(@ApiParam(value = "订单ID",required = true)@RequestParam(value="orderId",defaultValue = "")String orderId){
        return orderService.buyMemberNotifyForWxH5(orderId);
    }
    /**
     * 作者: zy
     * 描述: 加油卡回调--> 微信H5
     * 备注:
     * @return
     */
    @ApiOperation(value = "加油卡回调--> 微信H5",notes = "加油卡回调--> 微信H5")
    @PostMapping("/rechargeNotifyForWxH5Recharge")
    public Result<Map<String, Object>> rechargeNotifyForWxH5Recharge(@ApiParam(value = "订单ID",required = true)@RequestParam(value="orderId",defaultValue = "")String orderId){
        return orderService.rechargeNotifyForWxH5Recharge(orderId);
    }

    /**
     * 作者: 李明
     * 描述: 理财产品 借款处理
     * 备注:
     * @return
     */
    @ApiOperation(value = "理财产品 借款处理",notes = "理财产品 借款处理")
    @PostMapping("/handBorrowProduct")
    public Result<Map<String, Object>> handBorrowProduct(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result){
        return orderService.handBorrowProduct(result);
    }

    /**
     * 作者: 李明
     * 描述: 理财产品 创建借款产品
     * 备注:
     * @return
     */
    @ApiOperation(value = "理财产品 创建借款产品",notes = "理财产品 创建借款产品")
    @PostMapping("/createBorrowProduct")
    public Result<Map<String, Object>> createBorrowProduct(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result){
        return orderService.createBorrowProduct(result);
    }


    /**
     * 作者: 唐漆
     * 描述: 是否结算
     * 备注:
     * @return
     */
    @ApiOperation(value = "是否结算",notes = "是否结算")
    @GetMapping("/commission")
    @AccessToken
    public Result<Map<String, Object>> commission(String status, String sort, String token, Integer limit, Integer offset){
        return commissionService.commissionAll(status, sort, token, limit, offset);
    }
    @ApiOperation(value = "加油充值回调--> 易宝",notes = "加油充值回调--> 易宝")
    @PostMapping("/rechargeNotifyForYiBao")
    public Result<Map<String, Object>> rechargeNotifyForYiBao(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result){
        return orderService.rechargeNotifyForYiBao(result);
    }
}
