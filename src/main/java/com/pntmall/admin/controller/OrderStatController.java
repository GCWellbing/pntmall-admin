package com.pntmall.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.pntmall.admin.domain.Category;
import com.pntmall.admin.domain.Clinic;
import com.pntmall.admin.service.CategoryService;
import com.pntmall.admin.service.ClinicService;
import com.pntmall.admin.service.OrderStatService;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/stat")
public class OrderStatController {
	public static final Logger logger = LoggerFactory.getLogger(OrderStatController.class);
	
	@Autowired
	private OrderStatService orderStatService;
		
	@Autowired
	private CategoryService categoryService;
		
	@Autowired
	private ClinicService clinicService;
		
	@GetMapping("/saleRank/list")
	public ModelAndView saleRank(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		
		if(StringUtils.isEmpty(param.get("sdate"))) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -6);
			param.set("sdate", Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
		}
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));

		List<Param> list = orderStatService.getSaleRankList(param);
		List<Category> cate1List= categoryService.getList(0);
		if(StringUtils.isNotEmpty(param.get("cate1"))) {
			mav.addObject("cate2List", categoryService.getList(param.getInt("cate1")));
		}
		mav.addObject("list", list);
		mav.addObject("cate1List", cate1List);
		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));
		
		return mav;
	}
	
	@GetMapping("/saleRank/excel")
	@ResponseBody
	public void saleRankExcel(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		List<Param> list = orderStatService.getSaleRankList(param);

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
		cell.setCellValue("순위");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("상품코드");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("제품명");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("상품금액");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 4;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매수량");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 4;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		headerRow = sheet.createRow(1);
		idx = 2;
		
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
		cell.setCellValue("앱");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("합계");
		sheet.setColumnWidth(idx, width);

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
		cell.setCellValue("앱");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("합계");
		sheet.setColumnWidth(idx, width);

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
		cell.setCellValue("앱");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("합계");
		sheet.setColumnWidth(idx, width);

		for(int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 2);
			Param p = list.get(i);

			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(i + 1);

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("pno"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("pname"));
		
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_pc_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_mo_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_ap_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_pc_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_mo_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_ap_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_pc_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_mo_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_ap_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_cnt")));

		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=sale_rank_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/sale/list")
	public ModelAndView saleList(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		
		if(param.getInt("gubun") == 3) {
			if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", Utils.getTimeStampString("yyyy.MM"));
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM"));
		} else {
			if(StringUtils.isEmpty(param.get("sdate"))) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -6);
				param.set("sdate", Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
			}
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));
		}
		
		List<Param> list = orderStatService.getSaleList(param);
		mav.addObject("list", list);
		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));
		
		return mav;
	}
	
	@GetMapping("/sale/excel")
	@ResponseBody
	public void saleExcel(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		List<Param> list = orderStatService.getSaleList(param);

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
		cell.setCellValue(param.getInt("gubun") == 1 ? "날짜" : (param.getInt("gubun") == 2 ? "시간" : "월"));
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구분");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("매출금액");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width * 7);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 6));

		idx += 7;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("환불금액");
		sheet.setColumnWidth(idx, width);

		headerRow = sheet.createRow(1);
		idx = 2;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("상품판매가");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("상품할인(-)");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("결제금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("배송비");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("배송비할인(-)");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("결제금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매총액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("환불총액(-)");
		sheet.setColumnWidth(idx, width);

		int r = 1;
		for(Param p : list) {
			Row row = sheet.createRow(++r);
			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("odate"));
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 0, 0));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("PC");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("pc_pay_amt") + p.getInt("pc_ship_pay_amt") - p.getInt("pc_refund")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_ship_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_ship_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_ship_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_refund")));

			row = sheet.createRow(++r);
			idx = 1;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("모바일");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("mo_pay_amt") + p.getInt("mo_ship_pay_amt") - p.getInt("mo_refund")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_ship_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_ship_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_ship_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_refund")));

			row = sheet.createRow(++r);
			idx = 1;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("앱");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("ap_pay_amt") + p.getInt("ap_ship_pay_amt") - p.getInt("ap_refund")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_ship_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_ship_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_ship_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_refund")));

			row = sheet.createRow(++r);
			idx = 1;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("총액");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("pay_amt") + p.getInt("ship_pay_amt") - p.getInt("refund")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ship_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ship_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ship_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("refund")));
		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + (param.getInt("gubun") == 1 ? "daily" : (param.getInt("gubun") == 2 ? "hourly" : "monthly")) + "_sale_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/order/list")
	public ModelAndView orderList(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		
		if(param.getInt("gubun") == 3) {
			if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", Utils.getTimeStampString("yyyy.MM"));
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM"));
		} else {
			if(StringUtils.isEmpty(param.get("sdate"))) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -6);
				param.set("sdate", Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
			}
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));
		}

		List<Param> list = orderStatService.getOrderList(param);
		mav.addObject("list", list);
		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));
		
		return mav;
	}
	
	@GetMapping("/order/excel")
	@ResponseBody
	public void orderExcel1(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		List<Param> list = orderStatService.getOrderList(param);

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
		cell.setCellValue(param.getInt("gubun") == 1 ? "날짜" : (param.getInt("gubun") == 2 ? "시간" : "월"));
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("전체");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));
		idx += 3;

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("PC");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));
		idx += 3;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("모바일");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));
		idx += 3;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("앱");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		headerRow = sheet.createRow(1);
		idx = 0;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		int r = 1;
		for(Param p : list) {
			Row row = sheet.createRow(++r);
			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("odate"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("pc_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("mo_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("ap_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty")));

		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + (param.getInt("gubun") == 1 ? "daily" : (param.getInt("gubun") == 2 ? "hourly" : "monthly")) + "_order_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/clinic/rank/list")
	public ModelAndView clinicRank(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		
		if(StringUtils.isEmpty(param.get("sdate"))) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -6);
			param.set("sdate", Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
		}
		if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));

		if(!"".equals(param.get("memId"))) {
			Clinic clinic = clinicService.getInfo(param.get("memId"));
			if(clinic != null) {
				mav.addObject("clinic", clinic);
				param.set("clinicMemNo", clinic.getMemNo());
				List<Param> list = orderStatService.getSaleRankList(param);
				mav.addObject("list", list);
			}
		}		
		
		List<Category> cate1List= categoryService.getList(0);
		if(StringUtils.isNotEmpty(param.get("cate1"))) {
			mav.addObject("cate2List", categoryService.getList(param.getInt("cate1")));
		}
		mav.addObject("cate1List", cate1List);
		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));

		return mav;
	}
	
	@GetMapping("/clinic/rank/excel")
	@ResponseBody
	public void clinicRankExcel(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		Clinic clinic = clinicService.getInfo(param.get("memId"));
		List<Param> list = new ArrayList<Param>();

		if(clinic != null) {
			param.set("clinicMemNo", clinic.getMemNo());
			list = orderStatService.getSaleRankList(param);
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
		cell.setCellValue("순위");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원ID");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원명");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("상품코드");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("제품명");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("상품금액");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 4;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매수량");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 4;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		headerRow = sheet.createRow(1);
		idx = 4;
		
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
		cell.setCellValue("앱");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("합계");
		sheet.setColumnWidth(idx, width);

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
		cell.setCellValue("앱");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("합계");
		sheet.setColumnWidth(idx, width);

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
		cell.setCellValue("앱");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("합계");
		sheet.setColumnWidth(idx, width);

		for(int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 2);
			Param p = list.get(i);

			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(i + 1);

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinic.getMemId());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinic.getClinicName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("pno"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("pname"));
		
			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_pc_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_mo_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_ap_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_pc_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_mo_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_ap_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_pc_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_mo_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_ap_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("tot_cnt")));
		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=clinic_rank_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/clinic/sale/list")
	public ModelAndView clinicSaleList(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		
		if(param.getInt("gubun") == 3) {
			if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", Utils.getTimeStampString("yyyy.MM"));
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM"));
		} else {
			if(StringUtils.isEmpty(param.get("sdate"))) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -6);
				param.set("sdate", Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
			}
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));
		}

		if(!"".equals(param.get("memId"))) {
			Clinic clinic = clinicService.getInfo(param.get("memId"));
			if(clinic != null) {
				mav.addObject("clinic", clinic);
				param.set("clinicMemNo", clinic.getMemNo());
				List<Param> list = orderStatService.getSaleList(param);
				mav.addObject("list", list);
			}
		}		

		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));

		return mav;
	}
	
	@GetMapping("/clinic/sale/excel")
	@ResponseBody
	public void clinicSaleExcel(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		Clinic clinic = clinicService.getInfo(param.get("memId"));
		List<Param> list = new ArrayList<Param>();

		if(clinic != null) {
			param.set("clinicMemNo", clinic.getMemNo());
			list = orderStatService.getSaleList(param);
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
		cell.setCellValue(param.getInt("gubun") == 1 ? "날짜" : (param.getInt("gubun") == 2 ? "시간" : "월"));
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원ID");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원명");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구분");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("매출금액");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width * 7);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 6));

		idx += 7;
		cell = headerRow.createCell(idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("환불금액");
		sheet.setColumnWidth(idx, width);

		headerRow = sheet.createRow(1);
		idx = 4;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("상품판매가");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("상품할인(-)");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("결제금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("배송비");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("배송비할인(-)");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("결제금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매총액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("환불총액(-)");
		sheet.setColumnWidth(idx, width);

		int r = 1;
		for(Param p : list) {
			Row row = sheet.createRow(++r);
			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("odate"));
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 0, 0));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinic.getMemId());
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 1, 1));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinic.getClinicName());
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 2, 2));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("PC");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("pc_pay_amt") + p.getInt("pc_ship_pay_amt") - p.getInt("pc_refund")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_ship_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_ship_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_ship_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_refund")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("모바일");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("mo_pay_amt") + p.getInt("mo_ship_pay_amt") - p.getInt("mo_refund")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_ship_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_ship_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_ship_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_refund")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("앱");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("ap_pay_amt") + p.getInt("ap_ship_pay_amt") - p.getInt("ap_refund")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_ship_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_ship_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_ship_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_refund")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("총액");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("pay_amt") + p.getInt("ship_pay_amt") - p.getInt("refund")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ship_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ship_discount")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ship_pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pay_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("refund")));
		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + (param.getInt("gubun") == 1 ? "daily" : (param.getInt("gubun") == 2 ? "hourly" : "monthly")) + "_clinic_sale_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/clinic/order/list")
	public ModelAndView clinicOrderList(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
		ModelAndView mav = new ModelAndView();
		
		if(param.getInt("gubun") == 3) {
			if(StringUtils.isEmpty(param.get("sdate"))) param.set("sdate", Utils.getTimeStampString("yyyy.MM"));
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM"));
		} else {
			if(StringUtils.isEmpty(param.get("sdate"))) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -6);
				param.set("sdate", Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
			}
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));
		}

		if(!"".equals(param.get("memId"))) {
			Clinic clinic = clinicService.getInfo(param.get("memId"));
			if(clinic != null) {
				mav.addObject("clinic", clinic);
				param.set("clinicMemNo", clinic.getMemNo());
				List<Param> list = orderStatService.getOrderList(param);
				mav.addObject("list", list);
			}
		}		
		
		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));

		return mav;
	}
	
	@GetMapping("/clinic/order/excel")
	@ResponseBody
	public void clinicOrderExcel(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		Clinic clinic = clinicService.getInfo(param.get("memId"));
		List<Param> list = new ArrayList<Param>();

		if(clinic != null) {
			param.set("clinicMemNo", clinic.getMemNo());
			list = orderStatService.getOrderList(param);
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
		cell.setCellValue(param.getInt("gubun") == 1 ? "날짜" : (param.getInt("gubun") == 2 ? "시간" : "월"));
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("전체");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));
		idx += 3;

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("PC");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));
		idx += 3;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("모바일");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));
		idx += 3;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("앱");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		headerRow = sheet.createRow(1);
		idx = 0;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		int r = 1;
		for(Param p : list) {
			Row row = sheet.createRow(++r);
			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("odate"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("pc_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("mo_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.getInt("ap_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty")));

		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + (param.getInt("gubun") == 1 ? "daily" : (param.getInt("gubun") == 2 ? "hourly" : "monthly")) + "_clinic_order_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/clinic/age/list")
	public ModelAndView clinicAgeList(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));
		}

		if(!"".equals(param.get("memId"))) {
			Clinic clinic = clinicService.getInfo(param.get("memId"));
			if(clinic != null) {
				mav.addObject("clinic", clinic);
				param.set("clinicMemNo", clinic.getMemNo());
				List<Param> list = orderStatService.getOrderAgeList(param);
				mav.addObject("list", list);
			}
		}		
		
		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));

		return mav;
	}

	@GetMapping("/clinic/age/excel")
	@ResponseBody
	public void clinicAgeExcel(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		Clinic clinic = clinicService.getInfo(param.get("memId"));
		List<Param> list = new ArrayList<Param>();

		if(clinic != null) {
			param.set("clinicMemNo", clinic.getMemNo());
			list = orderStatService.getOrderAgeList(param);
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
		cell.setCellValue(param.getInt("gubun") == 1 ? "날짜" : "월");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원ID");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원명");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구분");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("10대");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("20대");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("30대");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("40대");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("50대");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("60대");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("70대");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("알수없음");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		headerRow = sheet.createRow(1);
		idx = 3;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);



		int r = 1;
		for(Param p : list) {
			Row row = sheet.createRow(++r);
			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("odate"));
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 0, 0));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinic.getMemId());
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 1, 1));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinic.getClinicName());
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 2, 2));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("PC");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_amt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_cnt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_mem_cnt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_qty80")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("모바일");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_amt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_cnt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_mem_cnt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_qty80")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("앱");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_amt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_cnt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_mem_cnt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_qty80")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("총액");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty10")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty20")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty30")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty40")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty50")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty60")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty70")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("amt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("cnt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mem_cnt80")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("qty80")));
		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + (param.getInt("gubun") == 1 ? "daily" : "monthly") + "_clinic_age_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/clinic/gender/list")
	public ModelAndView clinicGenderList(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(param.get("edate"))) param.set("edate", Utils.getTimeStampString("yyyy.MM.dd"));
		}

		if(!"".equals(param.get("memId"))) {
			Clinic clinic = clinicService.getInfo(param.get("memId"));
			if(clinic != null) {
				mav.addObject("clinic", clinic);
				param.set("clinicMemNo", clinic.getMemNo());
				List<Param> list = orderStatService.getOrderGenderList(param);
				mav.addObject("list", list);
			}
		}		
		
		mav.addObject("sdate", param.get("sdate"));
		mav.addObject("edate", param.get("edate"));

		return mav;
	}

	@GetMapping("/clinic/gender/excel")
	@ResponseBody
	public void clinicGenderExcel(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		Clinic clinic = clinicService.getInfo(param.get("memId"));
		List<Param> list = new ArrayList<Param>();

		if(clinic != null) {
			param.set("clinicMemNo", clinic.getMemNo());
			list = orderStatService.getOrderGenderList(param);
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
		cell.setCellValue(param.getInt("gubun") == 1 ? "날짜" : "월");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원ID");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("병의원명");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구분");
		sheet.setColumnWidth(idx, width);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, idx, idx));

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("남자");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("여자");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		idx += 3;
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("미확인");
		sheet.setColumnWidth(idx, width * 4);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, idx, idx + 3));

		headerRow = sheet.createRow(1);
		idx = 3;
		
		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("판매금액");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매건수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매자수");
		sheet.setColumnWidth(idx, width);

		cell = headerRow.createCell(++idx);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("구매개수");
		sheet.setColumnWidth(idx, width);

		int r = 1;
		for(Param p : list) {
			Row row = sheet.createRow(++r);
			idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(p.get("odate"));
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 0, 0));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinic.getMemId());
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 1, 1));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinic.getClinicName());
			sheet.addMergedRegion(new CellRangeAddress(r, r + 3, 2, 2));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("PC");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_m_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_m_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_m_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_m_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_w_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_w_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_w_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_w_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_n_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_n_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_n_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("pc_n_qty")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("모바일");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_m_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_m_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_m_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_m_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_w_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_w_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_w_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_w_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_n_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_n_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_n_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("mo_n_qty")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("앱");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_m_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_m_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_m_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_m_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_w_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_w_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_w_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_w_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_n_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_n_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_n_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("ap_n_qty")));

			row = sheet.createRow(++r);
			idx = 3;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("총액");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("m_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("m_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("m_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("m_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("w_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("w_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("w_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("w_qty")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("n_amt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("n_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("n_mem_cnt")));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(p.get("n_qty")));
		}
		
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + (param.getInt("gubun") == 1 ? "daily" : "monthly") + "_clinic_gender_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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
