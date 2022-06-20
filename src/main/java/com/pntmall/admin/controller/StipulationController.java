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

import com.pntmall.admin.domain.Stipulation;
import com.pntmall.admin.domain.StipulationSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.StipulationService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/etc/stipulation")
public class StipulationController {
	public static final Logger logger = LoggerFactory.getLogger(StipulationController.class);

    @Autowired
	private StipulationService stipulationService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute StipulationSearch stipulationSearch) {
		Utils.savePath("stipulation");
		logger.debug("list", stipulationSearch);
		List<Stipulation> list = stipulationService.getList(stipulationSearch);
		Integer count = stipulationService.getCount(stipulationSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, stipulationSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("stipulationSearch", stipulationSearch);

		return mav;
	}

	@GetMapping("/info")
	@ResponseBody
	public ResultMessage info(Integer stipulationNo) {
		Stipulation info = stipulationService.getInfo(stipulationNo);
		Param param = new Param();
		param.set("info", info);

		return new ResultMessage(true, "", param);
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer stipulationNo) {
		String mode = "create";
		Stipulation stipulation = new Stipulation();
		if(stipulationNo != null && stipulationNo > 0) {
			mode = "modify";
			stipulation = stipulationService.getInfo(stipulationNo);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("stipulation", stipulation);
		mav.addObject("retrivePath", Utils.retrivePath("stipulation"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Stipulation stipulation) {
		AdminSession sess = AdminSession.getInstance();

		try {
			stipulation.setCuser(sess.getAdminNo());
			stipulationService.create(stipulation);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Stipulation stipulation, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			stipulation.setCuser(sess.getAdminNo());
			stipulationService.modify(stipulation);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


}
