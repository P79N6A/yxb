package com.yxbkj.yxb.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.ResultApi;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.system.SafeApp;
import com.yxbkj.yxb.entity.system.ThirdOrder;
import com.yxbkj.yxb.entity.system.ThirdPayres;
import com.yxbkj.yxb.entity.vo.BorrowParam;
import com.yxbkj.yxb.entity.vo.BorrowParamNew;
import com.yxbkj.yxb.system.mapper.SafeAppMapper;
import com.yxbkj.yxb.system.mapper.ThirdOrderMapper;
import com.yxbkj.yxb.system.mapper.ThirdPayresMapper;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.util.*;
import com.yxbkj.yxb.util.wxshare.WxUtil;
import com.yxbkj.yxb.util.zhongan.RSAUtils;
import com.yxbkj.yxb.util.zhongan.YxbRsaUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 系统管理 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-18
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ConfigService configService;
    @Autowired
    private ThirdOrderMapper thirdOrderMapper;
    @Autowired
    private ThirdPayresMapper thirdPayresMapper;
    @Autowired
    private SafeAppMapper safeAppMapper;
    /**
     * 作者: 李明
     * 描述: 获取分享信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取分享信息",notes = "获取分享信息")
    @GetMapping("/getWxShareinfo")
    public Result<Map<String, String>> getWxShareinfo(
            @ApiParam(value = "地址",required = true)@RequestParam(value = "url",required = false,defaultValue = "") String  url
    ){
        Map<String, String> sign = WxUtil.getSign(url);
        return new Result<>(Code.SUCCESS,"",sign,Code.IS_ALERT_NO);
    }

    /**
     * 作者: 李明
     * 描述: 获取APP版本信息
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取APP版本信息",notes = "获取APP版本信息")
    @GetMapping("/getAppVersion")
    public Result<JSONObject> getAppVersion(){
        String appVersion = configService.getConfigValue("appVersion");
        JSONObject jsonObject = JSONObject.parseObject(appVersion);
        return new Result<>(Code.SUCCESS,"版本获取成功!",jsonObject,Code.IS_ALERT_NO);
    }
    /**********************************************借款人意外险***********************************************/

    /**
     * 作者: 李明
     * 描述: 获取第三方订单跳转地址
     * 备注:
     * @return
     */
    @ApiOperation(value = "获取第三方订单跳转地址",notes = "获取第三方订单跳转地址")
    @GetMapping("/getOtherReturnUrl")
    public Result<String> getOtherReturnUrl(
            @ApiParam(value = "投保单号",required = true)@RequestParam(value = "insur_no",required = false,defaultValue = "") String  insur_no
    ){
        ThirdOrder order = new ThirdOrder();
        order.setProposalNo(insur_no);
        ThirdOrder thirdOrder = thirdOrderMapper.selectOne(order);
        if(thirdOrder==null){
            return new Result<String>(Code.FAIL,"获取地址成功!","",Code.IS_ALERT_YES);
        }
        return new Result<String>(Code.SUCCESS,"获取地址成功!",thirdOrder.getCallUrl(),Code.IS_ALERT_NO);
    }

    /**
     * 作者: 李明
     * 描述: 验证请求参数
     * 备注:
     * @return
     */
    public ResultApi<String> validateRequest(HttpServletRequest request){
        String appid = request.getParameter("appid");
        String data = request.getParameter("data");
        if(StringUtil.isEmpty(appid)){
            return new ResultApi<String>(Code.FAIL,"appid不能为空",null);
        }
        if(StringUtil.isEmpty(data)){
            return new ResultApi<String>(Code.FAIL,"data不能为空",null);
        }
        logger.info("接收到的参数信息:appid:"+appid+"data:"+data);
        SafeApp safeApp = new SafeApp();
        safeApp.setAppid(appid);
        SafeApp safeAppDb = safeAppMapper.selectOne(safeApp);
        if(safeAppDb==null){
            return new ResultApi<String>(Code.FAIL,"无效的appid",null);
        }
        try {
            String data_encode =  YxbRsaUtils.privateDecrypt(data,YxbRsaUtils.getPrivateKey(RSAUtils.privateKey));
            logger.info("转换后的参数信息:data_encode:"+data_encode);
            return new ResultApi<String>(Code.SUCCESS,"数据解密完成!",data_encode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ResultApi<String>(Code.FAIL,"数据解密失败!",null);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return new ResultApi<String>(Code.FAIL,"数据解密失败!",null);
        }
    }

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {


        String prb = "MIICdAIBADANBgkqhkiG9w0BAQEFAASCAl4wggJaAgEAAoGBAJXhkZuoweksWB/B" +
                "melECm/2fWi2oTeNPy7TOMnfvR6hhXZOqjTgW02cLHF7lBWEHAdasiVmlgRK5eru" +
                "b8FW3rMt/vKUGeAm0Ht34QDqpiIKJh1F3b/jijaH7zaT4+xapXFp4dBASS4HdgCv" +
                "PR1we/JHDiite2MV8TEBd8CWtoZJAgMBAAECgYAWfEjD+YVd3fE6kmjsvfBy65/U" +
                "3dFB7EbWV4nlf1N4DPNg1FxkomQQOKXSQJ/uumsaD1k6kzFeY34qyKqrbsTqEe24" +
                "Ja7Hhtcdjt+UrhJfg3dMMiAWvmdcpOdyCiIqW3JSFTYGK/Q1zsLCHCx2qOAGdC+B" +
                "5ky2DjjcqXIP4Kz1oQJBAMWgLe2tJKmvd7FtDTXD164WwNZ/2PexJu0TagtoqpSf" +
                "KsgBIvsrhnyFMh/1oqKV+tiXe3nz6/cOq1QnX5SqjMMCQQDCJxkhjsICDzHcm2+0" +
                "s0cVROcgGflKdxSziixPTEXLHDYwWgb+216Pgi1dHb78zMefDOkKUnGg5FJgEXAk" +
                "f6ADAkBqR9uLX7tA2lHHhHs/N+SNBkWM1dKsWoQxqWg1XIOoS/Uo/JuAcobv/n3X" +
                "fDWLtJbj1oucKVb0VdpD9qzLefEbAkBhZdXQpNokyFSeNAfU4b7+J4O+8ejCd3yW" +
                "GPHjkgLNQsjYdsFdptUILyjstphyH5Tg8EwUFonUSdYdRYI5fSDbAj8i/Z8t1WSn" +
                "4eaTCoPG6ypPQhbKSgXsnqopEiRKdNfpc8KlJxbDr1ckgVj2ashftZANabEg8MSN" +
                "uYB+I1y6mvI=";

        String aaa =  YxbRsaUtils.publicEncrypt("AAA",YxbRsaUtils.getPublicKey(YxbRsaUtils.public_key));

        String decode = YxbRsaUtils.privateDecrypt(aaa,YxbRsaUtils.getPrivateKey(prb));

        System.out.println(decode);
    }

    /**
     * 作者: 李明
     * 描述: 保单地址下载请求
     * 备注:
     * @return
     */
    @ApiOperation(value = "保单地址下载请求",notes = "保单地址下载请求")
    @GetMapping(value = "/proposalDownload")
    public Object proposalDownload(HttpServletRequest request) {
        ResultApi<String> resultApi = validateRequest(request);
        if(resultApi.getResponseCode()==Code.FAIL){
            return resultApi;
        }
        String data = resultApi.getResponseData();
        JSONObject jsonObject = JSONObject.parseObject(data);
        String proposalNo = jsonObject.getString("proposalNo");
        if(StringUtil.isEmpty(proposalNo)){
            return  new ResultApi<String>(Code.FAIL,"保单号不能为空!",null);
        }
        StringBuffer  sendMsgBuffer = new StringBuffer(
                "");
        sendMsgBuffer.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.travelInTW.frontend.sinosoft.com\" xmlns:xsd=\"http://dto.travelInTW.frontend.sinosoft.com/xsd\">") ;
        sendMsgBuffer.append("<soapenv:Header/>") ;
        sendMsgBuffer.append("<soapenv:Body>") ;
        sendMsgBuffer.append("<ser:service>") ;
        sendMsgBuffer.append("<ser:request>") ;
        sendMsgBuffer.append("<xsd:policyNo>"+proposalNo+"</xsd:policyNo>") ;
        sendMsgBuffer.append("<xsd:pwd>111111</xsd:pwd>") ;
        sendMsgBuffer.append("<xsd:requestType>SYW</xsd:requestType>") ;
        sendMsgBuffer.append("<xsd:systemCode>SYW</xsd:systemCode>") ;
        sendMsgBuffer.append("<xsd:userName>FRONT</xsd:userName>") ;
        sendMsgBuffer.append("</ser:request>") ;
        sendMsgBuffer.append("</ser:service>") ;
        sendMsgBuffer.append("</soapenv:Body>") ;
        sendMsgBuffer.append("</soapenv:Envelope>") ;
        try{
            String res = WebserviceUtils.sendServiceData(sendMsgBuffer.toString(), YxbConfig.getDownloadWSUrl());
            return  new ResultApi<String>(Code.SUCCESS,"获取成功过!",res);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResultApi<String>(Code.FAIL,"未知异常!",null);
        }
    }

    /**
     * 作者: 李明
     * 描述: 投保请求
     * 备注:
     * @return
     */
    @ApiOperation(value = "投保请求",notes = "投保请求")
    @PostMapping(value = "/proposalSave")
    public Object proposalSave(HttpServletRequest request) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        Map<String,Object> map = new HashMap<>();
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        ResultApi<String> resultApi = validateRequest(request);
        if(resultApi.getResponseCode()==Code.FAIL){
            return resultApi;
        }
        String data = resultApi.getResponseData();
        BorrowParamNew param = null;
        try{
            param = JSONObject.parseObject(data, BorrowParamNew.class);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultApi<String>(Code.FAIL,"JSON解析异常!",null);
        }
        String appId = request.getParameter("appId");
        param.password="111111";
        param.requestType="01";
        param.systemCode="SYW";
        param.userName="FRONT";
        logger.info("【易小宝科技API:   投保请求】"+data);
        //先查询第三方订单号
        try{
            ThirdOrder tempOrder = new ThirdOrder();
            tempOrder.setOthOrderCode(param.mainEhm.othOrderCode);
            ThirdOrder thirdOrder = thirdOrderMapper.selectOne(tempOrder);
            if(thirdOrder!=null){
                return new ResultApi<String>(Code.FAIL,"第三方订单出现异常!",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResultApi<String>(Code.FAIL,"othOrderCode格式异常!",null);
        }
        //开始发送请求信息
        String xml = jsonToXmlNew(param);
        String errorMessage = "";
        Map<String,Object> responseData = new HashMap<>();
        String responseCode = "";
        try{
            String brrowWSUrl = YxbConfig.getBrrowWSUrl();
            logger.info("【易小宝科技API:   投保请求  投保接口地址】"+brrowWSUrl);
            String res = WebserviceUtils.sendServiceData(xml, brrowWSUrl);
            logger.info("【易小宝科技API:   投保请求  投保响应信息】"+res);
            //获取responseCode
            responseCode = getRes(res, "responseCode");
            errorMessage = getRes(res, "errorMessage");
            if("1".equals(responseCode)){
                String proposalNo = getRes(res, "proposalNo");
                responseData.put("proposalNo",proposalNo);
                String othOrderCode = getRes(res, "othOrderCode");
                responseData.put("othOrderCode",othOrderCode);
                ThirdOrder order =new ThirdOrder();
                order.setId(StringUtil.getUuid());
                order.setAmount(new BigDecimal(param.mainEhm.totalPremium));
                order.setProposalNo(proposalNo);
                order.setOthOrderCode(othOrderCode);
                order.setContent(xml);
                order.setAppId(appId);
                order.setExt(res);
                order.setStatus("0");
                order.setCreateTime(YxbConstants.sysDate());
                thirdOrderMapper.insert(order);
                return new ResultApi<Map<String,Object>>(Code.SUCCESS,"投保成功!",responseData);
            }else{
                return new ResultApi<String>(Code.FAIL,errorMessage,null);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("【易小宝科技API:   借款人意外险接口异常】"+xml);
            return new ResultApi<String>(Code.FAIL,"投保异常",null);
        }
    }

    /**
     * 作者: 李明
     * 描述: 投保请求新
     * 备注:
     * @return
     */
    @ApiOperation(value = "投保请求新",notes = "投保请求新")
    @PostMapping(value = "/proposalSaveNew")
    public Object proposalSaveNew(HttpServletRequest request) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        Map<String,Object> map = new HashMap<>();
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        ResultApi<String> resultApi = validateRequest(request);
        if(resultApi.getResponseCode()==Code.FAIL){
            return resultApi;
        }
        String data = resultApi.getResponseData();
        BorrowParamNew param = null;
        try{
            param = JSONObject.parseObject(data, BorrowParamNew.class);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultApi<String>(Code.FAIL,"JSON解析异常!",null);
        }
        String appId = request.getParameter("appId");
        param.password="111111";
        param.requestType="01";
        param.systemCode="SYW";
        param.userName="FRONT";
        logger.info("【易小宝科技API:   投保请求】"+data);
        //先查询第三方订单号
        try{
            ThirdOrder tempOrder = new ThirdOrder();
            tempOrder.setOthOrderCode(param.mainEhm.othOrderCode);
            ThirdOrder thirdOrder = thirdOrderMapper.selectOne(tempOrder);
            if(thirdOrder!=null){
                return new ResultApi<String>(Code.FAIL,"第三方订单出现异常!",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResultApi<String>(Code.FAIL,"othOrderCode格式异常!",null);
        }
        //开始发送请求信息
        String xml = jsonToXmlNew(param);
        String errorMessage = "";
        Map<String,Object> responseData = new HashMap<>();
        String responseCode = "";
        try{
            String brrowWSUrl = YxbConfig.getBrrowWSUrl();
            logger.info("【易小宝科技API:   投保请求  投保接口地址】"+brrowWSUrl);
            String res = WebserviceUtils.sendServiceData(xml, brrowWSUrl);
            logger.info("【易小宝科技API:   投保请求  投保响应信息】"+res);
            //获取responseCode
            responseCode = getRes(res, "responseCode");
            errorMessage = getRes(res, "errorMessage");
            if("1".equals(responseCode)){
                String proposalNo = getRes(res, "proposalNo");
                responseData.put("proposalNo",proposalNo);
                String othOrderCode = getRes(res, "othOrderCode");
                responseData.put("othOrderCode",othOrderCode);
                ThirdOrder order =new ThirdOrder();
                order.setId(StringUtil.getUuid());
                order.setAmount(new BigDecimal(param.mainEhm.totalPremium));
                order.setProposalNo(proposalNo);
                order.setOthOrderCode(othOrderCode);
                order.setContent(xml);
                order.setAppId(appId);
                order.setExt(res);
                order.setStatus("0");
                order.setCreateTime(YxbConstants.sysDate());
                thirdOrderMapper.insert(order);
                return new ResultApi<Map<String,Object>>(Code.SUCCESS,"投保成功!",responseData);
            }else{
                return new ResultApi<String>(Code.FAIL,errorMessage,null);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("【易小宝科技API:   借款人意外险接口异常】"+xml);
            return new ResultApi<String>(Code.FAIL,"投保异常",null);
        }
    }

    private String jsonToXmlNew(BorrowParamNew param) {
        String a = "";

        for(int i = 0;i<param.insuredEhmArray.size();i++){

            String b = "";

            for(int j = 0 ; j < param.insuredEhmArray.get(i).itemKindEhmArray.size();j++){

                    b+=  " <xsd1:itemKindEhmArray>  \n" +
                        "            <xsd1:kindCode>"+param.insuredEhmArray.get(i).itemKindEhmArray.get(j).kindCode+"</xsd1:kindCode>   \n" +
                        "            <xsd1:serialNo>"+param.insuredEhmArray.get(i).itemKindEhmArray.get(j).serialNo+"</xsd1:serialNo>  \n" +
                        "            <xsd1:Amount>"+param.insuredEhmArray.get(i).itemKindEhmArray.get(j).Amount+"</xsd1:Amount>  \n" +
                        "            <xsd1:Premium>"+param.insuredEhmArray.get(i).itemKindEhmArray.get(j).Premium+"</xsd1:Premium>  \n" +
                        "          </xsd1:itemKindEhmArray> \n" ;
            }

            a+="        <xsd:insuredEhmArray> \n" +
                    "          <xsd1:serialNo>"+param.insuredEhmArray.get(i).serialNo+"</xsd1:serialNo>    \n" +
                    "          <xsd1:insuredName>"+param.insuredEhmArray.get(i).insuredName+"</xsd1:insuredName>   \n" +
                    "          <xsd1:sex>"+param.insuredEhmArray.get(i).sex+"</xsd1:sex>    \n" +
                    "          <xsd1:age>"+param.insuredEhmArray.get(i).age+"</xsd1:age>    \n" +
                    "          <xsd1:birthday>"+param.insuredEhmArray.get(i).birthday+"</xsd1:birthday>   \n" +
                    "          <xsd1:insuredNature>"+param.insuredEhmArray.get(i).insuredNature+"</xsd1:insuredNature>   \n" +
                    "          <xsd1:identifyNumber>"+param.insuredEhmArray.get(i).identifyNumber+"</xsd1:identifyNumber>    \n" +
                    "          <xsd1:identifyType>"+param.insuredEhmArray.get(i).identifyType+"</xsd1:identifyType>   \n" +
                    "          <xsd1:applyNum>"+param.insuredEhmArray.get(i).applyNum+"</xsd1:applyNum>     \n" +
                    "          <xsd1:relation>"+param.insuredEhmArray.get(i).relation+"</xsd1:relation>     \n" +
                    "          <xsd1:occupationCode1>"+param.insuredEhmArray.get(i).occupationCode1+"</xsd1:occupationCode1>   \n" +
                    "          <xsd1:occupationCode2>"+param.insuredEhmArray.get(i).occupationCode2+"</xsd1:occupationCode2>   \n" +
                    "          <xsd1:occupationCode3>"+param.insuredEhmArray.get(i).occupationCode3+"</xsd1:occupationCode3>     \n" +
                    "          <xsd1:benefitFlag>"+param.insuredEhmArray.get(i).benefitFlag+"</xsd1:benefitFlag>     \n" +
                    "          <xsd1:appOccupationFlag>"+param.insuredEhmArray.get(i).appOccupationFlag+"</xsd1:appOccupationFlag>    \n" +
                    "          <xsd1:insuredCode/>  \n" +
                    "\t\t   \n" +
                    "          <xsd1:loanInfoEhm> \n" +
                    "            <xsd1:loanAmount>"+param.insuredEhmArray.get(i).loanInfoEhm.loanAmount+"</xsd1:loanAmount>   \n" +
                    "            <xsd1:loanBankCode>"+param.insuredEhmArray.get(i).loanInfoEhm.loanBankCode+"</xsd1:loanBankCode>    \n" +
                    "            <xsd1:loanContractNo>"+param.insuredEhmArray.get(i).loanInfoEhm.loanContractNo+"</xsd1:loanContractNo>    \n" +
                    "            <xsd1:loanEndDate>"+param.insuredEhmArray.get(i).loanInfoEhm.loanEndDate+"</xsd1:loanEndDate>   \n" +
                    "            <xsd1:loanNature>"+param.insuredEhmArray.get(i).loanInfoEhm.loanNature+"</xsd1:loanNature>     \n" +
                    "            <xsd1:loanStartDate>"+param.insuredEhmArray.get(i).loanInfoEhm.loanStartDate+"</xsd1:loanStartDate>    \n" +
                    "          </xsd1:loanInfoEhm>  \n" +
                    "\t\t    \n" +
                 b+
                    "        </xsd:insuredEhmArray>  \n";

        }

        String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.proposalsaveV4X.weijinsuo.frontend.sinosoft.com\" xmlns:xsd=\"http://dto.proposalsaveV4X.weijinsuo.frontend.sinosoft.com/xsd\" xmlns:xsd1=\"http://common.dto.proposalsaveV4X.weijinsuo.frontend.sinosoft.com/xsd\">  \n" +
                "  <soapenv:Header/>  \n" +
                "  <soapenv:Body> \n" +
                "    <ser:service> \n" +
                "      <ser:request> \n" +
                "        \n" +
                "        <xsd:applicantLoanEhm> \n" +
                "          <xsd1:appliName>"+param.applicantLoanEhm.appliName+"</xsd1:appliName>   \n" +
                "          <xsd1:insuredNature>"+param.applicantLoanEhm.insuredNature+"</xsd1:insuredNature>    \n" +
                "          <xsd1:identifyNumber>"+param.applicantLoanEhm.identifyNumber+"</xsd1:identifyNumber>     \n" +
                "          <xsd1:identifyType>"+param.applicantLoanEhm.identifyType+"</xsd1:identifyType>   \n" +
                "          <xsd1:email>"+param.applicantLoanEhm.email+"</xsd1:email>   \n" +
                "        </xsd:applicantLoanEhm>  \n" +
                "        <xsd:dealCode>"+param.dealCode+"</xsd:dealCode>   \n" +
                "         \n" +
                        a+
                "\t\t<xsd:mainEhm> \n" +
                "          <xsd1:dataSource>"+param.mainEhm.dataSource+"</xsd1:dataSource>   \n" +
                "          <xsd1:policyType>"+param.mainEhm.policyType+"</xsd1:policyType>    \n" +
                "          <xsd1:comCode>"+param.mainEhm.comCode+"</xsd1:comCode>    \n" +
                "          <xsd1:uuid>"+param.mainEhm.uuid+"</xsd1:uuid>   \n" +
                "          <xsd1:returnUrl>"+param.mainEhm.returnUrl+"</xsd1:returnUrl>   \n" +
                "          <xsd1:othOrderCode>"+param.mainEhm.othOrderCode+"</xsd1:othOrderCode>   \n" +
                "          <xsd1:sendTime>"+param.mainEhm.sendTime+"</xsd1:sendTime>    \n" +
                "          <xsd1:productCode>"+param.mainEhm.productCode+"</xsd1:productCode>    \n" +
                "          <xsd1:startDate>"+param.mainEhm.startDate+"</xsd1:startDate>   \n" +
                "          <xsd1:endDate>"+param.mainEhm.endDate+"</xsd1:endDate>   \n" +
                "          <xsd1:totalAmount>"+param.mainEhm.totalAmount+"</xsd1:totalAmount>   \n" +
                "          <xsd1:totalPremium>"+param.mainEhm.totalPremium+"</xsd1:totalPremium>   \n" +
                "        </xsd:mainEhm>  \n" +
                "        <xsd:password>"+param.password+"</xsd:password>  \n" +
                "        <xsd:requestType>"+param.requestType+"</xsd:requestType>     \n" +
                "        <xsd:systemCode>"+param.systemCode+"</xsd:systemCode>  \n" +
                "        <xsd:userName>"+param.userName+"</xsd:userName> \n" +
                "      </ser:request> \n" +
                "    </ser:service> \n" +
                "  </soapenv:Body> \n" +
                "</soapenv:Envelope>";

        return s;
    }

    /**
     * 作者: 李明
     * 描述: 强行截取xml字符串
     * 备注:  工具类
     * @return
     */
    public String getRes(String res,String key){
        String[] responseCodes = res.split(key);
        String responseCode = responseCodes[1];
        String[] split = responseCodes[1].split("</");
        return  split[0].replace(">","").replace("<","");
    }

    /**
     * 作者: 李明
     * 描述: JSON对象装换成XML
     * 备注:  工具类
     * @return
     */
    public String jsonToXml(BorrowParam param){

        String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.proposalsaveV4X.weijinsuo.frontend.sinosoft.com\" xmlns:xsd=\"http://dto.proposalsaveV4X.weijinsuo.frontend.sinosoft.com/xsd\" xmlns:xsd1=\"http://common.dto.proposalsaveV4X.weijinsuo.frontend.sinosoft.com/xsd\">  \n" +
                "  <soapenv:Header/>  \n" +
                "  <soapenv:Body> \n" +
                "    <ser:service> \n" +
                "      <ser:request> \n" +
                "        \n" +
                "        <xsd:applicantLoanEhm> \n" +
                "          <xsd1:appliName>"+param.applicantLoanEhm.appliName+"</xsd1:appliName>   \n" +
                "          <xsd1:insuredNature>"+param.applicantLoanEhm.insuredNature+"</xsd1:insuredNature>    \n" +
                "          <xsd1:identifyNumber>"+param.applicantLoanEhm.identifyNumber+"</xsd1:identifyNumber>     \n" +
                "          <xsd1:identifyType>"+param.applicantLoanEhm.identifyType+"</xsd1:identifyType>   \n" +
                "          <xsd1:email>"+param.applicantLoanEhm.email+"</xsd1:email>   \n" +
                "        </xsd:applicantLoanEhm>  \n" +
                "        <xsd:dealCode>"+param.dealCode+"</xsd:dealCode>   \n" +
                "         \n" +
                "        <xsd:insuredEhmArray> \n" +
                "          <xsd1:serialNo>"+param.insuredEhmArray.serialNo+"</xsd1:serialNo>    \n" +
                "          <xsd1:insuredName>"+param.insuredEhmArray.insuredName+"</xsd1:insuredName>   \n" +
                "          <xsd1:sex>"+param.insuredEhmArray.sex+"</xsd1:sex>    \n" +
                "          <xsd1:age>"+param.insuredEhmArray.age+"</xsd1:age>    \n" +
                "          <xsd1:birthday>"+param.insuredEhmArray.birthday+"</xsd1:birthday>   \n" +
                "          <xsd1:insuredNature>"+param.insuredEhmArray.insuredNature+"</xsd1:insuredNature>   \n" +
                "          <xsd1:identifyNumber>"+param.insuredEhmArray.identifyNumber+"</xsd1:identifyNumber>    \n" +
                "          <xsd1:identifyType>"+param.insuredEhmArray.identifyType+"</xsd1:identifyType>   \n" +
                "          <xsd1:applyNum>"+param.insuredEhmArray.applyNum+"</xsd1:applyNum>     \n" +
                "          <xsd1:relation>"+param.insuredEhmArray.relation+"</xsd1:relation>     \n" +
                "          <xsd1:occupationCode1>"+param.insuredEhmArray.occupationCode1+"</xsd1:occupationCode1>   \n" +
                "          <xsd1:occupationCode2>"+param.insuredEhmArray.occupationCode2+"</xsd1:occupationCode2>   \n" +
                "          <xsd1:occupationCode3>"+param.insuredEhmArray.occupationCode3+"</xsd1:occupationCode3>     \n" +
                "          <xsd1:benefitFlag>"+param.insuredEhmArray.benefitFlag+"</xsd1:benefitFlag>     \n" +
                "          <xsd1:appOccupationFlag>"+param.insuredEhmArray.appOccupationFlag+"</xsd1:appOccupationFlag>    \n" +
                "          <xsd1:insuredCode/>  \n" +
                "\t\t   \n" +
                "          <xsd1:loanInfoEhm> \n" +
                "            <xsd1:loanAmount>"+param.insuredEhmArray.loanInfoEhm.loanAmount+"</xsd1:loanAmount>   \n" +
                "            <xsd1:loanBankCode>"+param.insuredEhmArray.loanInfoEhm.loanBankCode+"</xsd1:loanBankCode>    \n" +
                "            <xsd1:loanContractNo>"+param.insuredEhmArray.loanInfoEhm.loanContractNo+"</xsd1:loanContractNo>    \n" +
                "            <xsd1:loanEndDate>"+param.insuredEhmArray.loanInfoEhm.loanEndDate+"</xsd1:loanEndDate>   \n" +
                "            <xsd1:loanNature>"+param.insuredEhmArray.loanInfoEhm.loanNature+"</xsd1:loanNature>     \n" +
                "            <xsd1:loanStartDate>"+param.insuredEhmArray.loanInfoEhm.loanStartDate+"</xsd1:loanStartDate>    \n" +
                "          </xsd1:loanInfoEhm>  \n" +
                "\t\t    \n" +
                "          <xsd1:itemKindEhmArray>  \n" +
                "            <xsd1:kindCode>"+param.insuredEhmArray.itemKindEhmArray.kindCode+"</xsd1:kindCode>   \n" +
                "            <xsd1:serialNo>"+param.insuredEhmArray.itemKindEhmArray.serialNo+"</xsd1:serialNo>  \n" +
                "            <xsd1:Amount>"+param.insuredEhmArray.itemKindEhmArray.Amount+"</xsd1:Amount>  \n" +
                "            <xsd1:Premium>"+param.insuredEhmArray.itemKindEhmArray.Premium+"</xsd1:Premium>  \n" +
                "          </xsd1:itemKindEhmArray> \n" +
                "        </xsd:insuredEhmArray>  \n" +
                "          \n" +
                "\t\t<xsd:mainEhm> \n" +
                "          <xsd1:dataSource>"+param.mainEhm.dataSource+"</xsd1:dataSource>   \n" +
                "          <xsd1:policyType>"+param.mainEhm.policyType+"</xsd1:policyType>    \n" +
                "          <xsd1:comCode>"+param.mainEhm.comCode+"</xsd1:comCode>    \n" +
                "          <xsd1:uuid>"+param.mainEhm.uuid+"</xsd1:uuid>   \n" +
                "          <xsd1:returnUrl>"+param.mainEhm.returnUrl+"</xsd1:returnUrl>   \n" +
                "          <xsd1:othOrderCode>"+param.mainEhm.othOrderCode+"</xsd1:othOrderCode>   \n" +
                "          <xsd1:sendTime>"+param.mainEhm.sendTime+"</xsd1:sendTime>    \n" +
                "          <xsd1:productCode>"+param.mainEhm.productCode+"</xsd1:productCode>    \n" +
                "          <xsd1:startDate>"+param.mainEhm.startDate+"</xsd1:startDate>   \n" +
                "          <xsd1:endDate>"+param.mainEhm.endDate+"</xsd1:endDate>   \n" +
                "          <xsd1:totalAmount>"+param.mainEhm.totalAmount+"</xsd1:totalAmount>   \n" +
                "          <xsd1:totalPremium>"+param.mainEhm.totalPremium+"</xsd1:totalPremium>   \n" +
                "        </xsd:mainEhm>  \n" +
                "        <xsd:password>"+param.password+"</xsd:password>  \n" +
                "        <xsd:requestType>"+param.requestType+"</xsd:requestType>     \n" +
                "        <xsd:systemCode>"+param.systemCode+"</xsd:systemCode>  \n" +
                "        <xsd:userName>"+param.userName+"</xsd:userName> \n" +
                "      </ser:request> \n" +
                "    </ser:service> \n" +
                "  </soapenv:Body> \n" +
                "</soapenv:Envelope>";

        return s;
    }

    /**
     * 作者: 李明
     * 描述: 影像上传接口
     * 备注:
     * @return
     */
    @ApiOperation(value = "影像上传接口",notes = "影像上传接口")
    @PostMapping(value = "/imageUploadRequest")
    public Object imageUploadRequest(HttpServletRequest request) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   影像上传接口】"+paramsStr);

        ResultApi<String> resultApi = validateRequest(request);
        if(resultApi.getResponseCode()==Code.FAIL){
            return resultApi;
        }
        String data = resultApi.getResponseData();
        JSONObject jsonObject = JSONObject.parseObject(data);

        Map<String,Object> map = new HashMap<>();

        String certiNo = jsonObject.getString("certiNo");
        String othOrderCode = jsonObject.getString("othOrderCode");
        String zipFileStream = jsonObject.getString("zipFileStream");
        ThirdOrder order = new ThirdOrder();
        order.setOthOrderCode(othOrderCode);
        order.setProposalNo(certiNo);
        ThirdOrder thirdOrder = thirdOrderMapper.selectOne(order);
        if(thirdOrder==null){
            return new ResultApi<String>(Code.FAIL,"上传失败!不存在的othOrderCode!",null);
        }
        if(StringUtil.isEmpty(zipFileStream)){
            return new ResultApi<String>(Code.FAIL,"上传失败!文件流不能为空",null);
        }

        thirdOrder.setFileContent(zipFileStream);
        thirdOrder.setStatus("1");
        thirdOrderMapper.updateById(thirdOrder);
        return new ResultApi<String>(Code.SUCCESS,"上传成功!",null);
    }


    /**
     * 作者: 李明
     * 描述: 收银台支付
     * 备注:
     * @return
     */
    @ApiOperation(value = "收银台支付",notes = "收银台支付")
    @PostMapping(value = "/policyPay")
    public Object policyPay(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> paramMap = RequestParams.getParams(request);
        String paramsStr = RequestParams.getOrderParamString(paramMap);
        logger.info("【易小宝科技API:   收银台支付】"+paramsStr);
        //String call_url = "http://testwebapp.ybw100.com/gspayres";
        //String insur_no = "927981851TM0000054282";//做投保单支付时必填
        String call_url = request.getParameter("call_url");
        String notify_url = request.getParameter("notify_url");
        String insur_no = request.getParameter("insur_no");
        String insur_flag =request.getParameter("insur_flag");//"0";//1:表示保单；0或者不填表示投保单(该域在表示投保单时可以不存    在)
        //查出当前保单
        EntityWrapper<ThirdOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("proposal_no",insur_no);
        ThirdOrder order = new ThirdOrder();
        order.setProposalNo(insur_no);

        ThirdOrder thirdOrder = thirdOrderMapper.selectOne(order);
        if(thirdOrder==null){
            logger.info("【易小宝科技API:   收银台支付】不存在的订单信息"+paramsStr);
            return "不存在的订单信息";
        }
        if("0".equals(thirdOrder.getStatus())){
            logger.info("请先上传影像文件"+paramsStr);
            return "请先上传影像文件";
        }
        thirdOrder.setNotifyUrl(notify_url);
        thirdOrder.setCallUrl(call_url);
        thirdOrderMapper.updateById(thirdOrder);
        //组装支付相关参数
        String othOrderCode = thirdOrder.getOthOrderCode();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        String startTime = format.replace(" ", "T");
        int totalamt = thirdOrder.getAmount().multiply(new BigDecimal("100")).intValue();
        int price = totalamt;//投保单金额，单位分 例如20101，即表示201.01元
        int trans_amt = totalamt;//汇总金额，单位分 例如20101，即表示201.01元
        String pid = othOrderCode;// "927981851TM0000054282";

        String eff_date =LocalDate.now().toString();//生效日期
        call_url = YxbConfig.getGsCallUrl();
        String sign_content = MD5Util.MD5(call_url+pid+trans_amt+YxbConfig.getGsMd5Key());

        String req = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
                "<bets><pubarea><pkg_id>"+othOrderCode+"</pkg_id>" +
                "<corp_code>yxbb</corp_code>" +
                "<channel_code>9999</channel_code>" +
                "<trans_type>100</trans_type>" +
                "<submit_time>"+startTime+"</submit_time>" +
                "</pubarea><body>" +
                "<totalnum>1</totalnum>" +
                "<totalamt>"+totalamt+"</totalamt>" +
                "<flag>1</flag><req_list>" +
                "<req_seq>1</req_seq>" +
                "<pid>"+pid+"</pid>" +
                "<call_url>"+call_url+"</call_url>" +
                "<insur_num>1</insur_num>" +
                "<insur_list><insur><insur_flag>"+insur_flag+"</insur_flag>" +
                "<insur_no>"+insur_no+"</insur_no>" +
                "<insur_name>易小保保单</insur_name>" +
                "<eff_date>"+eff_date+"</eff_date>" +
                "<price>"+price+"</price>" +
                "</insur></insur_list>" +
                "<cur_code>CNY</cur_code>" +
                "<trans_amt>"+trans_amt+"</trans_amt>" +
                "<trans_use/><trans_abstr/>" +
                "<reserved1/><reserved2/>" +
                "</req_list></body><signarea>" +
                "<sign_time>"+startTime+"</sign_time>" +
                "<sign_content>"+sign_content+"</sign_content>" +
                "</signarea></bets>";


        try{
            String reqDecode = URLEncoder.encode(req,"UTF-8");

            logger.info("【易小保科技  借款人接口 支付请求报文 明文】"+req);
            logger.info("【易小保科技  借款人接口 支付请求报文】"+reqDecode);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
            out.println(" <BODY>");
            String gsPayUrl =  YxbConfig.getGsPayUrl();
            out.println("<form name=\"submitForm\" action=\""+gsPayUrl+"\" method=\"post\">");
            out.println("<input type=\"hidden\"  id=\"cmd\" name=\"cmd\" value=\"toMCD\" />\n" +
                    "<input  type=\"hidden\" id=\"f\" name=\"f\" value=\"1\" />\n" +
                    "<input  type=\"hidden\" id=\"req\" name=\"req\" value=\""+reqDecode+"\" />\n" +
                    "<input type=\"hidden\" id=\"sys\" name=\"sys\" value=\"1\" />");
            out.println("</from>");
            out.println("<script>window.document.submitForm.submit();</script>");
            out.println(" </BODY>");
            out.println("</HTML>");
            out.flush();
            out.close();

            return null;
        }catch (Exception e){
            e.printStackTrace();
            logger.info("易安支付出现异常");
        }


        Map<String,Object> map = new HashMap<>();
        map.put("errorMessage","服务器异常");
        map.put("responseData",-1);
        return map;
    }


    /**
     * 作者: 李明
     * 描述: 支付结果通知
     * 备注:
     * @return
     */
    @ApiOperation(value = "支付结果通知",notes = "支付结果通知")
    @PostMapping(value = "/policyNotify")
    public Result<Boolean> policyNotify(@RequestParam(value = "req",required = false,defaultValue = "") String req) {

        logger.info("【易小保科技  借款人接口 支付回调请求】"+req);
        try{
           String s = req;//"%3C%3Fxml+version%3D%221.0%22+encoding%3D%22GBK%22%3F%3E%0D%0A%3Cbets%3E%0D%0A++%3Cpubarea%3E%0D%0A++++%3Cpkg_id%3E20181017150001542%3C%2Fpkg_id%3E%0D%0A++++%3Ccorp_code%3Eyxbb%3C%2Fcorp_code%3E%0D%0A++++%3Cchannel_code%3E9999%3C%2Fchannel_code%3E%0D%0A++++%3Ctrans_type%3E301%3C%2Ftrans_type%3E%0D%0A++++%3Csubmit_time%3E2018-10-17+15%3A00%3A01%3C%2Fsubmit_time%3E%0D%0A++%3C%2Fpubarea%3E%0D%0A++%3Cbody%3E%0D%0A++++%3Ctotalnum%3E1%3C%2Ftotalnum%3E%0D%0A++++%3Creq_list%3E%0D%0A++++++%3Creq_seq%3E1%3C%2Freq_seq%3E%0D%0A++++++%3Ctrans_id%3Egbyxbb20181017004690%3C%2Ftrans_id%3E%0D%0A++++++%3Cpid%3E152437247249%3C%2Fpid%3E%0D%0A++++++%3Cpkg_totalnum%3E1%3C%2Fpkg_totalnum%3E%0D%0A++++++%3Ctrans_amt%3E10000%3C%2Ftrans_amt%3E%0D%0A++++++%3Cresp_code%3E4%3C%2Fresp_code%3E%0D%0A++++++%3Cbankresp_code+%2F%3E%0D%0A++++++%3Cresp_desc%3E%E4%BA%A4%E6%98%93%E6%88%90%E5%8A%9F%3C%2Fresp_desc%3E%0D%0A++++++%3Cmerchant_no%3E00999901%3C%2Fmerchant_no%3E%0D%0A++++++%3Cterminal_no%3E0001%3C%2Fterminal_no%3E%0D%0A++++++%3Cchannel_code%3E04%3C%2Fchannel_code%3E%0D%0A++++++%3Ctrans_method%3E10%3C%2Ftrans_method%3E%0D%0A++++++%3Cconfirm_code%3E1%3C%2Fconfirm_code%3E%0D%0A++++++%3Cbatch_id+%2F%3E%0D%0A++++++%3Cinsur_list%3E%0D%0A++++++++%3Cinsur%3E%0D%0A++++++++++%3Cinsur_no%3E927981851TM0000054386%3C%2Finsur_no%3E%0D%0A++++++++++%3Cpolicy_no%3E827982018510175000003%3C%2Fpolicy_no%3E%0D%0A++++++++++%3Cinsur_sta+%2F%3E%0D%0A++++++++++%3Csta_msg+%2F%3E%0D%0A++++++++++%3Cissue_code+%2F%3E%0D%0A++++++++%3C%2Finsur%3E%0D%0A++++++%3C%2Finsur_list%3E%0D%0A++++%3C%2Freq_list%3E%0D%0A++%3C%2Fbody%3E%0D%0A++%3Csignarea%3E%0D%0A++++%3Csign_time%3E20181017150001542%3C%2Fsign_time%3E%0D%0A++++%3Csign_content%3E42A3AD294EB40EBCBD0931B063235285%3C%2Fsign_content%3E%0D%0A++%3C%2Fsignarea%3E%0D%0A%3C%2Fbets%3E%0D%0A%0D%0A";
           String decode = URLDecoder.decode(s, "UTF-8");
           decode = URLDecoder.decode(decode, "UTF-8");
            logger.info("【易小保科技  借款人接口 支付回调请求  明文】"+decode);
           //保存结果
           ThirdPayres res = new ThirdPayres();
           res.setId(StringUtil.getUuid());
           res.setCreateTime(YxbConstants.sysDate());
           res.setReq(decode);
           thirdPayresMapper.insert(res);
           //判断支付结果
           Map<String, Object> stringStringMap = XmlUtils.xmlToMap(decode);
           Map<String, Object> body = (Map<String, Object>) stringStringMap.get("body");

           Map<String, Object> req_list = (Map<String, Object>) body.get("req_list");


           Map<String, Object> pubarea = (Map<String, Object>) stringStringMap.get("pubarea");
           Object submit_time = pubarea.get("submit_time");
           Object resp_code = req_list.get("resp_code");
           if(resp_code==null){
               logger.info("响应为空"+req);
               return  new Result<>(Code.FAIL,"响应为空",false,Code.IS_ALERT_YES);
           }
           if("4".equals(resp_code.toString())){
               Map<String, Object> insur_list = (Map<String, Object>) req_list.get("insur_list");

               Object trans_id = req_list.get("trans_id");

               Map<String, Object> insur = (Map<String, Object>) insur_list.get("insur");
               Object insur_no = insur.get("insur_no");
               Object policy_no = insur.get("policy_no");
               ThirdOrder order = new ThirdOrder();
               order.setProposalNo(insur_no.toString());
               ThirdOrder thirdOrder = thirdOrderMapper.selectOne(order);
               if(thirdOrder==null){
                   logger.info("借款人意外险 无法查询  保单信息"+req);
                   //return  new Result<>(Code.FAIL,"借款人意外险 无法查询  保单信息",false,Code.IS_ALERT_YES);
               }else{
                   logger.info("借款人意外险 执行修改订单 和  执行回调"+req);
                   thirdOrder.setStatus("2");
                   thirdOrder.setPolicyNo(policy_no.toString());
                   thirdOrderMapper.updateById(thirdOrder);
                   final String notifyUrl = thirdOrder.getNotifyUrl();
                   final Map<String,Object> map = new HashMap<>();
                   map.put("insur_no",insur_no.toString());
                   map.put("policy_no",policy_no.toString());
                   map.put("othOrderCode",thirdOrder.getOthOrderCode());
                   new Thread(){
                       @Override
                       public void run() {
                           logger.info("启动线程  执行回调");
                           HttpUtil.doGet(notifyUrl,map,false);
                       }
                   }.start();
               }
               return  new Result<>(Code.SUCCESS,trans_id+","+submit_time.toString(),true,Code.IS_ALERT_NO);
           }else{
               logger.info("借款人意外险 支付错误"+req);
               return  new Result<>(Code.FAIL,"借款人意外险 支付错误",false,Code.IS_ALERT_YES);
           }
       }catch (Exception e){
           e.printStackTrace();
           logger.info("借款人意外险 支付解析 出现异常"+req);
           return  new Result<>(Code.FAIL,"借款人意外险 支付解析 出现异常",false,Code.IS_ALERT_YES);
       }
    };


    /**********************************************借款人意外险***********************************************/



}
