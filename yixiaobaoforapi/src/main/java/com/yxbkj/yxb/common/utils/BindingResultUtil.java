package com.yxbkj.yxb.common.utils;

import com.yxbkj.yxb.common.entity.YxbConstants;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 校验结构处理工具类
 *
 * @author lideyang
 * @date 2018/7/18
 */
public class BindingResultUtil {
    private BindingResultUtil() {
    }

    /**
     * 获取字符串形式的校验错误信息
     *
     * @param bindingResult bindingResult
     * @return 校验错误信息字符串
     */
    public static String getErrorString(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError err : errors) {
                sb.append(err.getDefaultMessage());
                sb.append(";  ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * 获取校验结果
     *
     * @param bindingResult bindingResult
     * @param <T>           泛型
     * @return 校验结果
     */
    public static <T> Message<T> getMessage(BindingResult bindingResult) {
        Message<T> message = new Message<>();
        //设置输入参数错误
        if (bindingResult.hasErrors()) {
            String errorString = BindingResultUtil.getErrorString(bindingResult);
            message.setCode(YxbConstants.PARAMETER_ERROR_CODE);
            message.setMessage(errorString);
        }
        return message;
    }

}
