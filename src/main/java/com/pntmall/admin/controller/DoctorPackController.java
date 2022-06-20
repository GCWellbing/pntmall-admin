package com.pntmall.admin.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.Reservation;
import com.pntmall.admin.domain.ReservationProduct;
import com.pntmall.admin.domain.ReservationSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.ClinicService;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.ReservationService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.directSend.DirectsendKakaoAllimApi;
import com.pntmall.common.directSend.SendKatalk;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/clinic/doctorPack")
public class DoctorPackController {
	public static final Logger logger = LoggerFactory.getLogger(DoctorPackController.class);

    @Value("${directSend.front.returnUrl}")
	String directSendFrontReturnUrl;

    @Value("${directSend.clinic.returnUrl}")
	String directSendClinicReturnUrl;

    @Autowired
	private ReservationService reservationService;

    @Autowired
	private CodeService codeService;

    @Autowired
	private ClinicService clinicService;

    @Autowired
	DirectsendKakaoAllimApi directsendKakaoAllimApi;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute ReservationSearch reservationSearch, HttpServletRequest request) {
		Utils.savePath("doctorPack");

		logger.debug("list"+reservationSearch+"---"+request.getRequestURI());

		List<Code> cateList = codeService.getList2("016");
		List<Code> statusList = codeService.getList2("025");

		reservationSearch.setDoctorPack("Y");
		List<Reservation> list = reservationService.getList(reservationSearch);
		Integer count = reservationService.getCount(reservationSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, reservationSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("cateList", cateList);
		mav.addObject("statusList", statusList);
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(@ModelAttribute ReservationSearch reservationSearch, HttpServletRequest request) {
		Reservation reservation = reservationService.getInfo(reservationSearch);
		List<ReservationProduct> reservationProductList = reservationService.getProductList(reservationSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("reservation", reservation);
		mav.addObject("reservationProductList", reservationProductList);
		mav.addObject("retrivePath", Utils.retrivePath("doctorPack"));
		mav.addObject("directSendFrontReturnUrl", directSendFrontReturnUrl);

		return mav;
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Reservation reservation, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {

			reservation.setUuser(sess.getAdminNo());
			reservationService.modify(reservation);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


	@PostMapping("/memo")
	@ResponseBody
	public ResultMessage memo(@ModelAttribute Reservation reservation, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {

			reservation.setUuser(sess.getAdminNo());
			reservationService.modifyMemo(reservation);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}

	@PostMapping("/complete")
	@ResponseBody
	public ResultMessage complete(@ModelAttribute Reservation reservation, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		try {
			reservation.setUuser(sess.getAdminNo());
			reservationService.complete(reservation, param, request);


			//SMS발송
			//회원정보
			Member infoMem = clinicService.infoMem(reservation.getCuser());

 	    	SimpleDateFormat simpl = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
 	    	long millis = System.currentTimeMillis();
 	    	String s = simpl.format(millis);

			//일반 회원 SMS발송
			try {
				SendKatalk sendKatalk = new SendKatalk();
				sendKatalk.setUserTemplateNo(247);
				sendKatalk.setName(infoMem.getName());
				sendKatalk.setMobile(infoMem.getMtel1()+infoMem.getMtel2());
				sendKatalk.setNote1(s);
				sendKatalk.setNote2(directSendFrontReturnUrl+"/order/cart?gubun=4");
				sendKatalk.setNote3(directSendFrontReturnUrl);

				directsendKakaoAllimApi.send(sendKatalk);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "처리되었습니다.");
	}

}
