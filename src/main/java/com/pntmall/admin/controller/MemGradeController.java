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

import com.pntmall.admin.domain.MemGrade;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.MemGradeService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;

@Controller
@RequestMapping("/member/grade")
public class MemGradeController {
	public static final Logger logger = LoggerFactory.getLogger(MemGradeController.class);
	
    @Autowired
	private MemGradeService memGradeService;
	
	@GetMapping("/list")
	public ModelAndView list() {
		List<MemGrade> list = memGradeService.getList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		return mav;
	}
	
	@GetMapping("/info")
	@ResponseBody
	public ResultMessage info(Integer gradeNo) {
		MemGrade memGrade = memGradeService.getInfo(gradeNo);
		Param param = new Param();
		param.set("info", memGrade);
		
		return new ResultMessage(true, "", param);
	}	

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute MemGrade memGrade) {
		AdminSession sess = AdminSession.getInstance();
		
		try {
			memGrade.setCuser(sess.getAdminNo());
			memGradeService.create(memGrade);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute MemGrade memGrade, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		
		try {
			memGrade.setCuser(sess.getAdminNo());
			memGradeService.modify(memGrade);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
	
}
