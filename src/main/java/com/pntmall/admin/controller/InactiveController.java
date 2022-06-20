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

import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.MemberSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.InactiveService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/member/inactive")
public class InactiveController {
	public static final Logger logger = LoggerFactory.getLogger(InactiveController.class);

    @Autowired
	private InactiveService inactiveService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute MemberSearch memberSearch) {
		Utils.savePath("member");

		List<Member> list = inactiveService.getList(memberSearch);
		Integer count = inactiveService.getCount(memberSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, memberSearch);


		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("memberSearch", memberSearch);

		return mav;
	}

	@PostMapping("/activeMember")
	@ResponseBody
	public ResultMessage activeMember(HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		Param param = new Param(request.getParameterMap());
		try {
			inactiveService.activeMember(param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "휴면해제 처리되었습니다.");
	}

	@PostMapping("/secedeMember")
	@ResponseBody
	public ResultMessage secedeMember(HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		Param param = new Param(request.getParameterMap());
		try {
			inactiveService.secedeMember(param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "탈퇴가 정상적으로 처리되었습니다.");
	}

}
