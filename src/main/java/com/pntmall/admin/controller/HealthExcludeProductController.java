package com.pntmall.admin.controller;

import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.HealthExample;
import com.pntmall.admin.domain.HealthQuestion;
import com.pntmall.admin.domain.HealthExcludeProduct;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.HealthQuestionService;
import com.pntmall.admin.service.HealthExcludeProductService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/health/product")
public class HealthExcludeProductController {
	public static final Logger logger = LoggerFactory.getLogger(HealthExcludeProductController.class);

	@Autowired
	private HealthExcludeProductService healthExcludeProductService;

	@Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list() {
		List<HealthExcludeProduct> list = healthExcludeProductService.getList();

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			healthExcludeProductService.create(param, sess.getAdminNo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}
}
