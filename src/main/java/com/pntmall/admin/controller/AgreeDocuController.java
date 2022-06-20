package com.pntmall.admin.controller;

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

import com.pntmall.admin.domain.AgreeDocu;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.AgreeDocuService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;

@Controller
@RequestMapping("/etc/agreeDocu")
public class AgreeDocuController {
	public static final Logger logger = LoggerFactory.getLogger(AgreeDocuController.class);

    @Autowired
	private AgreeDocuService agreeDoceService;


	@GetMapping("/edit")
	public ModelAndView edit(Integer privacyNo) {
		String mode = "create";
		AgreeDocu agreeDocu = agreeDoceService.getInfo();

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("agree", agreeDocu);

		return mav;
	}


	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute AgreeDocu agreeDocu, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();

		try {
			agreeDocu.setCuser(sess.getAdminNo());
			agreeDoceService.create(agreeDocu);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

}
