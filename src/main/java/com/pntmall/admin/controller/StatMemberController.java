package com.pntmall.admin.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.NoticeComment;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.StatMemberService;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/stat/member")
public class StatMemberController {
	public static final Logger logger = LoggerFactory.getLogger(StatMemberController.class);

    @Autowired
	private StatMemberService statMemberService;

	@GetMapping("/join/list")
	public ModelAndView joinList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        cal.add(Calendar.DATE, -1);
        String edate = df.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String sdate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		logger.debug("joinList param:::"+ param);

		Param total = statMemberService.getJoinTotal(param);
		List<Param> list = statMemberService.getJoinList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("total", total);
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/join/monthList")
	public ModelAndView monthList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM");
        DateFormat dfYmd = new SimpleDateFormat("yyyy.MM.dd");

        String sdate = df.format(cal.getTime());
        String edate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		//시작월 첫날, 종료월 마지막날 구하기
		sdate = param.get("sdate");
		cal.set(Calendar.YEAR,Integer.parseInt(sdate.substring(0,4)));
		cal.set(Calendar.MONTH,Integer.parseInt(sdate.substring(5,7))-1);
		cal.set(Calendar.DATE,1);
		param.set("sdate", dfYmd.format(cal.getTime()) );

		edate = param.get("edate");
		cal.set(Calendar.YEAR,Integer.parseInt(edate.substring(0,4)));
		cal.set(Calendar.MONTH,Integer.parseInt(edate.substring(5,7))-1);
		cal.set(Calendar.DATE,1);
		cal.add(Calendar.MONTH,1);
		cal.add(Calendar.DATE,-1);
		param.set("edate", dfYmd.format(cal.getTime()) );
		logger.debug("joinList param:::"+ param);

		Param total = statMemberService.getJoinTotal(param);
		List<Param> list = statMemberService.getJoinMonthList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("total", total);
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/join/downloadExcel")
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		Param total = statMemberService.getJoinTotal(param);
		List<Param> list = null;
		String excelTltle = "";

		if("daily".equals(param.get("gubun"))){
			list = statMemberService.getJoinList(param);
			excelTltle= "member_daily";
		} else if("monthly".equals(param.get("gubun"))){

			//시작월 첫날, 종료월 마지막날 구하기
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date());
	        DateFormat dfYmd = new SimpleDateFormat("yyyy.MM.dd");

	        String sdate = param.get("sdate");
			cal.set(Calendar.YEAR,Integer.parseInt(sdate.substring(0,4)));
			cal.set(Calendar.MONTH,Integer.parseInt(sdate.substring(5,7))-1);
			cal.set(Calendar.DATE,1);
			param.set("sdate", dfYmd.format(cal.getTime()) );

			String edate = param.get("edate");
			cal.set(Calendar.YEAR,Integer.parseInt(edate.substring(0,4)));
			cal.set(Calendar.MONTH,Integer.parseInt(edate.substring(5,7))-1);
			cal.set(Calendar.DATE,1);
			cal.add(Calendar.MONTH,1);
			cal.add(Calendar.DATE,-1);
			param.set("edate", dfYmd.format(cal.getTime()) );

			list = statMemberService.getJoinMonthList(param);
			excelTltle = "member_monthly";
		}

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
		bodyStyleDate.setAlignment(HorizontalAlignment.LEFT);
		bodyStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));


		Row headerRow = sheet.createRow(0);
		int idx = -1;
		int width = 4000;

		Cell cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("날짜");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 회원수");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("신규회원수");
		sheet.setColumnWidth(idx, width * 3);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 2));

		idx += 3;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("휴면회원수");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("탈퇴회원수");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		headerRow = sheet.createRow(1);
		idx = 1;

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("PC");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("모바일");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("APP");
		sheet.setColumnWidth(idx, width);


		Integer totalCnt = total.getInt("TOTAL_BEFORE");

		int i=1;
		for (Param paramRst : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(paramRst.get("YMD"));

			totalCnt += paramRst.getInt("DAY_CNT");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue((Utils.formatMoney(totalCnt)));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("PC_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("MO_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("APP_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("SLEEP_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("SECEDE_CNT")));

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

	@GetMapping("/join/genderList")
	public ModelAndView genderList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        cal.add(Calendar.DATE, -1);
        String edate = df.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String sdate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		logger.debug("joinList param:::"+ param);

		Param total = statMemberService.getJoinTotal(param);
		List<Param> list = statMemberService.getJoinList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("total", total);
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/join/downloadExcelGender")
	public void downloadExcelGender(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		Param total = statMemberService.getJoinTotal(param);
		List<Param> list = statMemberService.getJoinList(param);
		String excelTltle = "member_gender";

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
		bodyStyleDate.setAlignment(HorizontalAlignment.LEFT);
		bodyStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));


		Row headerRow = sheet.createRow(0);
		int idx = -1;
		int width = 4000;

		Cell cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("날짜");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("남자");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("여자");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("미확인");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("비율");
		sheet.setColumnWidth(idx, width);

		headerRow = sheet.createRow(1);


		Integer totalCnt = total.getInt("TOTAL_BEFORE");
		Integer mCnt = total.getInt("TOTAL_M_BEFORE");
		Integer fCnt = total.getInt("TOTAL_F_BEFORE");
		Integer nosexCnt = total.getInt("TOTAL_NOSEX_BEFORE");

		int i=0;
		for (Param paramRst : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(paramRst.get("YMD"));

			totalCnt += paramRst.getInt("DAY_CNT");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue((Utils.formatMoney(totalCnt)));

			mCnt += paramRst.getInt("M_CNT");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(mCnt));

			fCnt += paramRst.getInt("F_CNT");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(fCnt));

			nosexCnt += paramRst.getInt("NOSEX_CNT");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(nosexCnt));

			double mRate = Double.valueOf(mCnt)/Double.valueOf(totalCnt)*100.0;
			double fRate = Double.valueOf(fCnt)/Double.valueOf(totalCnt)*100.0;
			double nRate = Double.valueOf(nosexCnt)/Double.valueOf(totalCnt)*100.0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(format00(mRate)+"/"+format00(fRate)+"/"+format00(nRate));

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

	public static String format00(double nMoney) {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#,##0.00");

		return df.format(nMoney);
	}

	@GetMapping("/join/ageList")
	public ModelAndView ageList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        cal.add(Calendar.DATE, -1);
        String edate = df.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String sdate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		logger.debug("joinList param:::"+ param);

		Param total = statMemberService.getAgeTotal(param);
		List<Param> list = statMemberService.getAgeList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("total", total);
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/join/downloadExcelAge")
	public void downloadExcelAge(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		Param total = statMemberService.getAgeTotal(param);
		List<Param> list = statMemberService.getAgeList(param);
		String excelTltle = "member_age";

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
		bodyStyleDate.setAlignment(HorizontalAlignment.LEFT);
		bodyStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));


		Row headerRow = sheet.createRow(0);
		int idx = -1;
		int width = 4000;

		Cell cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("날짜");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("10대");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("20대");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("30대");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("40대");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("50대");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("60대");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("70대");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("미확인");
		sheet.setColumnWidth(idx, width);

		headerRow = sheet.createRow(1);


		Integer totalCnt = total.getInt("BEFORE_CNT");
		Integer cnt10 = total.getInt("BEFORE_CNT10");
		Integer cnt20 = total.getInt("BEFORE_CNT20");
		Integer cnt30 = total.getInt("BEFORE_CNT30");
		Integer cnt40 = total.getInt("BEFORE_CNT40");
		Integer cnt50 = total.getInt("BEFORE_CNT50");
		Integer cnt60 = total.getInt("BEFORE_CNT60");
		Integer cnt70 = total.getInt("BEFORE_CNT70");
		Integer nonCnt = total.getInt("BEFORE_NON_CNT");

		int i=0;
		for (Param paramRst : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(paramRst.get("YMD"));

			totalCnt += paramRst.getInt("DAY_CNT");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue((Utils.formatMoney(totalCnt)));

			cnt10 += paramRst.getInt("CNT10");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(cnt10));

			cnt20 += paramRst.getInt("CNT20");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(cnt20));

			cnt30 += paramRst.getInt("CNT30");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(cnt30));

			cnt40 += paramRst.getInt("CNT40");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(cnt40));

			cnt50 += paramRst.getInt("CNT50");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(cnt50));

			cnt60 += paramRst.getInt("CNT60");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(cnt60));

			cnt70 += paramRst.getInt("CNT70");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(cnt70));

			nonCnt += paramRst.getInt("NON_CNT");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(nonCnt));

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

	@GetMapping("/visit/list")
	public ModelAndView visitList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        cal.add(Calendar.DATE, -1);
        String edate = df.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String sdate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		logger.debug("joinList param:::"+ param);

		List<Param> list = statMemberService.getVisitList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/visit/monthList")
	public ModelAndView monthVisitList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM");
        DateFormat dfYmd = new SimpleDateFormat("yyyy.MM.dd");

        String sdate = df.format(cal.getTime());
        String edate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		//시작월 첫날, 종료월 마지막날 구하기
		sdate = param.get("sdate");
		cal.set(Calendar.YEAR,Integer.parseInt(sdate.substring(0,4)));
		cal.set(Calendar.MONTH,Integer.parseInt(sdate.substring(5,7))-1);
		cal.set(Calendar.DATE,1);
		param.set("sdate", dfYmd.format(cal.getTime()) );

		edate = param.get("edate");
		cal.set(Calendar.YEAR,Integer.parseInt(edate.substring(0,4)));
		cal.set(Calendar.MONTH,Integer.parseInt(edate.substring(5,7))-1);
		cal.set(Calendar.DATE,1);
		cal.add(Calendar.MONTH,1);
		cal.add(Calendar.DATE,-1);
		param.set("edate", dfYmd.format(cal.getTime()) );
		logger.debug("joinList param:::"+ param);

		List<Param> list = statMemberService.getVisitMonthList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}
	@GetMapping("/visit/downloadExcelVisit")
	public void downloadExcelVisit(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		List<Param> list = statMemberService.getVisitList(param);
		String excelTltle = "member_visit_daily";

		if("daily".equals(param.get("gubun"))){
			list = statMemberService.getVisitList(param);
			excelTltle= "member_visit_daily";
		} else if("monthly".equals(param.get("gubun"))){

			//시작월 첫날, 종료월 마지막날 구하기
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date());
	        DateFormat dfYmd = new SimpleDateFormat("yyyy.MM.dd");

	        String sdate = param.get("sdate");
			cal.set(Calendar.YEAR,Integer.parseInt(sdate.substring(0,4)));
			cal.set(Calendar.MONTH,Integer.parseInt(sdate.substring(5,7))-1);
			cal.set(Calendar.DATE,1);
			param.set("sdate", dfYmd.format(cal.getTime()) );

			String edate = param.get("edate");
			cal.set(Calendar.YEAR,Integer.parseInt(edate.substring(0,4)));
			cal.set(Calendar.MONTH,Integer.parseInt(edate.substring(5,7))-1);
			cal.set(Calendar.DATE,1);
			cal.add(Calendar.MONTH,1);
			cal.add(Calendar.DATE,-1);
			param.set("edate", dfYmd.format(cal.getTime()) );

			list = statMemberService.getVisitMonthList(param);
			excelTltle = "member_visit_monthly";
		}

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
		bodyStyleDate.setAlignment(HorizontalAlignment.LEFT);
		bodyStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));


		Row headerRow = sheet.createRow(0);
		int idx = -1;
		int width = 4000;

		Cell cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("날짜");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 방문자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("PC 방문자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("모바일 방문자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("APP 방문자수");
		sheet.setColumnWidth(idx, width);

		headerRow = sheet.createRow(1);

		int i=0;
		for (Param paramRst : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(paramRst.get("YMD"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue((Utils.formatMoney(paramRst.getInt("DAY_CNT"))));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("PC_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("MO_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("APP_CNT")));

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
