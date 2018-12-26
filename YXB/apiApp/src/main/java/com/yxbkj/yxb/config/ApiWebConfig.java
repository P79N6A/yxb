package com.yxbkj.yxb.config;

import com.yxbkj.yxb.interceptor.ApiHandlerInterceptor;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * ClassName：ApiWebConfig
 * Description：API WEB MVC 配置
 * Author：李明
 * Created：2017/4/6
 */
@Configuration
public class ApiWebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    /**
     * 注册拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //加载api拦截器
        ApiHandlerInterceptor apiHandlerInterceptor = new ApiHandlerInterceptor();
        apiHandlerInterceptor.setRedisTemplateUtils(redisTemplateUtils);
        registry.addInterceptor(apiHandlerInterceptor)
                .excludePathPatterns("/error")
                .addPathPatterns("/**");
    }
    /**
     * 配置路径
     *
     * @param configurer
     */
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        configurer.setUseSuffixPatternMatch(false);
    }
}