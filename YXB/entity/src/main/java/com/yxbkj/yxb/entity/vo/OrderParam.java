package com.yxbkj.yxb.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 订单参数vo
 * </p>
 *
 * @author 李明
 * @since 2018-08-15
 */
public class OrderParam  implements Serializable  {
    public String token;
    public String productId;
    public String productIdSelf;
    public String remark;
    public String orderSource;
    public String renewalPolicyNo  ;
    public String totalPremium  ;
    public String effDate  ;
    public String isNoticeConfirm  ;
    public String esaleSubSourceType  ;
    public String isShareCoverage  ;
    public String applyDate  ;
    public String outChannelOrderId  ;
    public  ApplyOrg applyOrg;
    public static class ApplyOrg implements Serializable {
        public String address  ;
        public String cityName  ;
        public String areaName  ;
        public String organizationCode  ;
        public String isSendMail  ;
        public String name  ;
        public String orgCodeEffDate  ;
        public String industryCode  ;
        public String orgCodeType  ;
        public  ImUploads imUploads;
        public static class ImUploads implements Serializable  {
            public String imUploadFlag  ;
            public String imUploadType  ;
            public String imUploadFileId  ;
        }
        public  OrgContactPerson orgContactPerson;
        public static class OrgContactPerson implements Serializable  {
            public String certificateTypeCode  ;
            public String birthday  ;
            public String sex  ;
            public String name  ;
            public String certificateNo  ;
            public String phoneNo  ;
            public String email  ;
        }
    }
    public  ChannelInfo channelInfo;
    public static class ChannelInfo implements Serializable  {
        public String sellerCode  ;
        public String sellerName  ;

    }public List<Insurants> insurants;
    public static class Insurants implements Serializable  {
        public String birthday  ;
        public String isRenewal  ;
        public String socialSecurityLocation  ;
        public String idType  ;
        public String seqno  ;
        public String sex  ;
        public String idno  ;
        public String relationshipWithApplicant  ;
        public String uwMedicalId  ;
        public String name  ;
        public String relationshipWithPrimaryInsurant  ;
        public String socialSecurity  ;
        public  ContactInfo contactInfo;
        public static class ContactInfo implements Serializable  {
            public String mobile  ;
            public String email  ;
        }
        public  Coverages coverages;
        public static class Coverages implements Serializable  {
            public String period  ;
            public String actualPrem  ;
            public String planType  ;
            public String sumInsured  ;
            public String benLevel  ;
            public String paymentPeriodDay  ;
            public String paymentPeriod  ;
            public String periodDay  ;
        }
        public  HealthNotes healthNotes;
        public static class HealthNotes implements Serializable  {
            public String questionId  ;
            public String healthNoteSeq  ;
            public String answer  ;
            public String description  ;
        }
    }
    public  Applicant applicant;
    public static class Applicant implements Serializable  {
        public String birthday  ;
        public String idType  ;
        public String sex  ;
        public String name  ;
        public String idno  ;
        public  ContactInfo contactInfo;
        public static class ContactInfo implements Serializable  {
            public String communicationNo  ;
            public String mobile  ;
            public String email  ;
        }
    }
    public  AuthInfo authInfo;
    public static class AuthInfo implements Serializable  {
        public String initialChargeMode  ;
    }
    public  ServiceAgreementInfo serviceAgreementInfo;
    public static class ServiceAgreementInfo implements Serializable  {
        public String premType  ;
    }
}