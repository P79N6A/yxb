package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.AcceptParam;
import com.yxbkj.yxb.feign.ServerOrderFeignClient;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import com.yxbkj.yxb.util.*;
import com.yxbkj.yxb.util.pingan.PingAnUtils;
import com.yxbkj.yxb.util.wxpay.util.PayCommonUtil;
import com.yxbkj.yxb.util.wxpay.util.PayConfigUtil;
import com.yxbkj.yxb.util.wxpay.util.XMLUtil;
import com.yxbkj.yxb.util.yeepay.YeePayUtils;
import com.yxbkj.yxb.util.zhongan.RSAUtils;
import com.zhongan.scorpoin.common.ZhongAnNotifyClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 *  回调通知接口(国寿前台跳转地址) 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-10
 */
@Api(value = "NotifyGsController",description = "回调通知接口(国寿前台跳转地址) 测试环境")
@Controller
@RequestMapping("/")
public class NotifyGsController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    @ApiOperation(value = "国寿前台跳转地址 测试环境",notes = "国寿前台跳转地址 测试环境 测试环境")
    @RequestMapping(value = "/gspayres")
    @ResponseBody
    public Object gspayres(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   国寿前台跳转地址】"+paramsStr);
        String content = streamToString(request);
        logger.info("【易小宝科技API:   国寿前台跳转地址Stream】"+content);
        String req = request.getParameter("req");
        String resp_code = XmlUtils.getRes(req, "resp_code");
        String insur_no = XmlUtils.getRes(req, "insur_no");
        String policy_no = XmlUtils.getRes(req, "policy_no");
        String resp_desc = XmlUtils.getRes(req, "resp_desc");
        logger.info("【易小宝科技API:   resp_code】"+resp_code);
        logger.info("【易小宝科技API:   insur_no】"+insur_no);
        logger.info("【易小宝科技API:   resp_code】"+policy_no);
        logger.info("【易小宝科技API:   resp_desc】"+resp_desc);
        if(!StringUtil.isEmpty(insur_no)){
            Result<String> otherReturnUrl = serverSystemFeignClient.getOtherReturnUrl(insur_no);
            if(!StringUtil.isEmpty(otherReturnUrl.getData())){
                response.sendRedirect(otherReturnUrl.getData()+"?resp_code="+resp_code+"&insur_no="+insur_no+"&policy_no="+policy_no+"&resp_desc="+URLEncoder.encode(resp_desc,"UTF-8"));
            }
        }
        return null;
    }


    public String  streamToString(HttpServletRequest request){
        String strcont = "";
        try{
            ServletInputStream ris = request.getInputStream();
            StringBuilder content = new StringBuilder();
            byte[] b = new byte[1024];
            int lens = -1;
            while ((lens = ris.read(b)) > 0) {
                content.append(new String(b, 0, lens));
            }
            strcont = content.toString();// 内容
            logger.info("解析输入流完毕"+strcont);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("解析输入流正常"+e.getMessage());
        }
        return strcont;
    }
}
