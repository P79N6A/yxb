package com.yxbkj.yxb.system.webservice.insured;

import java.io.Serializable;

public class LimitEhm  implements Serializable {
    private String LimitCode;
    private String InsureArea;

    public String getLimitCode() {
        return LimitCode;
    }

    public void setLimitCode(String limitCode) {
        LimitCode = limitCode;
    }

    public String getInsureArea() {
        return InsureArea;
    }

    public void setInsureArea(String insureArea) {
        InsureArea = insureArea;
    }
}
