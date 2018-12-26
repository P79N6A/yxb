package com.yxbkj.yxb.common.annotation;

import com.alibaba.fastjson.JSONObject;

import com.yxbkj.yxb.common.entity.ResultPage;
import com.yxbkj.yxb.common.utils.AES;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApiAesAspect {

	private static final Logger logger = LoggerFactory.getLogger(ApiAesAspect.class);

	/*@Autowired
	SystemParamsService paramsService;
*/
	// Controller层切点
	@Pointcut("@annotation(com.yxbkj.yxb.common.annotation.ApiDeal)")
	public void apiControllerAspect() {

	}

	@Around("apiControllerAspect()")
	public Object doAfter(ProceedingJoinPoint joinPoint) throws Throwable {
		Object proceed = joinPoint.proceed();
		if(proceed instanceof ResultPage){
			ResultPage page= (ResultPage) proceed;
			logger.info(">>>>>>>>>>>>>>>>>>>>加密前："+page.toString());
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String headerVal=request.getHeader("Authorization");
			JSONObject object = JSONObject.parseObject(headerVal);
			String appid=object.getString("appid");
			/*Map<String, String> param = paramsService.selectParamsByKeys(appid);*/
			Map<String, String> param =null;
			if(!CollectionUtils.isEmpty(param)){
				String appsecretkey=param.get(appid);
				/*param=paramsService.selectParamsByKeys(appsecretkey);*/
				param=null;
				if(!CollectionUtils.isEmpty(param)){
					String secret = param.get(appsecretkey);
					// 加密返回的结果
					if(null!=page.getData()){
						page.setData(AES.aesEncryptByCBC(JSONObject.toJSONString(page.getData()), appid, secret));
						proceed=page;
						logger.info(">>>>>>>>>>>>>>>>>>>>加密成功！");
					}
				}
			}
			logger.info(">>>>>>>>>>>>>>>>>>>>加密后："+page.toString());
		}
		return proceed;
	}
}
