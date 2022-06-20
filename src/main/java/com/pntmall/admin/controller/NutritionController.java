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
import com.pntmall.admin.domain.Nutrition;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.NutritionService;
import com.pntmall.common.ResultMessage;

@Controller
@RequestMapping("/product/nutrition")
public class NutritionController {
	public static final Logger logger = LoggerFactory.getLogger(NutritionController.class);
	
    @Autowired
	private NutritionService nutritionService;
	
    @Autowired
	private CodeService codeService;
	
	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute Nutrition nutrition) {
		List<Nutrition> list = nutritionService.getList(nutrition);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(Integer nutritionNo) {
		String mode = "create";
		Nutrition nutrition = new Nutrition();
		if(nutritionNo != null && nutritionNo > 0) {
			mode = "modify";
			nutrition = nutritionService.getInfo(nutritionNo);
		}
		List<Code> unitList = codeService.getList2("002");

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("nutrition", nutrition);
		mav.addObject("unitList", unitList);

		return mav;
	}	

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Nutrition nutrition) {
		AdminSession sess = AdminSession.getInstance();
		
		try {
			nutrition.setCuser(sess.getAdminNo());
			nutritionService.create(nutrition);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Nutrition nutrition, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		
		try {
			nutrition.setCuser(sess.getAdminNo());
			nutritionService.modify(nutrition);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
	
	@PostMapping("/remove")
	@ResponseBody
	public ResultMessage remove(@ModelAttribute Nutrition nutrition, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		
		try {
			nutrition.setCuser(sess.getAdminNo());
			nutritionService.remove(nutrition);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "삭제되었습니다.");
	}
	
}
