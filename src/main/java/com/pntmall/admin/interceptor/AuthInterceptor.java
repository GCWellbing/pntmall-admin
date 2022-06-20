package com.pntmall.admin.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Admin;
import com.pntmall.admin.domain.Menu;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.AdminService;
import com.pntmall.admin.service.MenuService;


public class AuthInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    LocaleResolver localeResolver;

	@Autowired
	private MenuService menuService;

	@Autowired
	private AdminService adminService;

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //HandlerMethod 가 아니면 controller 함수가 아니기 때문에 불필요.(return)
        if(handler instanceof HandlerMethod == false) {
            return true;
        }

        String uri = request.getRequestURI();
        if(uri.indexOf(".") == -1) {
	    	AdminSession sess = AdminSession.getInstance();
	    	if(!sess.isLogin()) {
	    		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	    			response.sendRedirect("/ajaxMsg?type=logout");
	    		} else {
	    			response.sendRedirect("/login");
	    		}
	    		return false;
	    	} else {
	    		if(!uri.equals("/index") && !uri.equals("/") && !uri.equals("/url")
	    				&& !uri.startsWith("/upload") && !uri.startsWith("/download")
	    				&& !uri.startsWith("/include") && !uri.startsWith("/popup")) {
	    			uri = uri.substring(0, uri.lastIndexOf("/")) + "/";
		    		List<Menu> menu = menuService.findMenuList(uri);

					if(menu.size() == 1) {
						request.setAttribute("menuNo", menu.get(0).getMenuNo() + "");
//						request.setAttribute("menuName", menu.get(0).getName());

						// 수정 권한
						Admin admin = adminService.getInfo(sess.getAdminNo());
						request.setAttribute("updateAuth", admin.getUpdateAuth());
					} else {
			    		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			    			response.sendRedirect("/ajaxMsg?type=noauth");
			    		} else {
			    			response.sendRedirect("/index");
			    		}
			    		return false;
					}
	    		}
	    		request.setAttribute("adminSession", sess);
	    	}
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
    }
}
