package com.pntmall.admin.controller;

import com.pntmall.admin.service.HealthStatService;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/stat")
public class HealthStatController {
	public static final Logger logger = LoggerFactory.getLogger(HealthStatController.class);

	@Autowired
	private HealthStatService healthStatService;

	@GetMapping("/healthCheck/list")
	public ModelAndView healthList(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();

		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy.MM.dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -6);
		String sdate = sdformat.format(cal.getTime());
		param.set("tab", "D");
		if(StringUtils.isEmpty(param.get("gubun"))) param.set("gubun", "1");
		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));

		List<Param> list = healthStatService.getHealthCheckList(param);
		mav.addObject("list", list);

		return mav;
	}

	@GetMapping("/healthCheck/list2")
	public ModelAndView healthList2(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		param.set("tab", "M");
		if(StringUtils.isEmpty(param.get("gubun"))) param.set("gubun", "1");
		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", Utils.getTimeStampString("yyyy.MM"));
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM"));

		List<Param> list = healthStatService.getHealthCheckList(param);
		mav.addObject("list", list);

		return mav;
	}

	@GetMapping("/healthCheck/excel")
	@ResponseBody
	public void healthExcel(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		List<Param> list = healthStatService.getHealthCheckList(param);

		SXSSFWorkbook workbook = new SXSSFWorkbook();
		workbook.setCompressTempFiles(true);
		SXSSFSheet sheet = workbook.createSheet();
		sheet.setRandomAccessWindowSize(100);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle styleCenter = workbook.createCellStyle();
		styleCenter.setAlignment(HorizontalAlignment.CENTER);
		styleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle styleRight = workbook.createCellStyle();
		styleRight.setAlignment(HorizontalAlignment.RIGHT);

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle bodyStyleDate = workbook.createCellStyle();
		bodyStyleDate.setAlignment(HorizontalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
				createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));

		Row headerRow = sheet.createRow(0);
		int idx = -1;
		int width = 4000;

		Cell cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("날짜");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("총 완료건수");
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
		cell.setCellValue("70대이상");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("남자");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("여자");
		sheet.setColumnWidth(idx, width);

		int r = 0;
		for(Param p : list) {
			Row row = sheet.createRow(++r);
			idx = 0;

			if( r == 1) {

				cell = row.createCell(idx++);
				cell.setCellStyle(styleCenter);
				cell.setCellValue(p.get("m"));
				if(p.get("gubun").equals("1")){
					sheet.addMergedRegion(new CellRangeAddress(r, r + 2, 0, 0));
				}

			}else{
				if(p.get("gubun").equals("1")){
				cell = row.createCell(0);
				cell.setCellStyle(styleCenter);
				cell.setCellValue(p.get("m"));

					sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 0, 0));
				}

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(p.get("gubun").equals("1") ? "총 완료건수" : "총 완료자수");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("10대");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("20대");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("30대");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("40대");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("50대");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("60대");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("70대이상");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("남자");

				cell = row.createCell(++idx);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("여자");

				row = sheet.createRow(++r);
				idx = 1;
			}


			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("teens")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("twenty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("thirty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("forty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("fifty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("sixty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("seventy")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("man")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("woman")));

		}

		String tab = param.get("tab").equals("M") ? "month_" : "day_";
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=health_check_" + tab + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/healthTopic/list")
	public ModelAndView healthTopic(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy.MM.dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -6);
		String sdate = sdformat.format(cal.getTime());

		param.set("tab", "D");
		if(StringUtils.isEmpty(param.get("gubun"))) param.set("gubun", "1");
		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", sdate);
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));
		List<Param> list = new ArrayList<>();
		if (param.get("gubun").equals("2")) {

			list = healthStatService.getHealthTopicList2(param);
		}else{

			list = healthStatService.getHealthTopicList(param);
		}

		mav.addObject("list", list);

		return mav;
	}

	@GetMapping("/healthTopic/list2")
	public ModelAndView healthTopic2(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		param.set("tab", "M");
		if(StringUtils.isEmpty(param.get("gubun"))) param.set("gubun", "1");
		if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", Utils.getTimeStampString("yyyy.MM"));
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM"));

		List<Param> list = new ArrayList<>();
		if (param.get("gubun").equals("2")) {

			list = healthStatService.getHealthTopicList2(param);
		}else{

			list = healthStatService.getHealthTopicList(param);
		}

		mav.addObject("list", list);

		return mav;
	}

	@GetMapping("/healthTopic/excel")
	@ResponseBody
	public void healthTopicExcel(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

		List<Param> list = new ArrayList<>();
		String gubun = param.get("gubun", "1");
		if (gubun.equals("2")) {

			list = healthStatService.getHealthTopicList2(param);
		}else{

			list = healthStatService.getHealthTopicList(param);
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

		CellStyle styleCenter = workbook.createCellStyle();
		styleCenter.setAlignment(HorizontalAlignment.CENTER);
		styleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle styleRight = workbook.createCellStyle();
		styleRight.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle styleLeft = workbook.createCellStyle();
        styleLeft.setAlignment(HorizontalAlignment.LEFT);

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle bodyStyleDate = workbook.createCellStyle();
		bodyStyleDate.setAlignment(HorizontalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
				createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));

		Row headerRow = sheet.createRow(0);
		int idx = -1;
		int width = 4000;

		Cell cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("건강주제");
		sheet.setColumnWidth(idx, width * 2);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		if (gubun.equals("2")) {
			cell = headerRow.createCell(++idx);
			cell.setCellStyle(headerStyle);
			cell.setCellValue("관심정보");
            sheet.setColumnWidth(idx, width * 4);
			sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		}

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("선택 건수");
        sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("연령대");
        sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 6));

		idx += 6;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("성별");
        sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 1));

		headerRow = sheet.createRow(1);
		idx = gubun.equals("2") ? 2 : 1;
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
		cell.setCellValue("70대이상");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("남자");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("여자");
		sheet.setColumnWidth(idx, width);

		int r = 1;
		String healthNo = "";
		for(Param p : list) {
			Row row = sheet.createRow(++r);
			idx = 0;

			if (gubun.equals("2")) {
				if (healthNo.equals("") || !healthNo.equals(p.get("health_no"))) {
					cell = row.createCell(idx++);
					cell.setCellStyle(styleCenter);
					cell.setCellValue(p.get("title"));
					if (p.getInt("exam_cnt") > 1) {

						sheet.addMergedRegion(new CellRangeAddress(r, r + p.getInt("exam_cnt") - 1, 0, 0));
					}
				}else{
					idx++;
				}

				cell = row.createCell(idx++);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(p.get("example"));
			}else{
				cell = row.createCell(idx++);
				cell.setCellStyle(styleLeft);
				cell.setCellValue(p.get("title"));
			}


			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("teens")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("twenty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("thirty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("forty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("fifty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("sixty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("seventy")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("man")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("woman")));

			healthNo = p.get("health_no");

		}

		String tab = param.get("tab").equals("M") ? "month_" : "day_";
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=health_topic_" + tab + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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
