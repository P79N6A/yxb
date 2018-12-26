package com.yxbkj.yxb.system.webservice;

import com.yxbkj.yxb.entity.app.Bank;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

/**
 * webservice接口
 * 
 *
 * @author：Liming
 * @date：2018年09月19日 下午9:37:20
 */
@WebService(name = "ProposalSaveService", // 暴露服务名称
targetNamespace = "http://service.yxb.com"// 命名空间,一般是接口的包名倒序
)
public interface ProposalSaveService {

    /**
     * * 投保
     * @param mainEhm
     * @param applicantLoanEhm
     * @return
     */
    @WebMethod
    @WebResult(name = "proposalSave", targetNamespace = "")
    Result ProposalSave(
            @WebParam(name = "mainEhm") MainEhm mainEhm
            ,@WebParam(name = "applicantLoanEhm") ApplicantLoanEhm applicantLoanEhm
            ,@WebParam(name = "insuredEhm") List<InsuredEhm> insuredEhm
            ,@WebParam(name = "dealCode") String dealCode
            ,@WebParam(name = "password") String password
            ,@WebParam(name = "requestType") String requestType
            ,@WebParam(name = "systemCode") String systemCode
            ,@WebParam(name = "userName") String userName
    );
    /**
     * 保单下载
     * @param policyDownLoadParam
     * @return
     */
    @WebMethod
    @WebResult(name = "PolicyDownLoadParam", targetNamespace = "")
    PolicyDownLoadParamResponse PolicyDownLoad(@WebParam(name = "policyDownLoadParam") PolicyDownLoadParam policyDownLoadParam);





}