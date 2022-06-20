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

import com.pntmall.admin.domain.Privacy;
import com.pntmall.admin.domain.PrivacySearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.PrivacyService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/etc/privacy")
public class PrivacyController {
	public static final Logger logger = LoggerFactory.getLogger(PrivacyController.class);

    @Autowired
	private PrivacyService privacyService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute PrivacySearch privacySearch) {
		Utils.savePath("privacy");
		logger.debug("list", privacySearch);
		List<Privacy> list = privacyService.getList(privacySearch);
		Integer count = privacyService.getCount(privacySearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, privacySearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("privacySearch", privacySearch);

		return mav;
	}

	@GetMapping("/info")
	@ResponseBody
	public ResultMessage info(Integer privacyNo) {
		Privacy info = privacyService.getInfo(privacyNo);
		Param param = new Param();
		param.set("info", info);

		return new ResultMessage(true, "", param);
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer privacyNo) {
		String mode = "create";
		Privacy privacy = new Privacy();
		if(privacyNo != null && privacyNo > 0) {
			mode = "modify";
			privacy = privacyService.getInfo(privacyNo);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("privacy", privacy);
		mav.addObject("retrivePath", Utils.retrivePath("privacy"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Privacy privacy) {
		AdminSession sess = AdminSession.getInstance();

		try {
			privacy.setCuser(sess.getAdminNo());
			privacyService.create(privacy);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Privacy privacy, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			privacy.setCuser(sess.getAdminNo());
			privacyService.modify(privacy);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


}
