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

import com.pntmall.admin.domain.Set;
import com.pntmall.admin.domain.SetProduct;
import com.pntmall.admin.domain.SetSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.SetService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/content/set")
public class SetController {
	public static final Logger logger = LoggerFactory.getLogger(SetController.class);

    @Autowired
	private SetService setService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute SetSearch setSearch) {
		Utils.savePath("set");
		List<Set> list = setService.getList(setSearch);
		Integer count = setService.getCount(setSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, setSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer sno) {
		String mode = "create";
		Set set = new Set();
		List<SetProduct> setProductList = new ArrayList<SetProduct>();
		if(sno != null && sno > 0) {
			mode = "modify";
			set = setService.getInfo(sno);
			setProductList = setService.getProductList(sno);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("set", set);
		mav.addObject("setProductList", setProductList);
		mav.addObject("retrivePath", Utils.retrivePath("set"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Set set, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			set.setCuser(sess.getAdminNo());
			setService.create(set, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Set set, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			set.setCuser(sess.getAdminNo());
			setService.modify(set, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}



}
