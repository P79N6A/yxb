package com.yxbkj.yxb.exception;
/**
 * @auther Lin_J
 * @className: (业务异常类)
 * @date 2018/06/04 15:09:19
 */
public class BusinessException extends BaseException{

    public BusinessException(String msg) {
        super(msg);
    }
    public BusinessException(String code, String msg) {
        super(code, msg);
    }
}
