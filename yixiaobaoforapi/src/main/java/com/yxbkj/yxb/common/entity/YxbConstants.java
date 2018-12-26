package com.yxbkj.yxb.common.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YxbConstants implements Serializable {
    /**
     * 系统名称
     */
    public static final String SYSTEM_NAME = "易小保";
    /**
     * 系统异常状态码
     */
    public static final String SYSTEM_ERROR_CODE = "500";
    /**
     * 数据有效性 正常
     */
    public static final String DATA_NORMAL_STATUS_CODE = "10000001";
    /**
     * 数据有效性 无效
     */
    public static final String DATA_DELETE_STATUS_CODE = "10000002";


    public static String sysDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 分页最大页码
     */
    public static final int MAX_PAGE_CURRENT = 100;
    /**
     * 分页每页最大数据量限制
     */
    public static final int MAX_PAGE_LIMIT = 100;
    /**
     * 会员注册情况直线图统计周数
     */
    public static final int CHART_LINE_WEEK_COUNT = 12;

    /**
     * 请求正常码
     */
    public static final String OK_CODE = "200";
    /**
     * 请求参数错误码
     */
    public static final String PARAMETER_ERROR_CODE = "400";
    /**
     * 禁止服务错误码
     */
    public static final String FORBIDEN_CODE = "403";
    /**
     * 首选卡
     */
    public static final String CARD_PREFERRED = "10000037";
    /**
     * 未首选卡
     */
    public static final String CARD_NOT_PREFERRED = "10000038";
    /**
     * 提现状态
     */
    public static final String PAY_STATUS_TYPE = "PAY_STATUS_TYPE";
    /**
     * 用户来源
     */
    public static final String SOURCE_TYPE = "SOURCE_TYPE";
    /**
     * 所属银行
     */
    public static final String BANK_CODE = "bankCode";
    /**
     * 支付方式
     */
    public static final String PAY_TYPE = "PAY_TYPE";
    /**
     * 默认的申请审核人
     */
    public static final String DEFAULT_AUDIT_USER = "chaojiyonghu";

}
