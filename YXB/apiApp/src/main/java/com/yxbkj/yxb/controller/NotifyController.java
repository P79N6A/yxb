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
 *  回调通知接口 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-10
 */
@Api(value = "NotifyController",description = "回调通知接口")
@Controller
@RequestMapping("/notify")
public class NotifyController {
    @Autowired
    private ServerOrderFeignClient serverOrderFeignClient;
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "国寿支付回调 生产环境",notes = "国寿支付回调 生产环境")
    @RequestMapping(value = "/gsPayNotify")
    public Object gsPayNotify(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        String req = request.getParameter("req");
        Result<Boolean> booleanResult = serverSystemFeignClient.policyNotify(req);
        response.setCharacterEncoding("UTF-8");

        if(booleanResult.getData()){
            String msg = booleanResult.getMsg();
            String[] split = msg.split(",");
            String content = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<bets>\n" +
                    "<pubarea>\n" +
                    "<pkg_id>1</pkg_id><trans_type>301</trans_type><submit_time>"+split[1]+"</submit_time><resp_code>4</resp_code>\n" +
                    "<resp_desc>成功</resp_desc>\n" +
                    "</pubarea>\n" +
                    "<body>\n" +
                    "<totalnum>1</totalnum>\n" +
                    "<resp_list>\n" +
                    "<req_seq>1</req_seq>\n" +
                    "<trans_id>"+split[0]+"</trans_id>\n" +
                    "<resp_code>4</resp_code>\n" +
                    "<resp_desc>交易成功</resp_desc>\n" +
                    "</resp_list>\n" +
                    "</body>\n" +
                    "</bets>";
            logger.info("【易小宝科技API:   借款人意外险请求返回明文】"+ content );
            String strdoc =   URLEncoder.encode(content,"UTF-8");
            logger.info("【易小宝科技API:   借款人意外险请求返回】"+ strdoc );
            response.setContentType("application/xml");
            OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            out.write(strdoc);
            out.flush();
            out.close();
        }
        logger.info("【易小宝科技API:   借款信息回调】"+paramsStr);
        return null;
    }

    @ApiOperation(value = "国寿支付回调前台",notes = "国寿支付回调前台")
    @RequestMapping(value = "/gsPayReturn")
    public Object gsPayReturn(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
        return  null;
    }


    @ApiOperation(value = "安心车险订单回调",notes = "安心保险订单回调")
    @RequestMapping(value = "/axCarCallBack")
    @ResponseBody
    public Object axCarCallBack(HttpServletRequest request,HttpServletResponse response) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   安心车险订单回调】"+paramsStr);
        String content = streamToString(request);
        logger.info("【易小宝科技API:   安心车险订单回调Stream】"+content);
        Map<String,Object> map = new HashMap<>();
        map.put("responseCode","200");
        map.put("message","");
        map.put("data","");
        return  map;
    }

    @ApiOperation(value = "安心保险订单回调",notes = "安心保险订单回调")
    @RequestMapping(value = "/axOrderCallBack")
    @ResponseBody
    public Object axOrderCallBack(HttpServletRequest request,HttpServletResponse response) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   安心保险订单回调】"+paramsStr);
        String content = streamToString(request);
        logger.info("【易小宝科技API:   安心保险订单回调Stream】"+content);
        serverOrderFeignClient.axOrderCallBack(content);
        Map<String,Object> map = new HashMap<>();
        map.put("responseCode","200");
        map.put("message","");
        map.put("data","");
        return  map;
    }
    @ApiOperation(value = "安心保险保单回调",notes = "安心保险保单回调")
    @RequestMapping(value = "/axPolicyCallBack")
    @ResponseBody
    public Object axPolicyCallBack(HttpServletRequest request,HttpServletResponse response) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   安心保险保单回调】"+paramsStr);
        String content = streamToString(request);
        logger.info("【易小宝科技API:   安心保险保单回调Stream】"+content);
        serverOrderFeignClient.axPolicyCallBack(content);
        Map<String,Object> map = new HashMap<>();
        map.put("responseCode","200");
        map.put("message","");
        map.put("data","");
        return  map;
    }


    @ApiOperation(value = "易安保险回调",notes = "易安保险回调")
    @RequestMapping(value = "/yiAnNotify")
    @ResponseBody
    public Object pingAnNotify(HttpServletRequest request,HttpServletResponse response) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   易安保险回调】"+paramsStr);
        String content = streamToString(request);
        serverOrderFeignClient.yiAnNotify(content);
        /*{"amount":1.00,"dealType":"01","endDate":"1540742399000","epolicyUrl":"http://113.105.74.146:8083/epolicy/getEpolicy/v3?i=NnY4R2QxbTMwMUptalM5OTk5OEhNcTIwMThsWkNYMDAwMDAwMzU1NDZNYW9yRDk5eFg5OQ==.pdf&nonce=zN8w/UwsbV12EgaXpqWeCpdj2Eg=","orderCode":"1055388859363229696","orderExt":"1055386016422035456","othPolicyNo":"OFUN20181025172126451","param1":null,"payDate":1540459442000,"payResult":"Y","policyNo":"8G3019999201800000035546","riskCode":"9999","riskName":"e享运动-个人综合意外险","startDate":"1540656000000"}*/
        logger.info("【易小宝科技API:   易安保险回调Stream】"+content);
        WebUtils.outPrint(response,"SUCCESS");
        return  null;
    }

    @ApiOperation(value = "平安保险回调",notes = "平安保险回调")
    @RequestMapping(value = "/pingAnNotify")
    @ResponseBody
    public Result<String> pingAnNotify(HttpServletResponse response) {
        synchronized (this){
            HttpServletRequest requet = HttpKit.getHttpServletRequest();
            Map<String, String> paramMap = RequestParams.getParams(requet);
            String paramsStr = RequestParams.getOrderParamString(paramMap);
            logger.info("【易小宝科技API:   平安保险回调】"+paramsStr);
            AcceptParam acceptParam = new AcceptParam();
            boolean sign = PingAnUtils.validityData(paramMap, paramMap.get("sign"));
            if(sign){
                acceptParam.orderId = paramMap.get("channel_order_no");
                acceptParam.payType = paramMap.get("pay_type");
                serverOrderFeignClient.acceptOrder(acceptParam);
                try{
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    out.write("TRUE".getBytes());
                    out.flush();
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                try{
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    out.write("FALSE".getBytes());
                    out.flush();
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                logger.info("【易小宝科技API:   平安保险回调】 签名验证错误"+paramsStr);
                return new Result<String>(Code.FAIL,"签名验证错误",null,Code.IS_ALERT_YES);
            }
            return new Result<String>(Code.SUCCESS,"",null,Code.IS_ALERT_NO);
        }
    }

    @ApiOperation(value = "众安保险回调",notes = "众安保险回调")
    @RequestMapping(value = "/zhongAnNotify")
    @ResponseBody
    public Result<String> zhongAnNotify(HttpServletRequest request) {
        HttpServletRequest requet = HttpKit.getHttpServletRequest();
        Map<String, String> paramMap = RequestParams.getParams(requet);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   众安保险回调】"+paramsStr);
       try{
           ZhongAnNotifyClient notify = new ZhongAnNotifyClient("prd", RSAUtils.PRK); //PRK =开发者私钥
           Map<String, String[]> map = request.getParameterMap();
           String result = notify.parseNotifyRequest(map);
           logger.info("纵安解析数据成功"+result);
           //RC4{"productId":"PPRO19","source":"10000012","memberId":"M8536525192"}
           //{"bizContent":"a5f4b4af142549564e2e708f437c15c9f97b66674576a3d354db8b6debf5635b9f6fe695d08363c8911c4e1a079dfd9e9a5514a43a855af817820775239c4488ddf7d6","effectiveDate":"2018-09-11","expiryDate":"2019-09-10","notifyType":"1","policyNo":"88AF72001406205031","premium":"50.00","productName":"个人无忧意外险(标准版)","promoteCode":"INST180991992001","promoteFee":"15.00","promoteName":"个人无忧","sumInsured":"210000.00"}
           if(!StringUtil.isEmpty(result)){
               serverOrderFeignClient.znProductNotify(result);
           }
       }catch (Exception e){
           e.printStackTrace();
           logger.info("纵安解析数据失败"+e.getMessage());
       }
        return new Result<String>(Code.SUCCESS,"",null,Code.IS_ALERT_NO);
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

    @ApiOperation(value = "中瑞保险回调",notes = "中瑞保险回调")
    @RequestMapping(value = "/zhongRuiNotify")
    @ResponseBody
    public Result<String> zhongRuiNotify(HttpServletRequest request) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   中瑞保险回调】"+paramsStr);
        try{
            logger.info("开始解析中瑞密文输入流"+paramsStr);
            ServletInputStream ris = request.getInputStream();
            StringBuilder content = new StringBuilder();
            byte[] b = new byte[1024];
            int lens = -1;
            while ((lens = ris.read(b)) > 0) {
                content.append(new String(b, 0, lens));
            }
            String strcont = content.toString();// 内容
            logger.info("解析中瑞密文输入流完毕"+strcont);
            if(!StringUtil.isEmpty(strcont)){
                serverOrderFeignClient.zrProductNotify(strcont);
            }else{
                logger.info("解析中瑞密文为空----------------------");
            }
            if(ris!=null) ris.close();
        }catch (Exception e){
            e.printStackTrace();
            logger.info("中瑞保险回调出现异常"+e.getMessage());
        }
        return new Result<String>(Code.SUCCESS,"",null,Code.IS_ALERT_NO);
    }

    @ApiOperation(value = "借款信息回调  测试环境",notes = "借款信息回调  测试环境")
    @RequestMapping(value = "/borrowNotify")
    public Object borrowNotify(HttpServletRequest request,HttpServletResponse response) throws IOException {

        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        String req = request.getParameter("req");
        Result<Boolean> booleanResult = serverSystemFeignClient.policyNotify(req);
        response.setCharacterEncoding("UTF-8");

        if(booleanResult.getData()){
            String msg = booleanResult.getMsg();
            String[] split = msg.split(",");
            String content = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<bets>\n" +
                    "<pubarea>\n" +
                    "<pkg_id>1</pkg_id><trans_type>301</trans_type><submit_time>"+split[1]+"</submit_time><resp_code>4</resp_code>\n" +
                    "<resp_desc>成功</resp_desc>\n" +
                    "</pubarea>\n" +
                    "<body>\n" +
                    "<totalnum>1</totalnum>\n" +
                    "<resp_list>\n" +
                    "<req_seq>1</req_seq>\n" +
                    "<trans_id>"+split[0]+"</trans_id>\n" +
                    "<resp_code>4</resp_code>\n" +
                    "<resp_desc>交易成功</resp_desc>\n" +
                    "</resp_list>\n" +
                    "</body>\n" +
                    "</bets>";
            logger.info("【易小宝科技API:   借款人意外险请求返回明文】"+ content );
            String strdoc =   URLEncoder.encode(content,"UTF-8");
            logger.info("【易小宝科技API:   借款人意外险请求返回】"+ strdoc );
            response.setContentType("application/xml");
            OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            out.write(strdoc);
            out.flush();
            out.close();
        }
        logger.info("【易小宝科技API:   借款信息回调】"+paramsStr);
        return null;
    }



    @ApiOperation(value = "易宝支付回调  会员升级 异步",notes = "易宝支付回调  会员升级 异步")
    @RequestMapping(value = "/yeepayBuyMemberNotify")
    @ResponseBody
    public Result<String> yeepayBuyMemberNotify(HttpServletRequest request) {
        HttpServletRequest requet = HttpKit.getHttpServletRequest();
        Map<String, String> paramMap = RequestParams.getParams(requet);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   易宝支付回调  会员升级 异步】"+paramsStr);
        String response = request.getParameter("response");
        try{
            // 解码并解密
            response = YeePayUtils.parseYiBaoResponse(response);
            // 处理业务
            Result<Map<String, Object>> result = serverOrderFeignClient.buyMemberNotifyForYiBao(response);
        }catch (Exception e){
            logger.info("【易小宝科技API】    易宝支付回调  会员升级  异常 "+e.getMessage());
        }
        return new Result<String>(Code.SUCCESS,"",null,Code.IS_ALERT_NO);
    }
    @ApiOperation(value = "易宝支付回调  加油卡 异步",notes = "易宝支付回调  加油卡 异步")
    @RequestMapping(value = "/getYeepayRechargeNotify")
    @ResponseBody
    public Result<String> getYeepayRechargeNotify(HttpServletRequest request) {
        HttpServletRequest requet = HttpKit.getHttpServletRequest();
        Map<String, String> paramMap = RequestParams.getParams(requet);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   易宝支付回调  加油充值】"+paramsStr);
        String response = request.getParameter("response");
        //String response = "N60CUfReMgKguIhLoSrQ8vJB-qVYbBjl731Vae0KL0q5KLMQ_J1pE2yYY7N__5b1TtpuJBharXnieGmktNwhp5mxm79pF1AlwyKmEz5BRN37F14seKjGPTcP4HPECguzTiYDHa3z9THJkfQ0jSaE1KI0bm26vxB9cFGzuT-JZiJrNjKkYGxUhWk236j03IndpkoXdn5kIaWiaVtSYyH9qf8eoK4xSRy0xnHJx-sklhriXv0uU2I6Kx-R813AuKkJZFnzL-2ykA-lUmFVsNHdQ1QtKOiHyDRZK7kzPPo3E1zsl3QnY6aTibUiweqzj5r5kkUclZKqgqFxzDhCcjn9Ew%24JqqxuDOtExqt0JbZ_4sE4xZIGyklYZkDY3ehL6n4CwDfy8utqm8sSG1z-LUCzQ7_vFYr8trJZD4XimHBomA-2bS78TzOisVEQ6W7mgUX34XlvGt4uonGhqG_8bqmgRfGZGiDBIcpVRXzmyWRHiG6AO3FC6jUbGgxReVIW0T0Xk2uRIRgwgyd-qunlNFqsFUijc5EojbxiSyQ94b0j9XZWjVa5o3RMCbmMNTPvC_QdHlNFaS7rfX6POkODZ9Mbuh6fD-cq0aL5g4-L9gBp3atNahLXsbXMkldSda_K8YtMJX3ku7xEjqSLZTmygxAAUjAvWRcS9xXvyCjFds-TA6NF0uuU1bkqH2rP0FHe4ED9T-sMNUCIBh1DevfTHbI5xn3ql76OHQJHJ3wn-Cjti8CHGzsU4u6TIfsZyVL_NYcfKzeB6Mip_I6SACAPnT2FWOoLCMo5EvkLEBiu-xfovThh5V_870rHzn0Fg5oRaK5QEgmgAsWrTbeivNb1572z9vNk2BKyeFvTFLNekceBBC4dpvh5REkbpgK39kuKtw41155pWJN6vtbhll8hosKkjo4LHrxm52qJWKH5IOL3HoPRqqak3xHItyBjCOVA9FhXLAkmUVxrfU3xhfwv5ATxGaSM6Lh4BVpuuLNfpvWuABoYv8I-4qiXYhoVnwyjl8-_UwfDejTn2aSCnhpJw6lUHUCfyzKvb9BBMIJAB5a54NzKI2Pto4ke5zYwtITTTm6ciG3KCpUVogoHs71akbpgfQ0LDCJ0r0R7v-tC3NKFnQMRYEwTLyomup13gZc7z0F0U41frkxU4o9X8XmrvfmyxicXjdSf7r4KsdZ7Kn8YAD275LLsJ5uBjzV2i3NfYDNy-NxXWoacp9Rp5bsdsEoM-Ddp8hZOT7jmsy68AG88KHUCN_-vIFTVvCm7HSijz9vapuoMzjzjcXx_uPbtCaaTG0eX6PNonYkNhVjCkSOl8RXn3aihmxn4sjsLqo9bJ9wY7lHzKO_wxL12pfLvsTeWo_7U9iCLmu8EWbQdgV9fp5tgbZ5Ebm2OLX7wRNs2_He1R0SfjuhykfpNIc3sBV96x8Rs7KgX1PzPc4TDDbm9jzcq4p-whfi02T0_yJ3uS4PEj8SVuHqKmWQEQbG0ma0SsvVfb4VwtfAHT3iRLEkEDtvNQ%24AES%24SHA256";
        //String response = "SGJYQdlIxLjcAVQhpskl6xjxS1U5K39UmGHN7xRWgcrYK_ywpDk3DF6N7OXyrrieil77T8aOVOFtyODoUQ3Cfb6WvJgS3st07Hn_rfmzNfWNhi31Tc_F-qiB78O024nFQM15Ctozz9fx86iJtYEagO41jMiQkgxRq1Ce3A0sh0duS8Dur66Ngxm1PS1lZVPH2XHITXLxWo6fZ9-M9c0t3Q0O2AsnYgpLUMIVgVkP57mnHf7c8d_AdaahRG8ONp0WpLUsciUj3yuIQUrsVWXWaM94FSRSo08DXd9EbrxgW0ipQaZAeccApgSouwhZAErx7qQj7K-mCRKeUCT8bY7AZQ%24jF5XLXA9lXJMfUyr73ONbOZJ3I1blimJKHkpLM55QHzXfhykOkEJ9NZbN4_0JMa4Ph5VB4GGjLqGTdEvMlgqMkiS40REm2_7JRFdaJus_HhudAevDyu4CFqafn9KK_rF7KNflGxy_lDTf1_SwKTreBIDqp-WNU4HMoHFcfMuzI84ZtJeJYbohIEVEJhPhRBEsU3TTS1wgD0jGAyRMdL_zn8hJnbDLyLEy9io0pfz7qHeWBC4r3ZYR_lZncuZar3BeNFtLIT97oc05ftTPpJAgQv5AqPRah7EV8Vvi9RHcdlpaxoZ43GZ_GFbfKxMauHbET8KvD203xg2IjZWR3koM0GqxLBPxkNK6GOfPGOTZIPScNUVG17xl3yz5-ZdLWZcjC2ONQJCjboMCpCWYeMccHkwgDJhw73GvX9sem7ujgsRzOj2trCOA0JNDdh-dBt0_2EfDxQWhlBEN-UQVMYYPma2dn18yJh0oOhOeZDTk6uMc7h13unZcKx7iT7dArXYhnb221Pn7bqgLMwDR1JIY-QpAg6USMD30qS6yLcF6QqTk5L9gt0axhRkQB3tqLC9C4iP7huK7p6mjDc_Cp0XyNgg6cPpvAj0gh3DyKG88ogJegfJAEIQB3b7oBuDShIEvIfs6TEM3m7uSzTGXBWc01c5wXUK_JodDc2O-jU3t7TCZ1A9xqjj0MogVOspJZ1sCl7h1zqdyIreW_dN4bXRuJJDpANd2YuDuZxg_Hf_k-oz7s2PW6zA8g7i5_r-PP7jxUmXFTEhgJPz708P3KNhvXz_iNOlgMza1fyL-sRQD6JMLR1jSbpiBclvFfyf5XmCgGH28uXNmzw0tXoXxTAEr7_PuUStlWFmexjcielr6VbnWa5M46CHzAsnMJt9KjSFeGT-Y2UyZQlUW5wQ_xtDLpOy-JMkrCDQuv1gfLEfpxAvcaMJyIW1vPSQdkn1Wk4Q_XziKSDXvAF7QQSAJc6rO-MVXVvZYyq33PtmsZL_mZbOJv5VEQLHh4oZw0YY7Fl4ejPvalrYrc5k8-eslYGgs-cscIfnQ-6QyX6XiMi_mmqy21ZR3sXMm1pVlhurZk_Nd9YBVNdZPDNlXUUYnGr2R16ekaeo3Q6OVFlP_DFU7MrpLfWruFnheZp1tUdXLKvjNWvDsI4L7pDvo52-pinVBELHjbvTyc-mo0CDkIQnT0A%24AES%24SHA256";
        try{
            // 解码并解密
            response = YeePayUtils.parseYiBaoResponse(response);
            // 处理业务
            Result<Map<String, Object>> result = serverOrderFeignClient.rechargeNotifyForYiBao(response);
        }catch (Exception e){
            logger.info("【易小宝科技API】    易宝支付回调  加油充值  异常 "+e.getMessage());
        }
        return new Result<String>(Code.SUCCESS,"",null,Code.IS_ALERT_NO);
    }

    @ApiOperation(value = "易宝支付回调  会员升级 同步",notes = "易宝支付回调  会员升级 同步")
    @RequestMapping(value = "/yeepayBuyMemberRedirect")
    public String yeepayBuyMemberRedirect(HttpServletRequest request) {
        HttpServletRequest requet = HttpKit.getHttpServletRequest();
        Map<String, String> paramMap = RequestParams.getParams(requet);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   易宝支付回调  会员升级 同步】"+paramsStr);
        return "redirect:https://www.baidu.com/";
    }


    @ApiOperation(value = "微信支付H5回调  会员升级",notes = "微信支付H5回调  会员升级")
    @RequestMapping(value = "/wxh5Notify")
    public String wxh5Notify(HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.info("【易小宝科技API:   微信支付H5回调  会员升级】");
        Map<String, String> params = new HashMap<>();
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        // 解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 通知微信.回传信息.默认.
        String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        if (!PayCommonUtil.isTenpaySign("UTF-8", packageParams,  PayConfigUtil.API_KEY)) {
             logger.info("【易小宝科技API:  微信支付回调：签名验证失败】"+params);
            out.write(resXml.getBytes());
            out.flush();
            return "error";
        }
        // 返回状态码是否成功
        if (!"SUCCESS".equals((String) packageParams.get("result_code"))) {
            out.write(resXml.getBytes());
            out.flush();
            logger.info("【易小宝科技API:  返回状态码错误】");
            return "error";
        }
        logger.info("【易小宝科技API:  处理业务信息】");
        String out_trade_no = (String) packageParams.get("out_trade_no");
        logger.debug( "【易小宝科技API】 业务数据付款订单号   out_trade_no: {}",out_trade_no);
        if(!StringUtil.isEmpty(out_trade_no)){
            serverOrderFeignClient.buyMemberNotifyForWxH5(out_trade_no);
        }
        // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>"
                + "</xml> ";
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        return "success";
    }

    @ApiOperation(value = "微信支付H5回调  加油充值",notes = "微信支付H5回调  加油充值")
    @RequestMapping(value = "/wxh5NotifyRecharge")
    public String wxh5NotifyRecharge(HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.info("【易小宝科技API:   微信支付H5回调  加油充值】");
        Map<String, String> params = new HashMap<>();
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        // 解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 通知微信.回传信息.默认.
        String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        if (!PayCommonUtil.isTenpaySign("UTF-8", packageParams,  PayConfigUtil.API_KEY)) {
            logger.info("【易小宝科技API:  微信支付回调：签名验证失败】"+params);
            out.write(resXml.getBytes());
            out.flush();
            return "error";
        }
        // 返回状态码是否成功
        if (!"SUCCESS".equals((String) packageParams.get("result_code"))) {
            out.write(resXml.getBytes());
            out.flush();
            logger.info("【易小宝科技API:  返回状态码错误】");
            return "error";
        }
        logger.info("【易小宝科技API:  处理业务信息】");
        String out_trade_no = (String) packageParams.get("out_trade_no");
        logger.debug( "【易小宝科技API】 业务数据付款订单号   out_trade_no: {}",out_trade_no);
        if(!StringUtil.isEmpty(out_trade_no)){
            serverOrderFeignClient.rechargeNotifyForWxH5Recharge(out_trade_no);
        }
        // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>"
                + "</xml> ";
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        return "success";
    }
    @ApiOperation(value = "理财产品 借款处理",notes = "理财产品 借款处理")
    @RequestMapping(value = "/handBorrowProduct")
    @ResponseBody
    public Object handBorrowProduct(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   财产品 借款处理 回调】"+paramsStr);

        String res = paramsStr.replace("=", "");

        Result<Map<String, Object>> result = serverOrderFeignClient.handBorrowProduct(res);


        Map<String,Object> map  = new HashMap<>();
        map.put("code",1);
        map.put("message","服务器暂时无法处理此请求!");
        return map;
    }



    @ApiOperation(value = "理财产品 创建借款产品",notes = "理财产品 创建借款产品")
    @RequestMapping(value = "/createBorrowProduct")
    @ResponseBody
    public Object createBorrowProduct(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   理财产品 创建借款产品】"+paramsStr);

        String res = paramsStr.replace("=", "");

        Result<Map<String, Object>> result = serverOrderFeignClient.createBorrowProduct(res);


        Map<String,Object> map  = new HashMap<>();
        map.put("code",1);
        map.put("message","服务器暂时无法处理此请求!");
        return map;
    }




    @ApiOperation(value = "借款人意外险信息回调",notes = "借款人意外险信息回调")
    @RequestMapping(value = "/proposalNotify")
    @ResponseBody
    public Object proposalNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   借款人意外险信息回调】"+paramsStr);
        Map<String,Object> map  = new HashMap<>();
        map.put("code",0);
        map.put("message","响应成功!");
        return map;
    }


    @ApiOperation(value = "数据接收回调",notes = "数据接收回调")
    @RequestMapping(value = "/receiveData")
    @ResponseBody
    public Object receiveData(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   数据接收回调】"+paramsStr);
        Map<String,Object> map  = new HashMap<>();
        map.put("code",0);
        map.put("message","响应成功!");
        return map;
    }
    @ApiOperation(value = "易宝支付回调  充值加油 异步",notes = "易宝支付回调  充值加油 异步")
    @RequestMapping(value = "/yeepayRechargeNotify")
    @ResponseBody
    public Result<String> yeepayRechargeNotify(HttpServletRequest request) {
        HttpServletRequest requet = HttpKit.getHttpServletRequest();
        Map<String, String> paramMap = RequestParams.getParams(requet);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   易宝支付回调  充值加油 异步】"+paramsStr);
        //String response = request.getParameter("response");
        String response = "SGJYQdlIxLjcAVQhpskl6xjxS1U5K39UmGHN7xRWgcrYK_ywpDk3DF6N7OXyrrieil77T8aOVOFtyODoUQ3Cfb6WvJgS3st07Hn_rfmzNfWNhi31Tc_F-qiB78O024nFQM15Ctozz9fx86iJtYEagO41jMiQkgxRq1Ce3A0sh0duS8Dur66Ngxm1PS1lZVPH2XHITXLxWo6fZ9-M9c0t3Q0O2AsnYgpLUMIVgVkP57mnHf7c8d_AdaahRG8ONp0WpLUsciUj3yuIQUrsVWXWaM94FSRSo08DXd9EbrxgW0ipQaZAeccApgSouwhZAErx7qQj7K-mCRKeUCT8bY7AZQ%24jF5XLXA9lXJMfUyr73ONbOZJ3I1blimJKHkpLM55QHzXfhykOkEJ9NZbN4_0JMa4Ph5VB4GGjLqGTdEvMlgqMkiS40REm2_7JRFdaJus_HhudAevDyu4CFqafn9KK_rF7KNflGxy_lDTf1_SwKTreBIDqp-WNU4HMoHFcfMuzI84ZtJeJYbohIEVEJhPhRBEsU3TTS1wgD0jGAyRMdL_zn8hJnbDLyLEy9io0pfz7qHeWBC4r3ZYR_lZncuZar3BeNFtLIT97oc05ftTPpJAgQv5AqPRah7EV8Vvi9RHcdlpaxoZ43GZ_GFbfKxMauHbET8KvD203xg2IjZWR3koM0GqxLBPxkNK6GOfPGOTZIPScNUVG17xl3yz5-ZdLWZcjC2ONQJCjboMCpCWYeMccHkwgDJhw73GvX9sem7ujgsRzOj2trCOA0JNDdh-dBt0_2EfDxQWhlBEN-UQVMYYPma2dn18yJh0oOhOeZDTk6uMc7h13unZcKx7iT7dArXYhnb221Pn7bqgLMwDR1JIY-QpAg6USMD30qS6yLcF6QqTk5L9gt0axhRkQB3tqLC9C4iP7huK7p6mjDc_Cp0XyNgg6cPpvAj0gh3DyKG88ogJegfJAEIQB3b7oBuDShIEvIfs6TEM3m7uSzTGXBWc01c5wXUK_JodDc2O-jU3t7TCZ1A9xqjj0MogVOspJZ1sCl7h1zqdyIreW_dN4bXRuJJDpANd2YuDuZxg_Hf_k-oz7s2PW6zA8g7i5_r-PP7jxUmXFTEhgJPz708P3KNhvXz_iNOlgMza1fyL-sRQD6JMLR1jSbpiBclvFfyf5XmCgGH28uXNmzw0tXoXxTAEr7_PuUStlWFmexjcielr6VbnWa5M46CHzAsnMJt9KjSFeGT-Y2UyZQlUW5wQ_xtDLpOy-JMkrCDQuv1gfLEfpxAvcaMJyIW1vPSQdkn1Wk4Q_XziKSDXvAF7QQSAJc6rO-MVXVvZYyq33PtmsZL_mZbOJv5VEQLHh4oZw0YY7Fl4ejPvalrYrc5k8-eslYGgs-cscIfnQ-6QyX6XiMi_mmqy21ZR3sXMm1pVlhurZk_Nd9YBVNdZPDNlXUUYnGr2R16ekaeo3Q6OVFlP_DFU7MrpLfWruFnheZp1tUdXLKvjNWvDsI4L7pDvo52-pinVBELHjbvTyc-mo0CDkIQnT0A%24AES%24SHA256";
        try{
            // 解码并解密
            response = YeePayUtils.parseYiBaoResponse(response);
            // 处理业务
            Result<Map<String, Object>> result = serverOrderFeignClient.rechargeNotifyForYiBao(response);
        }catch (Exception e){
            logger.info("【易小宝科技API】    易宝支付回调  会员升级  异常 "+e.getMessage());
        }
        return new Result<String>(Code.SUCCESS,"",null,Code.IS_ALERT_NO);
    }


}
