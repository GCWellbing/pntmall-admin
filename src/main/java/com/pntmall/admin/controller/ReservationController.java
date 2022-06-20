package com.pntmall.admin.controller;

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

import com.pntmall.admin.domain.Clinic;
import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.Reservation;
import com.pntmall.admin.domain.ReservationSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.ClinicService;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.ReservationService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.directSend.DirectsendKakaoAllimApi;
import com.pntmall.common.directSend.SendKatalk;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/clinic/reservation")
public class ReservationController {
	public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

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
		Utils.savePath("reservation");
		logger.debug("list"+reservationSearch+"---"+request.getRequestURI());

		List<Code> cateList = codeService.getList2("016");
		List<Code> statusList = codeService.getList2("025");

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

		ModelAndView mav = new ModelAndView();
		mav.addObject("reservation", reservation);
		mav.addObject("retrivePath", Utils.retrivePath("reservation"));


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


			//SMS발송
			//회원정보
			Member infoMem = clinicService.infoMem(reservation.getCuser());
//			Clinic infoClinic = clinicService.getInfo(reservation.getUuser());
			Clinic infoClinic = clinicService.getInfo(reservation.getClinicMemNo());

			if("025004".equals(reservation.getStatus())) { //예약 확정
				//일반 회원 SMS발송
				try {
					SendKatalk sendKatalk = new SendKatalk();
					sendKatalk.setUserTemplateNo(184);
					sendKatalk.setName(infoMem.getName());
					sendKatalk.setMobile(infoMem.getMtel1()+infoMem.getMtel2());
					sendKatalk.setNote1(Utils.unSafeHTML(infoClinic.getClinicName()));
					sendKatalk.setNote2(infoClinic.getAddr1()+" "+infoClinic.getAddr2());
					sendKatalk.setNote3(reservation.getRdate()+" "+reservation.getRtime());
					sendKatalk.setNote4(reservation.getCateName());
					sendKatalk.setNote5(directSendFrontReturnUrl);

					directsendKakaoAllimApi.send(sendKatalk);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				//병의원 회원 SMS발송
				try {
					SendKatalk sendKatalk = new SendKatalk();
					sendKatalk.setUserTemplateNo(193);
					sendKatalk.setName(Utils.unSafeHTML(infoClinic.getClinicName()));
					sendKatalk.setMobile(infoClinic.getAlarmTel1()+infoClinic.getAlarmTel2());
					sendKatalk.setNote1(infoMem.getName());
					sendKatalk.setNote2(infoMem.getMtel1()+infoMem.getMtel2());
					sendKatalk.setNote3(reservation.getRdate()+" "+reservation.getRtime());
					sendKatalk.setNote4(reservation.getCateName());
					sendKatalk.setNote5(directSendClinicReturnUrl);
					//야간 발송 금지 시 예약 시간 등록
					sendKatalk.setReserveTime(directsendKakaoAllimApi.nightSendChk());

					directsendKakaoAllimApi.send(sendKatalk);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				//닥터팩 상담 30분전 안내

			}else if("025003".equals(reservation.getStatus())) { //예약 불가
				//일반 회원 SMS발송
				try {
					SendKatalk sendKatalk = new SendKatalk();
					sendKatalk.setUserTemplateNo(187);
					sendKatalk.setName(infoMem.getName());
					sendKatalk.setMobile(infoMem.getMtel1()+infoMem.getMtel2());
					sendKatalk.setNote1(Utils.unSafeHTML(infoClinic.getClinicName()));
					sendKatalk.setNote2(reservation.getRdate()+" "+reservation.getRtime());
					sendKatalk.setNote3(reservation.getReason());
					sendKatalk.setNote4(directSendFrontReturnUrl);

					directsendKakaoAllimApi.send(sendKatalk);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				//병의원 회원 SMS발송
				try {
					SendKatalk sendKatalk = new SendKatalk();
					sendKatalk.setUserTemplateNo(196);
					sendKatalk.setName(Utils.unSafeHTML(infoClinic.getClinicName()));
					sendKatalk.setMobile(infoClinic.getAlarmTel1()+infoClinic.getAlarmTel2());
					sendKatalk.setNote1(infoMem.getName());
					sendKatalk.setNote2(infoMem.getMtel1()+infoMem.getMtel2());
					sendKatalk.setNote3(reservation.getRdate()+" "+reservation.getRtime());
					sendKatalk.setNote4(Utils.unSafeHTML(reservation.getReason()));
					sendKatalk.setNote5(directSendClinicReturnUrl);
					//야간 발송 금지 시 예약 시간 등록
					sendKatalk.setReserveTime(directsendKakaoAllimApi.nightSendChk());

					directsendKakaoAllimApi.send(sendKatalk);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


}
