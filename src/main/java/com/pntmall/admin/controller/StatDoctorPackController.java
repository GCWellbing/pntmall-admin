package com.pntmall.admin.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
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

import com.pntmall.admin.service.StatDoctorPackService;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/stat/doctorPack")
public class StatDoctorPackController {
	public static final Logger logger = LoggerFactory.getLogger(StatDoctorPackController.class);

    @Autowired
	private StatDoctorPackService statDoctorPackService;

	@GetMapping("/list")
	public ModelAndView dailyList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        //cal.add(Calendar.DATE, -1);
        String edate = df.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String sdate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		List<Param> list = statDoctorPackService.getDailyList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/weekList")
	public ModelAndView weekList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        //cal.add(Calendar.DATE, -1);
        String edate = df.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String sdate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		List<Param> list = statDoctorPackService.getWeekList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/timeList")
	public ModelAndView timeList(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        //cal.add(Calendar.DATE, -1);
        String edate = df.format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String sdate = df.format(cal.getTime());

		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", edate);

		List<Param> list = statDoctorPackService.getTimeList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/monthList")
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


		List<Param> list = statDoctorPackService.getMonthList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}


	@GetMapping("/downloadExcel")
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		List<Param> list = null;
		String excelTltle = "";

		if("daily".equals(param.get("gubun"))){
			list = statDoctorPackService.getDailyList(param);
			excelTltle= "doctoPack_daily";
		} else if("week".equals(param.get("gubun"))){
			list = statDoctorPackService.getWeekList(param);
			excelTltle= "doctoPack_week";
		} else if("time".equals(param.get("gubun"))){
			list = statDoctorPackService.getTimeList(param);
			excelTltle= "doctoPack_time";
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

			list = statDoctorPackService.getMonthList(param);
			excelTltle = "doctoPack_monthly";
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
		cell.setCellValue("구분");
		sheet.setColumnWidth(idx, width * 2);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx + 1));

		idx += 2;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("전체수");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("연령대");
		sheet.setColumnWidth(idx, width * 6);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 6));

		idx += 7;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("성별");
		sheet.setColumnWidth(idx, width * 2);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 1));

		headerRow = sheet.createRow(1);
		idx = 3;

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
		cell.setCellValue("남자");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("여자");
		sheet.setColumnWidth(idx, width);

		int i=1;
		for (Param paramRst : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(paramRst.get("YMD"));
			if("week".equals(param.get("gubun"))){
				sheet.addMergedRegion(new CellRangeAddress(i, i+6, idx-1, idx-1));
			}else {
				sheet.addMergedRegion(new CellRangeAddress(i, i+5, idx-1, idx-1));
			}

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue("예약건수");
			sheet.addMergedRegion(new CellRangeAddress(i, i, idx-1, idx));

			idx = idx + 1;
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("RES_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("10_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("20_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("30_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("40_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("50_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("60_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("70_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("M_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("F_CNT")));

			if("week".equals(param.get("gubun"))){
				row = sheet.createRow(++i);
				idx = 1;
				cell = row.createCell(idx++);
				cell.setCellStyle(bodyStyleCenter);
				cell.setCellValue("예약확정건수");
				sheet.addMergedRegion(new CellRangeAddress(i, i, idx-1, idx));

				idx = idx + 1;
				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_10_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_20_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_30_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_40_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_50_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_60_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_70_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_M_CNT")));

				cell = row.createCell(idx++);
				cell.setCellStyle(styleRight);
				cell.setCellValue(Utils.formatMoney(paramRst.get("CONFIRM_F_CNT")));

			}

			row = sheet.createRow(++i);
			idx = 1;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue("완료건수");
			sheet.addMergedRegion(new CellRangeAddress(i, i, idx-1, idx));

			idx = idx + 1;
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_10_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_20_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_30_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_40_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_50_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_60_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_70_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_M_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_F_CNT")));


			row = sheet.createRow(++i);
			idx = 1;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue("승인건수");
			sheet.addMergedRegion(new CellRangeAddress(i, i, idx-1, idx));

			idx = idx + 1;
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_10_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_20_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_30_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_40_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_50_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_60_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_70_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_M_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("COMPLETE_F_CNT")));

			row = sheet.createRow(++i);
			idx = 1;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue("취소건수");
			sheet.addMergedRegion(new CellRangeAddress(i, i+2, idx-1, idx-1));

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue("합계");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_10_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_20_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_30_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_40_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_50_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_60_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_70_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_M_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CANCEL_F_CNT")));

			row = sheet.createRow(++i);
			idx = 2;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue("고객취소");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_10_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_20_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_30_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_40_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_50_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_60_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_70_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_M_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CUS_CANCEL_F_CNT")));

			row = sheet.createRow(++i);
			idx = 2;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue("상담사취소");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_10_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_20_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_30_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_40_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_50_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_60_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_70_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_M_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.get("CLI_CANCEL_F_CNT")));
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
