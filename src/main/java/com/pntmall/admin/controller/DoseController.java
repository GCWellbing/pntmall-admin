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

import com.pntmall.admin.domain.Dose;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.DoseService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;

@Controller
@RequestMapping("/product/dose")
public class DoseController {
	public static final Logger logger = LoggerFactory.getLogger(DoseController.class);
	
    @Autowired
	private DoseService doseService;
	
	@GetMapping("/list")
	public ModelAndView list() {
		List<Dose> list = doseService.getList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		return mav;
	}
	
	@GetMapping("/info")
	@ResponseBody
	public ResultMessage info(Integer doseNo) {
		Dose info = doseService.getInfo(doseNo);
		Param param = new Param();
		param.set("info", info);
		
		return new ResultMessage(true, "", param);
	}	

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Dose dose) {
		AdminSession sess = AdminSession.getInstance();
		
		try {
			dose.setCuser(sess.getAdminNo());
			doseService.create(dose);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Dose dose, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		
		try {
			dose.setCuser(sess.getAdminNo());
			doseService.modify(dose);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
	
	@PostMapping("/remove")
	@ResponseBody
	public ResultMessage remove(@ModelAttribute Dose dose, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		
		try {
			dose.setCuser(sess.getAdminNo());
			doseService.remove(dose);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "삭제되었습니다.");
	}
	
	@PostMapping("/modifyRank")
	@ResponseBody
	public ResultMessage modifyRank(HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			doseService.modifyRank(param.getValues("doseNo"), sess.getAdminNo());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
	
}
