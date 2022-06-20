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

import com.pntmall.admin.domain.Clinic;
import com.pntmall.admin.domain.ClinicImg;
import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.MemberCs;
import com.pntmall.admin.domain.MemberSearch;
import com.pntmall.admin.domain.Order;
import com.pntmall.admin.domain.OrderSearch;
import com.pntmall.admin.domain.Qna;
import com.pntmall.admin.domain.QnaImg;
import com.pntmall.admin.domain.QnaSearch;
import com.pntmall.admin.domain.Reservation;
import com.pntmall.admin.domain.ReservationSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.ClinicService;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.MemberService;
import com.pntmall.admin.service.OrderService;
import com.pntmall.admin.service.QnaService;
import com.pntmall.admin.service.ReservationService;
import com.pntmall.admin.service.SecessionService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/member/secession")
public class SecessionController {
	public static final Logger logger = LoggerFactory.getLogger(SecessionController.class);

    @Autowired
	private MemberService memberService;

    @Autowired
	private SecessionService secessionService;

    @Autowired
	private QnaService qnaService;

    @Autowired
	private CodeService codeService;

    @Autowired
	private OrderService orderService;

    @Autowired
	private ReservationService reservationService;

    @Autowired
	private ClinicService clinicService;


	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute MemberSearch memberSearch) {
		Utils.savePath();
		logger.debug("list::"+memberSearch);

		List<Member> list = secessionService.getList(memberSearch);
		Integer count = secessionService.getCount(memberSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, memberSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("memberSearch", memberSearch);

		return mav;
	}

	@GetMapping("/editN")
	public ModelAndView editN(Integer memNo) {
		String mode = "modify";
		Member member = new Member();
		if(memNo != null && memNo > 0) {
			member = secessionService.getInfo(memNo);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
		mav.addObject("mode", mode);
		mav.addObject("member", member);

		return mav;
	}

	@GetMapping("/editY")
	public ModelAndView editY(Integer memNo) {
		String mode = "modify";
		Clinic clinic = new Clinic();
		List<ClinicImg> clinicImgList = new ArrayList<ClinicImg>();
		if(memNo != null && memNo > 0) {
			clinic = clinicService.getInfo(memNo);
			clinicImgList = clinicService.getClinicImgList(memNo);
		}
		List<Code> bankList = codeService.getList2("007");
		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
		mav.addObject("mode", mode);
		mav.addObject("bankList", bankList);
		mav.addObject("clinic", clinic);
		mav.addObject("clinicImgList", clinicImgList);

		return mav;
	}

	@GetMapping("/qnaList")
	public ModelAndView list(@ModelAttribute QnaSearch qnaSearch, HttpServletRequest request) {
		if(("/cs/qna/list").equals(request.getRequestURI())){
			qnaSearch.setClinicYn("N");
		}else if(("/clinic/qna/list").equals(request.getRequestURI())){
			qnaSearch.setClinicYn("Y");
		}else {
			qnaSearch.setClinicYn("");
		}

		logger.debug("list"+qnaSearch+"---"+request.getRequestURI());

		List<Code> cateList1 = codeService.getList2("014");
		List<Code> cateList2 = codeService.getList2("015");

		List<Qna> list = qnaService.getList(qnaSearch);
		Integer count = qnaService.getCount(qnaSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, qnaSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
		mav.addObject("cateList1", cateList1);
		mav.addObject("cateList2", cateList2);
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("qnaSearch", qnaSearch);

		return mav;
	}

	@GetMapping("/qnaEdit")
	public ModelAndView edit(@ModelAttribute QnaSearch qnaSearch, HttpServletRequest request) {
		if(("/cs/qna/list").equals(request.getRequestURI())){
			qnaSearch.setClinicYn("N");
		}else if(("/clinic/qna/list").equals(request.getRequestURI())){
			qnaSearch.setClinicYn("Y");
		}else {
			qnaSearch.setClinicYn("");
		}

		String mode = "modify";
		Qna qna = qnaService.getInfo(qnaSearch);
		List<QnaImg> qnaImgList = qnaService.getQnaImgList(qnaSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("qna", qna);
		mav.addObject("qnaImgList", qnaImgList);

		return mav;
	}

	@GetMapping("/order")
	public ModelAndView order(@ModelAttribute OrderSearch orderSearch) {

		ModelAndView mav = new ModelAndView();

		List<Order> list = orderService.getList(orderSearch);
		Integer count = orderService.getCount(orderSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, orderSearch);

		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("retrivePath", Utils.retrivePath());

		return mav;
	}

	@GetMapping("/reser")
	public ModelAndView reser(@ModelAttribute ReservationSearch reservationSearch, HttpServletRequest request) {

		List<Reservation> list = reservationService.getList(reservationSearch);
		Integer count = reservationService.getCount(reservationSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, reservationSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}


	@PostMapping("/deleteMember")
	@ResponseBody
	public ResultMessage deleteMember(HttpServletRequest request) {
		logger.debug("deleteMember:::::::::");
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		Param param = new Param(request.getParameterMap());
		try {
			secessionService.deleteMember(param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "삭제되었습니다.");
	}

}
