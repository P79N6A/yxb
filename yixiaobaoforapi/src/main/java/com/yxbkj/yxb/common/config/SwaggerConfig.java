package com.yxbkj.yxb.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket dqkApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("易小保科技")
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
				.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select().apis(RequestHandlerSelectors.basePackage("com.yxbkj.yxb.controller"))
				// .paths(or(regex("/api/.*")))//过滤的接口
				.build().apiInfo(testApiInfo());
	}


	private ApiInfo testApiInfo() {
		ApiInfo apiInfo = new ApiInfo("易小保科技API", // 大标题
				"所有请求均需公共参数:signature(必传),timeStamp(时间戳非必传),signature值为加密后的字符串信息,加密方式 yxbkj的MD5值+所有参数拼接成字符(limit=1&page=1)的值 拼接后再次加密为签名参数传入", // 小标题
				"1.0", // 版本
				"NO terms of service", new Contact("易小保科技", "swagger-ui.html", ""), // 作者
				"友情链接", // 链接显示文字
				"https://www.baidu.com/"// 网站链接
		);
		return apiInfo;
	}
}
