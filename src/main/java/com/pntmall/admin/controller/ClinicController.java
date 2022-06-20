package com.pntmall.admin.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import com.pntmall.admin.domain.ClinicImg;
import com.pntmall.admin.domain.ClinicJoin;
import com.pntmall.admin.domain.ClinicSearch;
import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.MemberSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.ClinicService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.directSend.DirectsendKakaoAllimApi;
import com.pntmall.common.directSend.SendKatalk;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/clinic/clinic")
public class ClinicController {
	public static final Logger logger = LoggerFactory.getLogger(ClinicController.class);

    @Value("${directSend.clinic.returnUrl}")
	String directSendClinicReturnUrl;

    @Autowired
	private ClinicService clinicService;

    @Autowired
	private CodeService codeService;

    @Autowired
	DirectsendKakaoAllimApi directsendKakaoAllimApi;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute ClinicSearch clinicSearch, HttpServletRequest request) {
		Utils.savePath("clinic");
		logger.debug("list", clinicSearch);
		Param param = new Param(request.getParameterMap());
		String[] approveArr = param.getValues("approveArr");

		List<Code> approveList = codeService.getList2("006");
		List<Clinic> list = clinicService.getList(clinicSearch);
		Integer count = clinicService.getCount(clinicSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, clinicSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("memberSearch", clinicSearch);
		mav.addObject("approveList", approveList);
		mav.addObject("approveArr", Arrays.toString(approveArr));

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer memNo) {
		String mode = "create";
		Clinic clinic = new Clinic();
		List<ClinicJoin> clinicJoinList = new ArrayList<ClinicJoin>();
		List<ClinicImg> clinicImgList = new ArrayList<ClinicImg>();
		if(memNo != null && memNo > 0) {
			mode = "modify";
			clinic = clinicService.getInfo(memNo);
			clinicJoinList = clinicService.getClinicJoinList(memNo);
			clinicImgList = clinicService.getClinicImgList(memNo);
		}

		//가입상태
		List<Code> approveList = codeService.getList2("006");
		//은행
		List<Code> bankList = codeService.getList2("007");
		List<Code> mtelList = codeService.getList2("011");
		List<Code> telList = codeService.getList2("012");
		List<Code> emailList = codeService.getList2("009");

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("approveList", approveList);
		mav.addObject("mtelList", mtelList);
		mav.addObject("telList", telList);
		mav.addObject("emailList", emailList);
		mav.addObject("bankList", bankList);
		mav.addObject("clinic", clinic);
		mav.addObject("clinicJoinList", clinicJoinList);
		mav.addObject("clinicImgList", clinicImgList);
		mav.addObject("retrivePath", Utils.retrivePath("clinic"));

		return mav;
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Clinic clinic, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			clinic.setCuser(sess.getAdminNo());
			clinicService.modify(clinic, param);

			//SMS발송 - 수신동의가 기존 값과 틀릴경우
		    if(!clinic.getSmsYn().equals(clinic.getSmsYnOld()) || !clinic.getEmailYn().equals(clinic.getEmailYnOld()) ) {
				SendKatalk sendKatalk = new SendKatalk();
				sendKatalk.setUserTemplateNo(265);
				sendKatalk.setName(clinic.getName());
				sendKatalk.setMobile(clinic.getMtel1()+clinic.getMtel2());
				sendKatalk.setNote1("Y".equals(clinic.getSmsYn())?"동의":"미동의");
				sendKatalk.setNote2("Y".equals(clinic.getEmailYn())?"동의":"미동의");
				sendKatalk.setNote3(directSendClinicReturnUrl);

				try {
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


	@SuppressWarnings("finally")
	@PostMapping("/createJoin")
	@ResponseBody
	public ResultMessage createJoin(@ModelAttribute ClinicJoin clinicJoin) {
		AdminSession sess = AdminSession.getInstance();
		boolean status = true;
		String msg = "등록되었습니다.";
		try {
			clinicJoin.setCuser(sess.getAdminNo());
			clinicService.createJoin(clinicJoin);


		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			status = false;
			msg = "오류가 발생했습니다."+e;
		} finally {

		   	if("006002".equals(clinicJoin.getApprove())) {	//승인
				//SMS발송
				//병의원정보 구하기
				Clinic clinic = clinicService.getInfo(clinicJoin.getMemNo());

				SendKatalk sendKatalk = new SendKatalk();
				sendKatalk.setUserTemplateNo(142);
				sendKatalk.setName(Utils.unSafeHTML(clinic.getClinicName()));
				sendKatalk.setMobile(clinic.getAlarmTel1()+clinic.getAlarmTel2());
				sendKatalk.setNote1(clinic.getMemId());
				sendKatalk.setNote2(directSendClinicReturnUrl);
				//예약발송
				//sendKatalk.setReserveTime("2021-10-07 11:40:00");
				//야간 발송 금지 시 예약 시간 등록
				//sendKatalk.setReserveTime(directsendKakaoAllimApi.nightSendChk());
				try {
					directsendKakaoAllimApi.send(sendKatalk);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		   	} else if("006003".equals(clinicJoin.getApprove())) {	//반려
				//SMS발송
				//병의원정보 구하기
				Clinic clinic = clinicService.getInfo(clinicJoin.getMemNo());

				SendKatalk sendKatalk = new SendKatalk();
				sendKatalk.setUserTemplateNo(148);
				sendKatalk.setName(Utils.unSafeHTML(clinic.getClinicName()));
				sendKatalk.setMobile(clinic.getAlarmTel1()+clinic.getAlarmTel2());
				sendKatalk.setNote1(clinic.getMemId());
				sendKatalk.setNote2(clinicJoin.getReason());
				sendKatalk.setNote3(directSendClinicReturnUrl);
				//예약발송
				//sendKatalk.setReserveTime("2021-10-07 11:40:00");
				//야간 발송 금지 시 예약 시간 등록
				//sendKatalk.setReserveTime(directsendKakaoAllimApi.nightSendChk());
				try {
					directsendKakaoAllimApi.send(sendKatalk);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		   	} else if("006006".equals(clinicJoin.getApprove())) {	//업데이트반려
				//SMS발송
				//병의원정보 구하기
				Clinic clinic = clinicService.getInfo(clinicJoin.getMemNo());

				SendKatalk sendKatalk = new SendKatalk();
				sendKatalk.setUserTemplateNo(151);
				sendKatalk.setName(Utils.unSafeHTML(clinic.getClinicName()));
				sendKatalk.setMobile(clinic.getAlarmTel1()+clinic.getAlarmTel2());
				sendKatalk.setNote1(clinic.getMemId());
				sendKatalk.setNote2(clinicJoin.getReason());
				sendKatalk.setNote3(directSendClinicReturnUrl);
				//예약발송
				//sendKatalk.setReserveTime("2021-10-07 11:40:00");
				//야간 발송 금지 시 예약 시간 등록
				//sendKatalk.setReserveTime(directsendKakaoAllimApi.nightSendChk());
				try {
					directsendKakaoAllimApi.send(sendKatalk);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		   	} else if("006007".equals(clinicJoin.getApprove())) {	//비활성화
				//SMS발송
				//병의원정보 구하기
				Clinic clinic = clinicService.getInfo(clinicJoin.getMemNo());

				SendKatalk sendKatalk = new SendKatalk();
				sendKatalk.setUserTemplateNo(154);
				sendKatalk.setName(Utils.unSafeHTML(clinic.getClinicName()));
				sendKatalk.setMobile(clinic.getAlarmTel1()+clinic.getAlarmTel2());
				sendKatalk.setNote1(clinic.getMemId());
				sendKatalk.setNote2(clinicJoin.getReason());
				sendKatalk.setNote3(directSendClinicReturnUrl);
				//예약발송
				//sendKatalk.setReserveTime("2021-10-07 11:40:00");
				//야간 발송 금지 시 예약 시간 등록
				//sendKatalk.setReserveTime(directsendKakaoAllimApi.nightSendChk());
				try {
					directsendKakaoAllimApi.send(sendKatalk);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		   	}

			return new ResultMessage(status, msg);

		}
	}



	@PostMapping("/statusPop")
	public ModelAndView statusPop(@ModelAttribute ClinicJoin clinicJoin, HttpServletRequest request) {
		logger.debug("clinicJoin:::::"+clinicJoin);
		ModelAndView mav = new ModelAndView();
		mav.addObject("clinicJoin", clinicJoin);

		return mav;
	}

	@PostMapping("/setStatusProc")
	@ResponseBody
	public ResultMessage setStatusProc(@ModelAttribute ClinicJoin clinicJoin) {
		AdminSession sess = AdminSession.getInstance();

		try {
			clinicJoin.setCuser(sess.getAdminNo());
			clinicService.createJoinMulti(clinicJoin);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다."+e);
		}


		return new ResultMessage(true, "처리 되었습니다.");
	}


	@GetMapping("/excel")
	public void downloadExcelVisit(@ModelAttribute ClinicSearch clinicSearch, HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());
		clinicSearch.setPageSize(Integer.MAX_VALUE);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat dfY_m_d = new SimpleDateFormat("yyyy_MM_dd");
        DateFormat dfYmd = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfYmdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<Clinic> list =  clinicService.getList(clinicSearch);
		String excelTltle = "clinic_"+dfY_m_d.format(cal.getTime());

		SXSSFWorkbook workbook = new SXSSFWorkbook();
		workbook.setCompressTempFiles(true);
		SXSSFSheet sheet = workbook.createSheet();
		sheet.setRandomAccessWindowSize(100);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);


		CellStyle bodyStyleCenter = workbook.createCellStyle();
		bodyStyleCenter.setAlignment(HorizontalAlignment.CENTER);
		bodyStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle styleLeft = workbook.createCellStyle();
		styleLeft.setAlignment(HorizontalAlignment.LEFT);
		styleLeft.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle styleRight = workbook.createCellStyle();
		styleRight.setAlignment(HorizontalAlignment.RIGHT);
		styleRight.setVerticalAlignment(VerticalAlignment.CENTER);

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle bodyStyleDate = workbook.createCellStyle();
		bodyStyleDate.setAlignment(HorizontalAlignment.CENTER);
		bodyStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));


		Row headerRow = sheet.createRow(0);
		int idx = -1;
		int width = 4000;

		Cell cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("회원번호");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(bodyStyleDate);
		cell.setCellValue("가입신청일");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("회원상태");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(bodyStyleDate);
		cell.setCellValue("업데이트일");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("최근로그인");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원ID");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("이름");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원명");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원 주소");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원 전화번호");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("휴대폰 번호");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("이메일 주소");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("SMS수신여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("메일수신여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("진료과목");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("원장님 소개");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("원장님약력");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("예약 가능 여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("소분 가능 여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("픽업 가능 여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("카톡 상담 여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("알림 설정 연락처");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원 대표자명");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원명");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원 사업자번호");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원 업태");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원 업종");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("건기식 대표자명");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("건기식 업체명");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("건기식 사업자번호");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("건기식 업태");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("건기식 업종");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("은행");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("계좌번호");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("예금주명");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("일치여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("SAP 고객코드");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("SAP 공급업체 코드");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("위도");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("경도");
		sheet.setColumnWidth(idx, width);

		headerRow = sheet.createRow(1);

		int i=0;
		for (Clinic clinic : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getMemNo());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
	        cal.setTime(clinic.getCdate());
        	cell.setCellValue(dfYmd.format(cal.getTime()));

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getApproveName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			if(clinic.getUdate() == null) {
				cell.setCellValue("");
			}else {
				cal.setTime(clinic.getUdate());
				cell.setCellValue(dfYmd.format(cal.getTime()));
			}

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			if(clinic.getLoginClinicDate() == null) {
				cell.setCellValue("");
			}else {
				cal.setTime(clinic.getLoginClinicDate());
				cell.setCellValue(dfYmdHms.format(cal.getTime()));
			}

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getMemId());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getClinicName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getZip()+" "+clinic.getAddr1()+" "+clinic.getAddr2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getTel1()+clinic.getTel2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getMtel1()+clinic.getMtel2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getEmail());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getSmsYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getEmailYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getSubject());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getDoctorIntro());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getDoctorHistory());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getReservationYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getDivisionYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getPickupYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getKatalkYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getAlarmTel1()+clinic.getAlarmTel2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessOwner());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessNo());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessType());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessItem());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessOwner2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessName2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessNo2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessType2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBusinessItem2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getBankName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getAccount());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getDepositor());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue("Y".equals(clinic.getDepositorNot()) ? "불일치":"일치");

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getClinicSellCd());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getClinicBuyCd());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getLatitude());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(clinic.getLongitude());
		}

		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+excelTltle+".xlsx");

		try {
			workbook.write(response.getOutputStream());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}


}
