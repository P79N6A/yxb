package com.yxbkj.yxb.handler;

import com.alibaba.fastjson.JSON;
import com.yxbkj.yxb.common.utils.RequestParams;
import com.yxbkj.yxb.domain.model.ExceptionLog;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.sql.Timestamp;

/**
 * ClassName：ApiHandlerExceptionResolver
 * Description：API 异常统一处理
 * Author：李明
 * Created：2017/7/23
 */
@Component
public class ApiHandlerExceptionResolver implements HandlerExceptionResolver {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 异常处理
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ExceptionLog exceptionLog = new ExceptionLog();
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            exceptionLog.setClass_name(method.getDeclaringClass().getName());
            exceptionLog.setMethod_name(method.getName());
        }else{
            exceptionLog.setClass_name(request.getRequestURI());
        }
        exceptionLog.setApp_key(request.getParameter("app_key"));
        exceptionLog.setExp_msg(ExceptionUtils.getMessage(ex));
        exceptionLog.setRequest_param(JSON.toJSONString(RequestParams.getParams(request)));
        exceptionLog.setExp_time(new Timestamp(System.currentTimeMillis()));
        exceptionLog.setExp_stack(ExceptionUtils.getStackTrace(ex));
        logger.info("异常信息：" + exceptionLog.toString());
        return null;

    }

}
