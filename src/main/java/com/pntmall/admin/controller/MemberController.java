package com.pntmall.admin.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pntmall.admin.domain.*;
import com.pntmall.admin.service.*;

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

import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.directSend.DirectsendKakaoAllimApi;
import com.pntmall.common.directSend.SendKatalk;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/member/general")
public class MemberController {
	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);


    @Value("${directSend.front.returnUrl}")
	String directSendFrontReturnUrl;

    @Autowired
	DirectsendKakaoAllimApi directsendKakaoAllimApi;

    @Autowired
	private MemberService memberService;

    @Autowired
	private QnaService qnaService;

    @Autowired
	private CodeService codeService;

    @Autowired
	private MemGradeService memGradeService;

    @Autowired
	private PointService pointService;

    @Autowired
	private CouponService couponService;

    @Autowired
	private OrderService orderService;

    @Autowired
	private ReservationService reservationService;

	@Autowired
	private MyHealthService healthService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute MemberSearch memberSearch) {
		Utils.savePath("member");
		logger.debug("list", memberSearch);

		List<Member> list = memberService.getList(memberSearch);
		Integer count = memberService.getCount(memberSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, memberSearch);

    	List<MemGrade> listGrade = memGradeService.getList();


		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("listGrade", listGrade);
		mav.addObject("memberSearch", memberSearch);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer memNo) {

		String mode = "create";
		Member member = new Member();
		List<MemberCs> memberCs = new ArrayList<MemberCs>();
		if(memNo != null && memNo > 0) {
			mode = "modify";
			member = memberService.getInfo(memNo);
			memberCs = memberService.getCsList(memNo);
		}

		Param totOrderInfo = orderService.getTotalOrderInfo(memNo);

		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath("member"));
		mav.addObject("mode", mode);
		mav.addObject("member", member);
		mav.addObject("memberCs", memberCs);
		mav.addObject("totOrderAmt", totOrderInfo.getInt("pay_amt"));
		mav.addObject("totOrderCount", totOrderInfo.getInt("cnt"));
		return mav;
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Member member, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			// 임직원으로 등급 변경시 마이클리닉 설정
			if(!member.getGradeNo().equals(member.getGradeNoOld()) && member.getGradeNo() == 4) {
				member.setClinicId("16"); // wellcare 녹십자 강남 아이메드
			}

			member.setCuser(sess.getAdminNo());
			memberService.modify(member);

			//SMS발송 - 등급 변경시
			if(!member.getGradeNo().equals(member.getGradeNoOld())) {
				SendKatalk sendKatalk = new SendKatalk();
				sendKatalk.setUserTemplateNo(268);
				sendKatalk.setName(member.getName());
				sendKatalk.setMobile(member.getMtel1()+member.getMtel2());
				sendKatalk.setNote1(member.getGradeName());
				sendKatalk.setNote2(directSendFrontReturnUrl);

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

	@PostMapping("/createCs")
	@ResponseBody
	public ResultMessage createCs(@ModelAttribute MemberCs memberCs) {
		AdminSession sess = AdminSession.getInstance();

		try {
			memberCs.setStatus("S");
			memberCs.setCuser(sess.getAdminNo());
			memberService.createCs(memberCs);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modifyCs")
	@ResponseBody
	public ResultMessage modifyCs(@ModelAttribute MemberCs memberCs, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			memberCs.setCuser(sess.getAdminNo());
			memberService.modifyCs(memberCs);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "처리되었습니다.");
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

	@GetMapping("/point")
	public ModelAndView point(@ModelAttribute PointSearch pointSearch) {

		ModelAndView mav = new ModelAndView();

		List<Point> list = pointService.getLogList(pointSearch);
		Integer count = pointService.getLogCount(pointSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, pointSearch);

		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("retrivePath", Utils.retrivePath());

		return mav;
	}

	@GetMapping("/coupon")
	public ModelAndView coupon(@ModelAttribute CouponSearch couponSearch) {

		ModelAndView mav = new ModelAndView();

		List<Coupon> list = couponService.getLogList(couponSearch);
		Integer count = couponService.getLogCount(couponSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, couponSearch);

		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("retrivePath", Utils.retrivePath());

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

	@PostMapping("/secedeProc")
	@ResponseBody
	public ResultMessage secedeProc(@ModelAttribute Member member) {
		AdminSession sess = AdminSession.getInstance();
	    if(!sess.isLogin()) {
            return new ResultMessage(false, "로그인이 필요합니다.");
        }

	    member.setUuser(sess.getAdminNo());
	    memberService.updateSecede(member);

		return new ResultMessage(true, "탈퇴가 정상적으로 처리되었습니다.");
	}

	@GetMapping("/health")
	public ModelAndView healt(@ModelAttribute MyHealth myHealth) {

		ModelAndView mav = new ModelAndView();

		List<MyHealth> list = healthService.getList(myHealth);
		Integer count = healthService.getCount(myHealth);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, myHealth);

		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/excel")
	public void downloadExcelVisit(@ModelAttribute MemberSearch memberSearch, HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());
		memberSearch.setPageSize(Integer.MAX_VALUE);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat dfY_m_d = new SimpleDateFormat("yyyy_MM_dd");
        DateFormat dfYmd = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfYmdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<Member> list =  memberService.getList(memberSearch);
		String excelTltle = "member_"+dfY_m_d.format(cal.getTime());

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
		cell.setCellStyle(headerStyle);
		cell.setCellValue("등록일");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("가입경로");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("최근 로그인");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("등급");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("아이디");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("이름");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("성별");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("생년월일");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("이메일");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("휴대폰번호");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("메일수신여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("SMS수신여부");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("마이클리닉 이름");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("마이클리닉 코드");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 주문금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 주문건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("포인트");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 로그인수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("CS상담정보");
		sheet.setColumnWidth(idx, width);

		headerRow = sheet.createRow(1);

		int i=0;
		for (Member member : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getMemNo());


			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
	        cal.setTime(member.getCdate());
        	cell.setCellValue(dfYmd.format(cal.getTime()));

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getJoinType()+"("+member.getJoinDevice()+")");

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
	        cal.setTime(member.getLoginDate() == null ? member.getCdate() : member.getLoginDate());
        	cell.setCellValue(dfYmdHms.format(cal.getTime()));

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getGradeName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getMemId());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			String gender = "M".equals(member.getGender()) ? "남자": "W".equals(member.getGender()) ? "여자":"";
			cell.setCellValue(gender);

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getBirthday());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getEmail());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getMtel1()+member.getMtel2());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getEmailYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getSmsYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getClinicName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getClinicId());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(Utils.formatMoney(member.getOrderPayAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(Utils.formatMoney(member.getOrderCnt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(Utils.formatMoney(member.getCurPoint()));

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(Utils.formatMoney(member.getLoginCnt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(member.getCsCnt());
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
