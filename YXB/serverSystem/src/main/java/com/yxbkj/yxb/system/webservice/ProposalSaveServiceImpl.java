package com.yxbkj.yxb.system.webservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxbkj.yxb.entity.app.Bank;
import com.yxbkj.yxb.system.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.List;

/**
 * webservice接口实现
 * 
 * 
 * @author：Liming
 * @date：2018年09月19日 下午9:37:20
 */
@WebService(serviceName = "ProposalSaveService", // 与接口中指定的name一致
targetNamespace = "http://service.yxb.com", // 与接口中的命名空间一致,一般是接口的包名倒
endpointInterface = "com.yxbkj.yxb.system.webservice.ProposalSaveService"// 接口地址
)
@Component
public class ProposalSaveServiceImpl implements ProposalSaveService {
    @Override
    public Result ProposalSave(
            MainEhm mainEhm
            , ApplicantLoanEhm applicantLoanEhm
            , List<InsuredEhm> insuredEhm,
            String dealCode, String password, String requestType, String systemCode, String userName
    ) {
        ProposalSaveResponse proposalSaveResponse = new ProposalSaveResponse();
        proposalSaveResponse.setMsg("投保异常!");
        Result res = new Result();
        res.setResponseCode("0");
        res.setErrorMessage("投保异常");
        return res;
    }
    @Override
    public PolicyDownLoadParamResponse PolicyDownLoad(PolicyDownLoadParam policyDownLoadParam) {
        PolicyDownLoadParamResponse policyDownLoadParamResponse = new PolicyDownLoadParamResponse();
        policyDownLoadParamResponse.setResponseCode("0");
        policyDownLoadParamResponse.setResponseCode("服务器异常");
        return policyDownLoadParamResponse;
    }
}
