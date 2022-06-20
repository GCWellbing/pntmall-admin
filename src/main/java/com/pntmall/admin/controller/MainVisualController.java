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

import com.pntmall.admin.domain.MainVisual;
import com.pntmall.admin.domain.MainVisualSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.MainVisualService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/content/mainVisual")
public class MainVisualController {
	public static final Logger logger = LoggerFactory.getLogger(MainVisualController.class);

    @Autowired
	private MainVisualService mainVisualService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute MainVisualSearch mainVisualSearch) {
		Utils.savePath("mainVisual");

		List<MainVisual> list = mainVisualService.getList(mainVisualSearch);
		Integer count = mainVisualService.getCount(mainVisualSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, mainVisualSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer mvNo) {
		String mode = "create";
		MainVisual mainVisual = new MainVisual();
		if(mvNo != null && mvNo > 0) {
			mode = "modify";
			mainVisual = mainVisualService.getInfo(mvNo);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("mainVisual", mainVisual);
		mav.addObject("retrivePath", Utils.retrivePath("mainVisual"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute MainVisual mainVisual, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			mainVisual.setCuser(sess.getAdminNo());
			mainVisualService.create(mainVisual, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute MainVisual mainVisual, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			mainVisual.setCuser(sess.getAdminNo());
			mainVisualService.modify(mainVisual, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}


}
