package com.pntmall.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.AppPush;
import com.pntmall.admin.domain.AppPushSearch;
import com.pntmall.admin.domain.AppPushTarget;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.AppPushService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/etc/push")
public class AppPushController {
	public static final Logger logger = LoggerFactory.getLogger(AppPushController.class);
	
    @Autowired
	private AppPushService appPushService;
	
	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute AppPushSearch appPushSearch) {
		Utils.savePath();
		
		List<AppPush> list = appPushService.getList(appPushSearch);
		Integer count = appPushService.getCount(appPushSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, appPushSearch);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(Integer pno) {
		String mode = "create";
		AppPush appPush = new AppPush();

		ModelAndView mav = new ModelAndView();

		if(pno != null && pno > 0) {
			mode = "modify";
			appPush = appPushService.getInfo(pno);
		} else {
			pno = 0;
		}
		
		mav.addObject("retrivePath", Utils.retrivePath());
        mav.addObject("appPush", appPush);
		mav.addObject("mode", mode);
		
		if("modify".equals(mode) && appPush.getStatus() != 30) {
			int targetCount = 0;
			if(appPush.getTargetGubun() == 1) {
				targetCount = appPushService.getTargetCount1(appPush);
			} else {
				targetCount = appPushService.getTargetCount2(pno);
			}

			mav.addObject("targetCount", targetCount);
		}
		
		return mav;
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute AppPush appPush) {
		AdminSession sess = AdminSession.getInstance();

		try {
			appPush.setCuser(sess.getAdminNo());
			appPushService.create(appPush);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute AppPush appPush, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			appPush.setCuser(sess.getAdminNo());
			appPushService.modify(appPush);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/status")
	@ResponseBody
	public ResultMessage status(@ModelAttribute AppPush appPush, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AppPush info = appPushService.getInfo(appPush.getPno());

		if(info.getStatus() == 30) {
			return new ResultMessage(false, "상태를 변경할 수 없습니다.");
		}
		
		AdminSession sess = AdminSession.getInstance();

		try {
			appPush.setCuser(sess.getAdminNo());
			appPushService.modifyStatus(appPush);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "변경되었습니다.");
	}
	
	@GetMapping("/target")
	public ModelAndView target(@ModelAttribute AppPushTarget appPushTarget) {
		List<AppPushTarget> list = appPushService.getTargetList(appPushTarget);
		Integer count = appPushService.getTargetCount(appPushTarget);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, appPushTarget);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		
		return mav;
	}
	
	
}
