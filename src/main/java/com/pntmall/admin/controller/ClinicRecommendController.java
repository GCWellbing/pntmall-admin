package com.pntmall.admin.controller;

import java.util.ArrayList;
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

import com.pntmall.admin.domain.Clinic;
import com.pntmall.admin.domain.ClinicImg;
import com.pntmall.admin.domain.ClinicJoin;
import com.pntmall.admin.domain.ClinicSearch;
import com.pntmall.admin.domain.Code;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.ClinicService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/clinic/recommend")
public class ClinicRecommendController {
	public static final Logger logger = LoggerFactory.getLogger(ClinicRecommendController.class);

    @Autowired
	private ClinicService clinicService;

    @Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute ClinicSearch clinicSearch) {
		Utils.savePath("clinic");
		logger.debug("list", clinicSearch);

		List<Clinic> list = clinicService.getRecommendList(clinicSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);

		return mav;
	}


}
