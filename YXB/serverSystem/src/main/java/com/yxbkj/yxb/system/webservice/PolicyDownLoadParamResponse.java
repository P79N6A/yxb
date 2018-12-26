package com.yxbkj.yxb.system.webservice;

public class PolicyDownLoadParamResponse {
    private String RequestType;
    private String ResponseCode;
    private String ErrorMessage;
    private String PolicyNo;
    private String PdfInputStream;

    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
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

    public String getPolicyNo() {
        return PolicyNo;
    }

    public void setPolicyNo(String policyNo) {
        PolicyNo = policyNo;
    }

    public String getPdfInputStream() {
        return PdfInputStream;
    }

    public void setPdfInputStream(String pdfInputStream) {
        PdfInputStream = pdfInputStream;
    }
}
