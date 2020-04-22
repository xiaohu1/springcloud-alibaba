package com.test.authServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author liujian
 * @ProjectName tiger-springcloud
 * @date 2019/7/11  11:36
 */
@Configuration
@EnableWebMvc
public class AuthWebMvcConfig extends WebMvcConfigurerAdapter {

//	@Resource
//	private TokenInterceptor vueViewInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/");
	}

	/**
	 * Add interceptors.
	 *
	 * @param registry the registry
	 */
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		super.addInterceptors(registry);
//		registry
//				.addInterceptor(vueViewInterceptor)
//				.addPathPatterns("/**")
//				.excludePathPatterns("/pay/alipayCallback", "/swagger-resources/**", "*.js", "/**/*.js", "*.css", "/**/*.css", "*.html", "/**/*.html");
//	}


}
