package com.yxbkj.yxb.system.webservice;

public class Result {
    private String requestType;
    private String ResponseCode;
    private String ErrorMessage;
    private String SendTime;
    private String DealCode;
    private String UUID;
    private String ProposalNo;
    private String PolicyNo;
    private String PolicyURL;
    private String OthOrderCode;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public String getDealCode() {
        return DealCode;
    }

    public void setDealCode(String dealCode) {
        DealCode = dealCode;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getProposalNo() {
        return ProposalNo;
    }

    public void setProposalNo(String proposalNo) {
        ProposalNo = proposalNo;
    }

    public String getPolicyNo() {
        return PolicyNo;
    }

    public void setPolicyNo(String policyNo) {
        PolicyNo = policyNo;
    }

    public String getPolicyURL() {
        return PolicyURL;
    }

    public void setPolicyURL(String policyURL) {
        PolicyURL = policyURL;
    }

    public String getOthOrderCode() {
        return OthOrderCode;
    }

    public void setOthOrderCode(String othOrderCode) {
        OthOrderCode = othOrderCode;
    }
}
