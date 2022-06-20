package com.pntmall.admin.etc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pntmall.admin.domain.Admin;
import com.pntmall.common.utils.Utils;

/**
 * 어드민 로그인 정보 관리
 */
public class AdminSession {
    private static String SESSION_KEY = "_PNTMALL_ADMIN_SESSION_" + Utils.getActiveProfile().toUpperCase();

	private HttpSession session;
	private Admin admin;

    /**
     * 생성자
     *
     * @param request HttpServletRequest
     */
    private AdminSession(HttpServletRequest request) {
    	this.session = request.getSession();
    	admin = (Admin) session.getAttribute(AdminSession.SESSION_KEY);
    }

    /**
     * 현재 로그인 정보 인스턴스를 반환.
     *
     * @param request HttpServletRequest
     * @return AdminSession Instance
     */
    public static AdminSession getInstance(HttpServletRequest request) {
        return new AdminSession(request);
    }

    /**
     * 현재 로그인 정보 인스턴스를 반환.
     *
     * @return 현재 로그인 정보 인스턴스
     */
    public static AdminSession getInstance() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return getInstance(request);
    }
    
    /**
     * 로그인 수행
     *
     * @param admin 관리자 정보
     */
    public void login(Admin admin) {
    	session.setMaxInactiveInterval(30 * 60);
    	session.setAttribute(AdminSession.SESSION_KEY, admin);
    }

    /**
     * 로그아웃 수행
     */
    public void logout() {
        session.removeAttribute(AdminSession.SESSION_KEY);
        if(session != null) {
        	session.invalidate();
        }
    }

    /**
     * 로그인 여부를 반환
     *
     * @return 로그인 여부
     */
    public boolean isLogin() {
        if(admin != null && !"".equals(admin.getAdminId())) {
        	return true;
        } else {
        	return false;
        }
    }

    public Integer getAdminNo() {
    	if(isLogin()) {
    		return admin.getAdminNo();
    	} else {
    		return 0;
    	}
    }

    /**
     * adminId
     * @return adminId
     */
    public String getAdminId() {
    	if(isLogin()) {
    		return admin.getAdminId();
    	} else {
    		return "";
    	}
    }

    /**
     * name
     *
     * @return name
     */
    public String getName() {
    	if(isLogin()) {
    		return admin.getName();
    	} else {
    		return "";
    	}
    }
    
    public String getUpdateAuth() {
    	if(isLogin()) {
    		return admin.getUpdateAuth();
    	} else {
    		return "";
    	}
    }

}
