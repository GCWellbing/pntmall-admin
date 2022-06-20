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

import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.StatClinicService;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/stat/clinic")
public class StatClinicController {
	public static final Logger logger = LoggerFactory.getLogger(StatClinicController.class);

    @Autowired
	private StatClinicService statClinicService;


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

		List<Param> list = statClinicService.getVisitList(param);

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

		List<Param> list = statClinicService.getVisitMonthList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}
	@GetMapping("/visit/downloadExcelVisit")
	public void downloadExcelVisit(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		List<Param> list = null;
		String excelTltle = "";

		if("daily".equals(param.get("gubun"))){
			list = statClinicService.getVisitList(param);
			excelTltle= "clinic_visit_daily";
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

			list = statClinicService.getVisitMonthList(param);
			excelTltle = "clinic_visit_monthly";
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


	@GetMapping("/clinicVisit/list")
	public ModelAndView clinicVisitList(HttpServletRequest request) {
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

		List<Param> list = statClinicService.getClinicVisitList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/clinicVisit/monthList")
	public ModelAndView monthClinicVisitList(HttpServletRequest request) {
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

		List<Param> list = statClinicService.getClinicVisitMonthList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}
	@GetMapping("/clinicVisit/downloadExcelClinicVisit")
	public void downloadExcelClinicVisit(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		List<Param> list = statClinicService.getClinicVisitList(param);
		String excelTltle = "";

		if("daily".equals(param.get("gubun"))){
			list = statClinicService.getClinicVisitList(param);
			excelTltle= "clinic_visit_daily";
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

			list = statClinicService.getClinicVisitMonthList(param);
			excelTltle = "clinic_visit_monthly";
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
			cell.setCellValue((Utils.formatMoney(paramRst.getInt("CLINIC_CNT"))));

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

	@GetMapping("/member/new/list")
	public ModelAndView newList(HttpServletRequest request) {
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

		Param total = statClinicService.getNewTotal(param);
		List<Param> list = statClinicService.getNewList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("total", total);
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/member/new/monthList")
	public ModelAndView newMonthVisitList(HttpServletRequest request) {
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

		Param total = statClinicService.getNewMonthTotal(param);
		List<Param> list = statClinicService.getNewMonthList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("total", total);
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/member/new/downloadExcel")
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		Param total = statClinicService.getNewTotal(param);
		List<Param> list = null;
		String excelTltle = "";

		if("daily".equals(param.get("gubun"))){
			list = statClinicService.getNewList(param);
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

			list = statClinicService.getNewMonthList(param);
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

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("신규회원수");
		sheet.setColumnWidth(idx, width);

		int i=0;
		for (Param paramRst : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(paramRst.get("YMD"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.getInt("DAY_CNT")));


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

	@GetMapping("/member/all/list")
	public ModelAndView allList(HttpServletRequest request) {
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

		Param total = statClinicService.getNewTotal(param);
		List<Param> list = statClinicService.getNewList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("total", total);
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/member/all/monthList")
	public ModelAndView allMonthVisitList(HttpServletRequest request) {
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

		Param total = statClinicService.getNewTotal(param);
		List<Param> list = statClinicService.getNewMonthList(param);

		ModelAndView mav = new ModelAndView();
		mav.addObject("total", total);
		mav.addObject("list", list);
		mav.addObject("sdate", sdate);
		mav.addObject("edate", edate);

		return mav;
	}

	@GetMapping("/member/all/downloadExcel")
	public void downloadExcelAll(HttpServletRequest request, HttpServletResponse response) {
		Param param = new Param(request.getParameterMap());

		Param total = statClinicService.getNewTotal(param);
		List<Param> list = null;
		String excelTltle = "";

		if("daily".equals(param.get("gubun"))){
			list = statClinicService.getNewList(param);
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

			list = statClinicService.getNewMonthList(param);
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

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("신규 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("가입 미승인 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("승인(활성) 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("업데이트 요청 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("업데이트 반려 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("비활성 회원수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("탈퇴 회원수");
		sheet.setColumnWidth(idx, width);

		int i=0;
		int totalCnt = total.getInt("TOTAL_BEFORE");
		for (Param paramRst : list) {
			Row row = sheet.createRow(++i);

			idx = 0;
			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleCenter);
			cell.setCellValue(paramRst.get("YMD"));

			totalCnt += paramRst.getInt("DAY_CNT") - paramRst.getInt("SECEDE_CNT");
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(totalCnt));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.getInt("DAY_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.getInt("NOT_APPR_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.getInt("APPR_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.getInt("UPDATE_REQ_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.getInt("UPDATE_RTN_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.getInt("INACTIVE_CNT")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(paramRst.getInt("SECEDE_CNT")));


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
