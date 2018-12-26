package com.yxbkj.yxb.system.webservice;

public class PolicyDownLoadParam {
    private String UserCode;
    private String Password;
    private String RequestType;
    private String PolicyNo;
    private String RequestInfo1;
    private String RequestInfo2;

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    public String getPolicyNo() {
        return PolicyNo;
    }

    public void setPolicyNo(String policyNo) {
        PolicyNo = policyNo;
    }

    public String getRequestInfo1() {
        return RequestInfo1;
    }

    public void setRequestInfo1(String requestInfo1) {
        RequestInfo1 = requestInfo1;
    }

    public String getRequestInfo2() {
        return RequestInfo2;
    }

    public void setRequestInfo2(String requestInfo2) {
        RequestInfo2 = requestInfo2;
    }
}
