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

import com.pntmall.admin.domain.Code;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;

@Controller
@RequestMapping("/admin/code")
public class CodeController {
	public static final Logger logger = LoggerFactory.getLogger(CodeController.class);
	
    @Autowired
	private CodeService codeService;
	
	@GetMapping("/list1")
	public ModelAndView list1() {
		List<Code> list = codeService.getList1();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		return mav;
	}
	
	@GetMapping("/list2")
	public ModelAndView list2(String code1) {
		List<Code> list = codeService.getList2(code1);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		return mav;
	}
	
	@GetMapping("/info")
	@ResponseBody
	public ResultMessage info(Code code) {
		Code info = codeService.getInfo(code);
		Param param = new Param();
		param.set("info", info);
		
		return new ResultMessage(true, "", param);
	}	

	@PostMapping("/create1")
	@ResponseBody
	public ResultMessage create1(@ModelAttribute Code code) {
		AdminSession sess = AdminSession.getInstance();
		
		try {
			code.setCuser(sess.getAdminNo());
			codeService.create1(code);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/create2")
	@ResponseBody
	public ResultMessage create2(@ModelAttribute Code code) {
		AdminSession sess = AdminSession.getInstance();
		
		try {
			code.setCuser(sess.getAdminNo());
			codeService.create2(code);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Code code, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		
		try {
			code.setCuser(sess.getAdminNo());
			codeService.modify(code);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
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
			codeService.modifyRank(param.get("code1"), param.getValues("code2"), sess.getAdminNo());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
	
	@PostMapping("/remove")
	@ResponseBody
	public ResultMessage remove(@ModelAttribute Code code, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		
		try {
			code.setCuser(sess.getAdminNo());
			codeService.remove(code);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "삭제되었습니다.");
	}
	
}
