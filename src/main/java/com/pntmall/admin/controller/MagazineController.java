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

import com.pntmall.admin.domain.Magazine;
import com.pntmall.admin.domain.MagazineProduct;
import com.pntmall.admin.domain.MagazineSearch;
import com.pntmall.admin.domain.SeProduct;
import com.pntmall.admin.domain.SetProduct;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.MagazineService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/content/magazine")
public class MagazineController {
	public static final Logger logger = LoggerFactory.getLogger(MagazineController.class);

    @Autowired
	private MagazineService magazineService;

    @Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute MagazineSearch magazineSearch) {
		Utils.savePath("magazine");

		List<Magazine> list = magazineService.getList(magazineSearch);
		Integer count = magazineService.getCount(magazineSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, magazineSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer mno) {
		String mode = "create";
		Magazine magazine = new Magazine();
		List<MagazineProduct> magazineProductList = new ArrayList<MagazineProduct>();
		if(mno != null && mno > 0) {
			mode = "modify";
			magazine = magazineService.getInfo(mno);
			magazineProductList = magazineService.getProductList(mno);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("magazine", magazine);
		mav.addObject("magazineProductList", magazineProductList);
		mav.addObject("retrivePath", Utils.retrivePath("magazine"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Magazine magazine, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			magazine.setCuser(sess.getAdminNo());
			magazineService.create(magazine, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Magazine magazine, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			magazine.setCuser(sess.getAdminNo());
			magazineService.modify(magazine, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


}
