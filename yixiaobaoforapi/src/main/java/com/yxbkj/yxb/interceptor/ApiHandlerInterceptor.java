package com.yxbkj.yxb.interceptor;

import com.alibaba.fastjson.JSON;
import com.yxbkj.yxb.common.utils.Code;
import com.yxbkj.yxb.common.utils.Result;
import com.yxbkj.yxb.common.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName：ApiHandlerInterceptor
 * Description：API拦截器
 * Author：李明
 * Created：2017/7/24
 */
public class ApiHandlerInterceptor implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * controller 执行之前调用
     * 只有返回true才会继续向下执行，返回false取消当前请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");//解决跨域
        //测试阶段关闭签名
        if (!WebUtils.valiSignature(request)) {
//            Result result = new Result(Code.FAIL, "签名信息错误");
//            WebUtils.outPrint(response, JSON.toJSONString(result));
//            return false;
        }
        return true;
    }

    /**
     * controller 执行之后调用
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。
     * postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行，
     * 但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操作。
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //wangjx7.21 取消日志
//        if(handler instanceof HandlerMethod ) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//            long start_time = (Long) request.getAttribute("start_time");
//            long end_time = System.currentTimeMillis();
//            long consume_time = end_time - start_time;//消耗时间
//
//            logger.info("postHandle访问目标:" + method.getDeclaringClass().getName() + "." + method.getName() + "，消耗时间：" + consume_time + " ms");
//        }
//        logger.info("postHandle-response.getStatus()：" + response.getStatus());
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行,
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，这个方法的主要作用是用于清理资源的，
     * 当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //wangjx7.21 取消日志
//        logger.info("afterCompletion-response.getStatus()：" + response.getStatus());
//        if(handler instanceof HandlerMethod ) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//
//            logger.info("afterCompletion-getShortLogMessage：" + handlerMethod.getShortLogMessage());
//            logger.info("afterCompletion-访问目标：" + method.getDeclaringClass().getName() + "." + method.getName() + " 结束");
//        }

    }


}
