package com.pntmall.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.common.type.Param;


public class CommonInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    @Autowired
    LocaleResolver localeResolver;

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //HandlerMethod 가 아니면 controller 함수가 아니기 때문에 불필요.(return)
        if(handler instanceof HandlerMethod == false) {
            return true;
        }

        String uri = request.getRequestURI();
        if(uri.indexOf(".") == -1) {
	    	logger.info(request.getRequestURI());
	    	Param param = new Param(request.getParameterMap());
	    	logger.info(param.toString());
        }
        
        request.setAttribute("DateFormat", "yyyy.MM.dd");
        request.setAttribute("TimeFormat", "HH:mm:ss");
        request.setAttribute("DateTimeFormat", "yyyy.MM.dd HH:mm:ss");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
    }
}
