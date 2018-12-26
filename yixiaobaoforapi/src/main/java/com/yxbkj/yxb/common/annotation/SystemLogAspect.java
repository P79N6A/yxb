package com.yxbkj.yxb.common.annotation;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.baomidou.mybatisplus.toolkit.SystemClock;
import com.yxbkj.yxb.common.utils.IpUtil;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Author Lin_J
 * @ClassName: SystemLogAspect 
 * @Description:  (切面)
 * @date 2017年10月17日 下午12:00:28 
 *
 */
@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SystemLogAspect {
    // 注入Service用于把日志保存数据库
   /* @Autowired
    private SystemLogService logService;*/

    private static Map<String, String> paramsMap=null;

    static {
        paramsMap=new HashMap<>();
    }

    // 本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    public SystemLogAspect(){
    }

    // Service层切点
    @Pointcut("@annotation(com.yxbkj.yxb.common.annotation.SystemServiceLog)")
    public  void serviceAspect() {
    }

    // Controller层切点
    @Pointcut("@annotation(com.yxbkj.yxb.common.annotation.SystemControllerLog)")
    public  void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Around("controllerAspect()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        return null;
    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // 读取session中的用户
        //Principal principal=(Principal) session.getAttribute(SessionUtil.USER_SESSION);
        // 请求的IP
        String ip = IpUtil.getAddrIP(request);
        Object[] params = joinPoint.getArgs();//获取方法参数
        String content= Arrays.toString(params);
        if(content.length()>3000){
            content=content.substring(0,2999);
        }
        StringBuilder sb=new StringBuilder();
        try {
            /*========控制台输出=========*/
            String custName="匿名用户";

            logger.info("=====异常通知开始=====");
            sb.append("异常代码: ").append(e.getClass().getName());
            logger.info(sb.toString());
            sb.setLength(0);
            sb.append("异常信息: ").append(e.getMessage());
            logger.info(sb.toString());
            sb.setLength(0);
            sb.append("异常方法: ").append(joinPoint.getTarget().getClass().getName()).append(".").append(joinPoint.getSignature().getName());
            logger.info(sb.toString());
            sb.setLength(0);
            sb.append("方法描述: ").append(getServiceMthodDescription(joinPoint));
            logger.info(sb.toString());
            logger.info("请求人："+custName);
            logger.info("请求IP:"+ip);
            logger.debug("请求参数:"+content);
            /*==========数据库日志=========*/
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            Browser browser = userAgent.getBrowser();
            OperatingSystem os = userAgent.getOperatingSystem();
            sb.setLength(0);
            sb.append(browser.getName()).append("===").append(browser.getBrowserType()).append("===").append(browser);
            logger.info(sb.toString());
            logger.info("os: "+os);
            // 请求方法
            sb.setLength(0);
            sb.append(joinPoint.getTarget().getClass().getName()).append(".").append(joinPoint.getSignature().getName()).append("()");
            // 保存数据库

            logger.info("=====异常通知结束=====");
        }  catch (Exception ex) {
            // 记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());
        }
         /*==========记录本地异常日志==========*/
         sb.setLength(0);
         sb.append("异常方法：").append(joinPoint.getTarget().getClass().getName())
                 .append(joinPoint.getSignature().getName()).append("异常信息：")
                 .append("异常代码：").append(e.getClass().getName())
                 .append(e.getMessage()).append("参数：").append(params);
        logger.debug(sb.toString());
    }


    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemServiceLog. class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}