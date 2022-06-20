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

import com.pntmall.admin.domain.Pexhibition;
import com.pntmall.admin.domain.PexhibitionSearch;
import com.pntmall.admin.domain.SeProduct;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.PexhibitionService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/product/pexhibition")
public class PexhibitionController {
	public static final Logger logger = LoggerFactory.getLogger(PexhibitionController.class);

    @Autowired
	private PexhibitionService pexhibitionService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute PexhibitionSearch pexhibitionSearch) {

		List<Pexhibition> list = pexhibitionService.getList(pexhibitionSearch);
		Integer count = pexhibitionService.getCount(pexhibitionSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, pexhibitionSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer seno) {
		String mode = "create";
		Pexhibition pexhibition = new Pexhibition();
		List<SeProduct> seProductList = new ArrayList<SeProduct>();
		if(seno != null && seno > 0) {
			mode = "modify";
			pexhibition = pexhibitionService.getInfo(seno);
			seProductList = pexhibitionService.getProductList(seno);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("pexhibition", pexhibition);
		mav.addObject("seProductList", seProductList);

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Pexhibition pexhibition, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			pexhibition.setCuser(sess.getAdminNo());
			pexhibitionService.create(pexhibition, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Pexhibition pexhibition, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			pexhibition.setCuser(sess.getAdminNo());
			pexhibitionService.modify(pexhibition, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


}
