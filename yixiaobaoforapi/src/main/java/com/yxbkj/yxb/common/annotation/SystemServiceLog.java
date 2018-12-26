package com.yxbkj.yxb.common.annotation;


import java.lang.annotation.*;

/**
 * 
 * @Author Lin_J
 * @ClassName: SystemServiceLog 
 * @Description:  (service日志注解)
 * @date 2017年11月27日 下午4:05:46 
 *
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface SystemServiceLog {

    String description()  default "";


}