package com.yxbkj.yxb.system.webservice.insured;

import java.io.Serializable;

public class LoanInfoEhm  implements Serializable {
    private String LoanBankCode;
    private String LoanNature;
    private String LoanContractNo;
    private String LoanStartDate;
    private String LoanEndDate;
    private String LoanAmount;

    public String getLoanBankCode() {
        return LoanBankCode;
    }

    public void setLoanBankCode(String loanBankCode) {
        LoanBankCode = loanBankCode;
    }

    public String getLoanNature() {
        return LoanNature;
    }

    public void setLoanNature(String loanNature) {
        LoanNature = loanNature;
    }

    public String getLoanContractNo() {
        return LoanContractNo;
    }

    public void setLoanContractNo(String loanContractNo) {
        LoanContractNo = loanContractNo;
    }

    public String getLoanStartDate() {
        return LoanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        LoanStartDate = loanStartDate;
    }

    public String getLoanEndDate() {
        return LoanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        LoanEndDate = loanEndDate;
    }

    public String getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        LoanAmount = loanAmount;
    }
}
