package com.yxbkj.yxb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.CalculatePremiumParam;
import com.yxbkj.yxb.entity.vo.OrderParam;
import com.yxbkj.yxb.entity.vo.ZnProductParam;
import com.yxbkj.yxb.entity.vo.ZrProductParam;
import com.yxbkj.yxb.feign.ServerOrderFeignClient;
import com.yxbkj.yxb.feign.ServerProductFeignClient;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 订单信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-08
 */
@Api(value = "OrderController",description = "订单信息接口")
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private ServerOrderFeignClient serverOrderFeignClient;

    /**
     * 作者: 李明
     * 描述: 获取易安保险支付信息
     * 备注:
     * @param jsonStr
     * @return
     */
    @ApiOperation(value = "获取易安保险支付信息",notes = "获取易安保险支付信息")
    @GetMapping("/yiAnOrderPay")
    @ResponseBody
    public Result<JSONObject> yiAnOrderPay(@ApiParam(value = "JSON串",required = true)@RequestParam(value = "jsonStr",defaultValue = "") String  jsonStr){
        return serverOrderFeignClient.yiAnOrderPay(jsonStr);
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
    @ResponseBody
    public Result<JSONObject> yiAnCreateOrder(@ApiParam(value = "JSON串",required = true)@RequestParam(value = "jsonStr",defaultValue = "") String  jsonStr){
        return serverOrderFeignClient.yiAnCreateOrder(jsonStr);
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
    @ResponseBody
    public Result<Map<String, Object>> createOrder(@RequestBody OrderParam orderParam){
        return serverOrderFeignClient.createOrder(orderParam);
    }

    /**
     * 作者: 李明
     * 描述: 保费试算
     * 备注:
     * @param calculatePremiumParam
     * @return
     */
    @ApiOperation(value = "保费试算",notes = "保费试算")
    @AccessToken
    @PostMapping("/calculatePremium")
    @ResponseBody
    Result<Map<String, Object>> calculatePremium(@RequestBody CalculatePremiumParam calculatePremiumParam){
        return serverOrderFeignClient.calculatePremium(calculatePremiumParam);
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
    @ResponseBody
    public Result<Map<String, Object>> orderPay(@ApiParam(value = "订单ID",required = true)@RequestParam(value = "orderId") String  orderId){
        if(StringUtil.isEmpty(orderId)){
            return  new Result<>(Code.FAIL,"订单ID不能为空!",null,Code.IS_ALERT_YES);
        }
        return serverOrderFeignClient.orderPay(orderId);
    }

    /**
     * 作者: 李明
     * 描述: 中瑞产品跳转
     * 备注:
     * @param zrProductParam
     * @return
     */
    @ApiOperation(value = "中瑞产品跳转",notes = "中瑞产品跳转")
    @AccessToken
    @GetMapping("/zrProductRediect")
    public Object zrProductRediect(ZrProductParam zrProductParam, HttpServletResponse response){
        Result<Map<String, Object>> result = serverOrderFeignClient.zrProductRediect(zrProductParam);
        Map<String, Object> data = result.getData();
        String redirectUrl = (String) data.get("redirectUrl");
        if(Code.FAIL==result.getCode()){
            try {
                WebUtils.outPrint(response, JSON.toJSONString(result));
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        return "redirect:"+redirectUrl;
    }


    /**
     * 作者: 李明
     * 描述: 纵安产品跳转
     * 备注:
     * @param znProductParam
     * @return
     */
    @ApiOperation(value = "纵安产品跳转",notes = "中瑞产品跳转")
    @AccessToken
    @GetMapping("/znProductRediect")
    public Object znProductRediect(ZnProductParam znProductParam, HttpServletResponse response){
        Result<Map<String, Object>> result = serverOrderFeignClient.znProductRediect(znProductParam);
        Map<String, Object> data = result.getData();
        String redirectUrl = (String) data.get("redirectUrl");
        if(Code.FAIL==result.getCode()){
            try {
                WebUtils.outPrint(response, JSON.toJSONString(result));
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        return "redirect:"+redirectUrl;
    }


    /**
     * 作者: 唐漆
     * 描述: 是否结算
     * 备注:
     * @return
     */
    @ApiOperation(value = "是否结算",notes = "是否结算")
    @GetMapping("/commission")
    @ResponseBody
    @AccessToken
    public Result<Map<String, Object>> commission(@ApiParam(value = "是否结算")@RequestParam(value = "status",required = false) String status,
                                                  @ApiParam(value = "排序")@RequestParam(value = "sort",required = false) String sort,
                                                  @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                                  @ApiParam(value = "limit")@RequestParam(value = "limit")Integer limit,
                                                  @ApiParam(value = "offset")@RequestParam(value = "offset")Integer offset){
        return serverOrderFeignClient.commissionAll(status, sort, token, limit, offset);
    }
    @ApiOperation(value = "加油充值回调--> 易宝",notes = "加油充值回调--> 易宝")
    @PostMapping("/rechargeNotifyForYiBao")
    public Result<Map<String, Object>> rechargeNotifyForYiBao(@ApiParam(value = "结果串",required = true)@RequestParam(value="result",defaultValue = "")String result){
        return serverOrderFeignClient.rechargeNotifyForYiBao(result);
    }
    @PostMapping("/rechargeNotifyForWxH5Recharge")
    Result<Map<String, Object>> rechargeNotifyForWxH5Recharge(@ApiParam(value = "订单ID",required = true)@RequestParam(value="orderId",defaultValue = "")String orderId) {
        return serverOrderFeignClient.rechargeNotifyForWxH5Recharge(orderId);
    }

}
