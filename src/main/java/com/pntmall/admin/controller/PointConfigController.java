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

import com.pntmall.admin.domain.PointConfig;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.PointService;
import com.pntmall.common.ResultMessage;

@Controller
@RequestMapping("/promotion/pointconfig")
public class PointConfigController {
	public static final Logger logger = LoggerFactory.getLogger(PointConfigController.class);
	
    @Autowired
	private PointService pointService;

	@GetMapping("/list")
	public ModelAndView list() {
		List<PointConfig> list = pointService.getConfigList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		return mav;
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute PointConfig pointConfig, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		pointConfig.setCuser(sess.getAdminNo());
		
		try {
			pointService.createConfig(pointConfig);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
		
	}
}
