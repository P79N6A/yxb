package com.yxbkj.yxb.entity.module;

/**
 * ClassName：Code
 * Description：代码值
 * Author：李明
 * Created：2017/7/28
 */
public class Code {

    /**
     * 返回到前台的用户结果键值,此值表示成功
     */
    public static final int SUCCESS = 1;

    /**
     * 返回到前台的用户结果键值,此值表示失败
     */
    public static final int FAIL = 2;
    /**
     * 返回到前台的用户结果键值,此值表示失败
     */
    public static final int TOKEN_NOT_EXISTS = 3;

    /**
     * 返回到前台的用户结果键值,此值表示返回错误信息
     */
    public static final int ERROR = 3;

    /**
     * 是否弹出提示：不弹出
     */
    public static final int IS_ALERT_NO = 0;

    /**
     * 是否弹出提示：弹出
     */
    public static final int IS_ALERT_YES = 1;


    public static final int acct_less= 400;//资料不完善
    public static final int acct_new= 401;//新注册
    public static final int acct_wait_audit_= 402;//审核中
    public static final int acct_audit__passed = 403;//审核通过
    public static final int acct_audit__nopass = 404;//审核不通过
    public static final int acct_balance_not_enough = 500;//余额不足



}
