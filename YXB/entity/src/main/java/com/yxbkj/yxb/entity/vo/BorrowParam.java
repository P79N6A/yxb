package com.yxbkj.yxb.entity.vo;

import java.io.Serializable;

public class BorrowParam implements Serializable  {
    public String password  ;
    public String requestType  ;
    public String systemCode  ;
    public String dealCode  ;
    public String userName  ;
    public  ApplicantLoanEhm applicantLoanEhm;
    public static class ApplicantLoanEhm implements Serializable  {
        public String identifyNumber  ;
        public String insuredNature  ;
        public String appliName  ;
        public String identifyType  ;
        public String email  ;

    }
    public  MainEhm mainEhm;
    public static class MainEhm implements Serializable {

        public String totalAmount  ;
        public String productCode  ;
        public String endDate  ;
        public String policyType  ;
        public String othOrderCode  ;
        public String totalPremium  ;
        public String comCode  ;
        public String returnUrl  ;
        public String dataSource  ;
        public String uuid  ;
        public String startDate  ;
        public String sendTime  ;

    }
    public  InsuredEhmArray insuredEhmArray;
    public static class InsuredEhmArray implements Serializable  {

        public String birthday  ;
        public String sex  ;
        public String occupationCode1  ;
        public String occupationCode2  ;
        public String occupationCode3  ;
        public String applyNum  ;
        public String identifyType  ;
        public String relation  ;
        public String serialNo  ;
        public String benefitFlag  ;
        public String identifyNumber  ;
        public String insuredNature  ;
        public String appOccupationFlag  ;
        public String insuredName  ;
        public String age  ;
        public  LoanInfoEhm loanInfoEhm;
        public static class LoanInfoEhm implements Serializable  {

            public String loanBankCode  ;
            public String loanStartDate  ;
            public String loanNature  ;
            public String loanAmount  ;
            public String loanEndDate  ;
            public String loanContractNo  ;

        }
        public  ItemKindEhmArray itemKindEhmArray;
        public static class ItemKindEhmArray implements Serializable  {

            public String kindCode  ;
            public String Amount  ;
            public String Premium  ;
            public String serialNo  ;

        }

    }


}
