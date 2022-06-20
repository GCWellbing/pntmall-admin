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

import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.Point;
import com.pntmall.admin.domain.PointSearch;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.PointService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/promotion/point")
public class PointController {
	public static final Logger logger = LoggerFactory.getLogger(PointController.class);
	
    @Autowired
	private PointService pointService;

    @Autowired
	private CodeService codeService;

	@GetMapping("/edit")
	public ModelAndView edit(@ModelAttribute PointSearch pointSearch) {
		Utils.savePath();
		
		List<Code> reasonList = codeService.getList2("019");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("reasonList", reasonList);
		
		return mav;
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Point point, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		point.setCuser(sess.getAdminNo());
		if("1".equals(param.get("gubun"))) {	// 지급
			point.setPoint(Math.abs(point.getPoint()));
		} else {	// 차감
			point.setPoint(Math.abs(point.getPoint()) * -1);
		}
		
		try {
			pointService.create(point, param.get("memId"));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
		
	}
}
