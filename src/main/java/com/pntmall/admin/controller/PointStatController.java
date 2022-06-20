package com.pntmall.admin.controller;

import java.io.IOException;
import java.util.Calendar;
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

import com.pntmall.admin.service.PointStatService;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/stat")
public class PointStatController {
	public static final Logger logger = LoggerFactory.getLogger(PointStatController.class);
	
	@Autowired
	private PointStatService pointStatService;
		
	@GetMapping("/point/list")
	public ModelAndView list(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		
		if(param.getInt("gubun") == 2) {
			if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", Utils.getTimeStampString("yyyy.MM"));
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM"));
		} else {
			if(StringUtils.isEmpty(param.get("sdate"))) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -6);
				param.set("sdate", Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
			}
//			if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", Utils.getTimeStampString("yyyy.MM.dd"));
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));
		}

		List<Param> list = pointStatService.getList(param);
		mav.addObject("list", list);
		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));
		
		return mav;
	}
	
	@GetMapping("/point/excel")
	@ResponseBody
	public void excel(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		List<Param> list = pointStatService.getList(param);

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
		cell.setCellValue("전여포인트");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("지급건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("지급금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("사용건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("사용금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("소멸건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("소멸금액");
		sheet.setColumnWidth(idx, width);

		for(int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 1);
			Param p = list.get(i);

			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("cdate"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("point")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt1")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("sum1")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt2")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("sum2")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt3")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("sum3")));

		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=point_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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
