package com.pntmall.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.pntmall.admin.domain.Admin;
import com.pntmall.admin.domain.AdminLog;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.AdminService;
import com.pntmall.admin.service.MenuService;
import com.pntmall.admin.service.SeqService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.SecurityUtils;

@Controller
public class IndexController {
    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private SeqService seqService;
    
    @Autowired
    private SecurityUtils securityUtils;

//    @RequestMapping("/{variable:(?!static).*}/**")
    @RequestMapping("/**/{variable:[a-zA-Z0-9\\-_]*}")
    public String resolve(HttpServletRequest request) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String uri = urlPathHelper.getRequestUri(request);
		uri = uri.substring(1);
		logger.debug("--- **/* :" + uri);
		return uri;
    }
    
    @RequestMapping("/template/test")
    public ModelAndView test() {
    	ModelAndView mav = new ModelAndView();
    	String id = seqService.getId();

    	mav.addObject("system", System.getProperty("spring.profiles.active"));
    	mav.addObject("id", id);
    	mav.addObject("cache", seqService.test());
    	mav.addObject("now", seqService.test2());
    	return mav;
    }
    
    @RequestMapping("/login")
    public ModelAndView login() {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("system", System.getProperty("spring.profiles.active"));
    	return mav;
    }
    
    @PostMapping("/loginProc")
    @ResponseBody
    public ResultMessage loginProc(@ModelAttribute Admin admin) throws Exception {
		String adminId = admin.getAdminId();
		String passwd = admin.getPasswd();
		
		Admin info = adminService.getInfo(adminId);
		
		if(info == null || "".equals(info.getAdminId()) || !"S".equals(info.getStatus())) {
			return new ResultMessage(false, "로그인 정보가 정확하지 않습니다.");
		} else if(info.getLoginFailCnt() >= 5) {
			return new ResultMessage(false, "잠긴 계정입니다. 관리자에 문의하세요.");
		} else {
			String enc = securityUtils.encodeSHA512(passwd);
			logger.debug(String.format("-------- %s", enc));
			
			if(!enc.equals(info.getPasswd())) {
				AdminLog adminLog = new AdminLog();
				adminLog.setAdminNo(info.getAdminNo());
				adminLog.setIp(HttpRequestHelper.getRequestIp());
				adminLog.setSuccessYn("N");
				adminService.createAccessLog(adminLog);
				
				admin.setLoginFailCnt(info.getLoginFailCnt() + 1);
				adminService.modifyFailCnt(admin);
				
				return new ResultMessage(false, "로그인 정보가 정확하지 않습니다.");
			} else {
				AdminSession adminSession = AdminSession.getInstance();
				adminSession.login(info);

				AdminLog adminLog = new AdminLog();
				adminLog.setAdminNo(info.getAdminNo());
				adminLog.setIp(HttpRequestHelper.getRequestIp());
				adminLog.setSuccessYn("Y");
				adminService.createAccessLog(adminLog);
				
				admin.setLoginFailCnt(0);
				adminService.modifyFailCnt(admin);

				return new ResultMessage(true, "");
			}
		}    	
    }

    @RequestMapping("/logout")
    public String logout() {
    	AdminSession adminSession = AdminSession.getInstance();
    	adminSession.logout();
    	
    	return "redirect:/login";
    }

	@GetMapping("/url")
    public String url(@RequestParam("menuNo") int menuNo) throws IOException {
    	AdminSession sess = AdminSession.getInstance();
    	
    	if(sess.isLogin()) {
    		String url = menuService.getUrl(menuNo, sess.getAdminNo());
    		return "redirect:" + url;
    	} else {
    		return "redirect:/login";
    	}
    }

	@RequestMapping("/ajaxMsg")
    @ResponseBody
    public ResultMessage ajaxMsg(String type) {
    	if("logout".equals(type)) {
    		return new ResultMessage(false, "자동 로그아웃되었습니다.\n다시 로그인하세요.");
    	} else if("noauth".equals(type)) {
        	return new ResultMessage(false, "잘못된 접근입니다.");
    	} else {
    		return new ResultMessage(false, "알수 없는 에러입니다.\n다시 로그인하세요.");
    	}
    }
}
