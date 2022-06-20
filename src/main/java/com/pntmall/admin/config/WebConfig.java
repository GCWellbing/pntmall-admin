package com.pntmall.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.pntmall.admin.interceptor.AuthInterceptor;
import com.pntmall.admin.interceptor.CommonInterceptor;

@Configuration
@EnableWebMvc
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
@ComponentScan(basePackages = { "com.pntmall.common", "com.pntmall.admin" })
public class WebConfig implements WebMvcConfigurer {
	public static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Bean
	InternalResourceViewResolver internalResourceViewResolver () {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public CommonInterceptor commonInterceptor() {
		return new CommonInterceptor();
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(commonInterceptor())
				.excludePathPatterns("/static/**")
				.excludePathPatterns("/error/**")
				.excludePathPatterns("/assets/**");

		registry.addInterceptor(authInterceptor())
				.excludePathPatterns("/static/**")
				.excludePathPatterns("/error/**")
				.excludePathPatterns("/assets/**")
				.excludePathPatterns("/test/**")
				.excludePathPatterns("/template/**")
				.excludePathPatterns("/login")
				.excludePathPatterns("/loginProc")
				.excludePathPatterns("/logout")
				.excludePathPatterns("/ajaxMsg");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean xssEscapeServletFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssEscapeServletFilter());
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

	@Bean
	public static ConfigureRedisAction configureRedisAction() {
	    return ConfigureRedisAction.NO_OP;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/", "/static/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:index");
	}
}
