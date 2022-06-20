package com.pntmall.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Coupon;
import com.pntmall.admin.domain.CouponSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.service.CouponService;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/promotion/couponlog")
public class CouponLogController {
	public static final Logger logger = LoggerFactory.getLogger(CouponLogController.class);
	
    @Autowired
	private CouponService couponService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute CouponSearch couponSearch) {
		Utils.savePath();
		
		List<Coupon> list = couponService.getLogList(couponSearch);
		Integer count = couponService.getLogCount(couponSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, couponSearch);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		
		return mav;
	}
	
	@GetMapping("/excel")
	@ResponseBody
	public void excel(@ModelAttribute CouponSearch couponSearch) {
		couponSearch.setPageSize(Integer.MAX_VALUE);
		List<Coupon> list = couponService.getLogList(couponSearch);
		
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		workbook.setCompressTempFiles(true);
		SXSSFSheet sheet = workbook.createSheet();
		sheet.setRandomAccessWindowSize(100);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle styleCenter = workbook.createCellStyle();
		styleCenter.setAlignment(HorizontalAlignment.CENTER);

		CellStyle styleRight = workbook.createCellStyle();
		styleRight.setAlignment(HorizontalAlignment.RIGHT);

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle bodyStyleDate = workbook.createCellStyle();
		bodyStyleDate.setAlignment(HorizontalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));

		Row headerRow = sheet.createRow(0);
		String[] headerTexts = { 
				"쿠폰유형", "쿠폰명", "아이디", "이름", "등급", "쿠폰할인금액", 
				"발급일", "만료일", "사용일"
			};
		
		int[] headerWidth = {
				4000, 4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000
			};

		for (int i = 0; i < headerTexts.length; i++) {
			String text = headerTexts[i];

			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(text);

			sheet.setColumnWidth(i, headerWidth[i]);
		}

		Cell cell;
		for(int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 1);

			Coupon coupon = list.get(i);

			int idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(coupon.getGubun() == 1 ? "제품쿠폰" : "배송비쿠폰");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(coupon.getTitle());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(coupon.getMemId());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(coupon.getMemName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(coupon.getGradeName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.formatMoney(coupon.getDiscount()) + (coupon.getDiscountType() == 1 ? "원" : "%"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.getTimeStampString(coupon.getCdate(), "yyyy.MM.dd HH:mm:ss"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(coupon.getExpire());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(coupon.getUseDate() != null ? Utils.getTimeStampString(coupon.getUseDate(), "yyyy.MM.dd HH:mm:ss") : "");
		}

		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=couponlog " + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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
