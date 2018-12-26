package com.yxbkj.yxb.feign;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 描述： 订单信息服务
 * 作者： 李明
 * 备注： 2017/08/15 10:22
 */
@FeignClient(value = "orderServer", configuration = FeignClientsConfiguration.class)
public interface ServerOrderFeignClient {


    /**
     * 作者: 李明
     * 描述: 安心订单回调
     * 备注:
     * @param str
     * @return
     */
    @ApiOperation(value = "安心订单回调",notes = "安心订单回调")
    @PostMapping("order/axOrderCallBack")
    Result<Boolean> axOrderCallBack(@ApiParam(value = "密文",required = true)@RequestParam(value = "str",defaultValue = "") String  str);

    /**
     * 作者: 李明
     * 描述: 安心订单回调
     * 备注:
     * @param str
     * @return
     */
    @ApiOperation(value = "安心保险保单回调",notes = "安心保险保单回调")
    @PostMapping("order/axPolicyCallBack")
    Result<Boolean> axPolicyCallBack(@ApiParam(value = "密文",required = true)@RequestParam(value = "str",defaultValue = "") String  str);



    /**
     * 作者: 李明
     * 描述: 易安保险回调
     * 备注:
     * @param jsonStr
     * @return
     */
    @ApiOperation(value = "易安保险回调",notes = "易安保险回调")
    @PostMapping("order/yiAnNotify")
    Result<JSONObject> yiAnNotify(@ApiParam(value = "JSON串",required = true)@RequestParam(value = "jsonStr",defaultValue = "") String  jsonStr);


    /**
     * 作者: 李明
     * 描述: 获取易安保险支付信息
     * 备注:
     * @param jsonStr
     * @return
     */
    @ApiOperation(value = "获取易安保险支付信息",notes = "获取易安保险支付信息")
    @GetMapping("order/yiAnOrderPay")
    Result<JSONObject> yiAnOrderPay(@ApiParam(value = "JSON串",required = true)@RequestParam(value = "jsonStr",defaultValue = "") String  jsonStr);

    /**
     * 作者: 李明
     * 描述: 易安保险下单
     * 备注:
     * @param jsonStr
     * @return
     */
    @ApiOperation(value = "易安保险下单",notes = "易安保险下单")
    @PostMapping("order/yiAnCreateOrder")
    Result<JSONObject> yiAnCreateOrder(@ApiParam(value = "JSON串",required = true)@RequestParam(value = "jsonStr",defaultValue = "") String  jsonStr);

    /**
     * 作者: 李明
     * 描述: 保险下单
     * 备注:
     * @param orderParam
     * @return
     */
    @PostMapping("order/createOrder")
    Result<Map<String, Object>> createOrder(@RequestBody OrderParam orderParam);


    /**
     * 作者: 李明
     * 描述: 保费试算
     * 备注:
     * @param calculatePremiumParam
     * @return
     */
    @PostMapping("order/calculatePremium")
    Result<Map<String, Object>> calculatePremium(@RequestBody CalculatePremiumParam calculatePremiumParam);


    /**
     * 作者: 李明
     * 描述: 订单支付
     * 备注:
     * @param orderId
     * @return
     */
    @PostMapping("order/orderPay")
    Result<Map<String, Object>> orderPay(@ApiParam(value = "订单ID",required = true)@RequestParam(value = "orderId") String  orderId);

    /**
     * 作者: 唐漆
     * 描述: 车险订单
     * 备注:
     * @return
     */
    @GetMapping("carorder/carorder")
    Result<Map<String, Object>> carOrder(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                         @ApiParam(value = "类型")@RequestParam(value = "orderStatus",required = false)String orderStatus,
                                         @ApiParam(value = "支付状态")@RequestParam(value = "payStatus",required = false)String payStatus,
                                         @RequestParam(value = "limit")Integer limit,
                                         @RequestParam(value = "offset")Integer offset);

    /**
     * 作者: 唐漆
     * 描述: 医疗险订单
     * 备注:
     * @return
     */
    @GetMapping("carorder/yiliao")
    Result<Map<String, Object>> selectMedical(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                              @ApiParam(value = "类型")@RequestParam(value = "orderStatus",required = false)String orderStatus,
                                              @ApiParam(value = "支付状态")@RequestParam(value = "payStatus",required = false)String payStatus,
                                              @RequestParam(value = "limit")Integer limit,
                                              @RequestParam(value = "offset")Integer offset);

    /**
     * 作者: 唐漆
     * 描述: 意外险订单
     * 备注:
     * @return
     */
    @GetMapping("carorder/accident")
    Result<Map<String, Object>> selectAccident(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                               @ApiParam(value = "类型")@RequestParam(value = "orderStatus",required = false)String orderStatus,
                                               @ApiParam(value = "支付状态")@RequestParam(value = "payStatus",required = false)String payStatus,
                                               @RequestParam(value = "limit")Integer limit,
                                               @RequestParam(value = "offset")Integer offset);

    /**
     * 作者: 唐漆
     * 描述: 逻辑删除订单
     * 备注:
     * @return
     */
    @GetMapping("carorder/delect")
    Result<Map<String, Object>> delectOrder(@ApiParam(value = "订单ID",required = true)@RequestParam(value = "orderId") String orderId);

    /**
     * 作者: 李明
     * 描述: 中瑞产品跳转
     * 备注:
     * @param zrProductParam
     * @return
     */
    @PostMapping("order/zrProductRediect")
    Result<Map<String, Object>> zrProductRediect(@RequestBody ZrProductParam zrProductParam);


    /**
     * 作者: 李明
     * 描述: 纵安产品跳转
     * 备注:
     * @param znProductParam
     * @return
     */
    @PostMapping("order/znProductRediect")
    Result<Map<String, Object>> znProductRediect(@RequestBody ZnProductParam znProductParam);


    /**
     * 作者: 李明
     * 描述: E生宝回调处理
     * 备注:
     * @param acceptParam
     * @return
     */
    @PostMapping("order/acceptOrder")
    Result<Map<String, Object>> acceptOrder(@RequestBody AcceptParam acceptParam);


    /**
     * 作者: 李明
     * 描述: 纵安回调业务处理
     * 备注:
     * @param result
     * @return
     */
    @PostMapping("order/znProductNotify")
    Result<Map<String, Object>> znProductNotify(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result);

    /**
     * 作者: 唐漆
     * 描述: 是否结算
     * 备注:
     * @param status
     * @return
     */
    @GetMapping("order/commission")
    Result<Map<String, Object>> commissionAll(@ApiParam(value = "是否结算")@RequestParam(value="status",required = false)String status,
                                              @ApiParam(value = "排序")@RequestParam(value="sort",required = false)String sort,
                                              @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                              @ApiParam(value = "limit")@RequestParam(value="limit")Integer limit,
                                              @ApiParam(value = "offset")@RequestParam(value="offset")Integer offset);

    /**
     * 作者: 李明
     * 描述: 理财产品 借款处理
     * 备注:
     * @return
     */
    @PostMapping("order/handBorrowProduct")
    Result<Map<String, Object>> handBorrowProduct(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result);

    /**
     * 作者: 李明
     * 描述: 理财产品 创建借款产品
     * 备注:
     * @return
     */
    @PostMapping("order/createBorrowProduct")
    Result<Map<String, Object>> createBorrowProduct(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result);


    /**
     * 作者: 李明
     * 描述: 会员升级回调--> 易宝
     * 备注:
     * @return
     */
    @PostMapping("order/buyMemberNotifyForYiBao")
    Result<Map<String, Object>> buyMemberNotifyForYiBao(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result);

    /**
     * 作者: zy
     * 描述: 加油卡充值回调--> 微信H5
     * 备注:
     * @return
     */
    @PostMapping("order/rechargeNotifyForWxH5Recharge")
    Result<Map<String, Object>> rechargeNotifyForWxH5Recharge(@ApiParam(value = "订单ID",required = true)@RequestParam(value="orderId",defaultValue = "")String orderId);
    /**
     * 作者: 李明
     * 描述: 会员升级回调--> 微信H5
     * 备注:
     * @return
     */
    @PostMapping("order/buyMemberNotifyForWxH5")
    Result<Map<String, Object>> buyMemberNotifyForWxH5(@ApiParam(value = "订单ID",required = true)@RequestParam(value="orderId",defaultValue = "")String orderId);
    /**
     * 作者: 李明
     * 描述: 中瑞回调处理
     * 备注:
     * @return
     */
    @PostMapping("order/zrProductNotify")
    Result<Map<String, Object>> zrProductNotify(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result);
    @PostMapping("order/rechargeNotifyForYiBao")
    Result<Map<String, Object>> rechargeNotifyForYiBao(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result);



}
