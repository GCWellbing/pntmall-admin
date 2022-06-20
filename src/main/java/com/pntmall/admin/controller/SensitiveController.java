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

import com.pntmall.admin.domain.Sensitive;
import com.pntmall.admin.domain.SensitiveSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.SensitiveService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/etc/sensitive")
public class SensitiveController {
	public static final Logger logger = LoggerFactory.getLogger(SensitiveController.class);

    @Autowired
	private SensitiveService sensitiveService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute SensitiveSearch sensitiveSearch) {
		Utils.savePath("sensitive");
		logger.debug("list", sensitiveSearch);
		List<Sensitive> list = sensitiveService.getList(sensitiveSearch);
		Integer count = sensitiveService.getCount(sensitiveSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, sensitiveSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("sensitiveSearch", sensitiveSearch);

		return mav;
	}

	@GetMapping("/info")
	@ResponseBody
	public ResultMessage info(Integer sensitiveNo) {
		Sensitive info = sensitiveService.getInfo(sensitiveNo);
		Param param = new Param();
		param.set("info", info);

		return new ResultMessage(true, "", param);
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer sensitiveNo) {
		String mode = "create";
		Sensitive sensitive = new Sensitive();
		if(sensitiveNo != null && sensitiveNo > 0) {
			mode = "modify";
			sensitive = sensitiveService.getInfo(sensitiveNo);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("sensitive", sensitive);
		mav.addObject("retrivePath", Utils.retrivePath("sensitive"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Sensitive sensitive) {
		AdminSession sess = AdminSession.getInstance();

		try {
			sensitive.setCuser(sess.getAdminNo());
			sensitiveService.create(sensitive);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Sensitive sensitive, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			sensitive.setCuser(sess.getAdminNo());
			sensitiveService.modify(sensitive);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


}
