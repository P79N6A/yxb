package com.yxbkj.yxb.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 核保试算参数vo
 * </p>
 *
 * @author 李明
 * @since 2018-08-15
 */
public class CalculatePremiumParam implements Serializable  {
    public String token;
    public String productId  ;
    public String isShareCoverage  ;
    public String applyDate  ;
    public String premType  ;
    public List<Insurants> insurants;
    public static class Insurants implements Serializable {
        public String birthday  ;
        public String seqno  ;
        public String sex  ;
        public String hasSocialSecurity  ;
        public String relationshipWithPrimaryInsurant  ;public List<Coverages> coverages;
        public static class Coverages implements Serializable  {
            public String sumInsured  ;
            public String benLevel  ;
            public String period  ;
            public String periodDay  ;
        }
    }
}
