package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.util.JiaYouCard.JiaYouCard;
import com.yxbkj.yxb.util.MD5Util;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zy
 * @desc
 * @since
 */
@RestController
@RequestMapping("/reNotifyController")
public class ReNotifyController {
    /**
     * 回调方法
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "回调方法",notes = "回调方法")
    @RequestMapping(value = "/callerBack", method = RequestMethod.POST)
    public String callerBack(@RequestParam("sporder_id") String sporder_id, @RequestParam("orderid") String orderid,
                           @RequestParam("sta") String sta, @RequestParam("sign") String sign) {
        String local_sign = MD5Util.strToMD5(JiaYouCard.APPKEY + sporder_id + orderid);//本地sign校验值
        if (sign == local_sign) {
            if (sta == "1") {
                //充值成功，根据自身业务逻辑进行后续处理
                System.out.println("成功");
                return "成功";
            } else if (sta == "9") {
                //充值失败,根据自身业务逻辑进行后续处理
                System.out.println("00000000000000");
                return null;
            }
        }
        return "sign校验值错误";
    }
}
