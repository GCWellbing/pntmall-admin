package com.pntmall.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pntmall.admin.domain.MemGrade;
import com.pntmall.admin.service.MemGradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.MonthlyRecom;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.MonthlyRecomService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;

@Controller
@RequestMapping("/content/monthlyRecom")
public class MonthlyRecomController {
	public static final Logger logger = LoggerFactory.getLogger(MonthlyRecomController.class);

    @Autowired
	private MonthlyRecomService monthlyRecomService;

	@Autowired
	private MemGradeService memGradeService;

	@GetMapping("/list")
	public ModelAndView list() {

		List<MonthlyRecom> list = monthlyRecomService.getList();
		List<MemGrade> memGradeList = memGradeService.getList();

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("memGradeList", memGradeList);

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(HttpServletRequest request) {
		logger.debug("MonthlyRecomController.java create");
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		MonthlyRecom monthlyRecom = new MonthlyRecom();

		try {
			monthlyRecom.setCuser(sess.getAdminNo());
			monthlyRecomService.create(monthlyRecom, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}




}
