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

import com.pntmall.admin.domain.Custom;
import com.pntmall.admin.domain.CustomProduct;
import com.pntmall.admin.domain.CustomSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CustomService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/content/custom")
public class CustomController {
	public static final Logger logger = LoggerFactory.getLogger(CustomController.class);

    @Autowired
	private CustomService customService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute CustomSearch customSearch) {
		Utils.savePath("custom");
		List<Custom> list = customService.getList(customSearch);
		Integer count = customService.getCount(customSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, customSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer cno) {
		String mode = "create";
		Custom custom = new Custom();
		List<CustomProduct> customProductList = new ArrayList<CustomProduct>();
		if(cno != null && cno > 0) {
			mode = "modify";
			custom = customService.getInfo(cno);
			customProductList = customService.getProductList(cno);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("custom", custom);
		mav.addObject("customProductList", customProductList);
		mav.addObject("retrivePath", Utils.retrivePath("custom"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Custom custom, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			custom.setCuser(sess.getAdminNo());
			customService.create(custom, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Custom custom, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			custom.setCuser(sess.getAdminNo());
			customService.modify(custom, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


}
