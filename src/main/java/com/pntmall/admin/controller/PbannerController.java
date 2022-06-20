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

import com.pntmall.admin.domain.Pbanner;
import com.pntmall.admin.domain.PbannerProduct;
import com.pntmall.admin.domain.PbannerSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.PbannerService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/product/pbanner")
public class PbannerController {
	public static final Logger logger = LoggerFactory.getLogger(PbannerController.class);

    @Autowired
	private PbannerService pbannerService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute PbannerSearch pbannerSearch) {

		List<Pbanner> list = pbannerService.getList(pbannerSearch);
		Integer count = pbannerService.getCount(pbannerSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, pbannerSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer bno) {
		String mode = "create";
		Pbanner pbanner = new Pbanner();
		List<PbannerProduct> pbannerProductList = new ArrayList<PbannerProduct>();
		if(bno != null && bno > 0) {
			mode = "modify";
			pbanner = pbannerService.getInfo(bno);
			pbannerProductList = pbannerService.getProductList(bno);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("pbanner", pbanner);
		mav.addObject("pbannerProductList", pbannerProductList);

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Pbanner pbanner, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			pbanner.setCuser(sess.getAdminNo());
			pbannerService.create(pbanner, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Pbanner pbanner, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			pbanner.setCuser(sess.getAdminNo());
			pbannerService.modify(pbanner, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}



}
