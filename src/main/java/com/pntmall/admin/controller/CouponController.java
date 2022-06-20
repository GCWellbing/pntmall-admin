package com.pntmall.admin.controller;

import java.io.IOException;
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
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Coupon;
import com.pntmall.admin.domain.CouponGrade;
import com.pntmall.admin.domain.CouponMem;
import com.pntmall.admin.domain.CouponProduct;
import com.pntmall.admin.domain.CouponSearch;
import com.pntmall.admin.domain.CouponSerial;
import com.pntmall.admin.domain.CouponSerialSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CouponService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/promotion/coupon")
public class CouponController {
	public static final Logger logger = LoggerFactory.getLogger(CouponController.class);
	
    @Autowired
	private CouponService couponService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute CouponSearch couponSearch) {
		Utils.savePath();
		
		List<Coupon> list = couponService.getList(couponSearch);
		Integer count = couponService.getCount(couponSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, couponSearch);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(String couponid) {
		String mode = "create";
		Coupon coupon = new Coupon();

		if(couponid != null && !"".equals(couponid)) {
			mode = "modify";
			coupon = couponService.getInfo(couponid);
		} else {
			couponid = "";
		}
		
		List<CouponGrade> gradeList = couponService.getGradeList(couponid);
		List<CouponMem> memList = couponService.getMemList(couponid);
		List<CouponProduct> productList = couponService.getProductList(couponid);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
        mav.addObject("coupon", coupon);
		mav.addObject("mode", mode);
		mav.addObject("gradeList", gradeList);
		mav.addObject("memList", memList);
		mav.addObject("productList", productList);
		
		return mav;
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Coupon coupon, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			coupon.setCuser(sess.getAdminNo());
			couponService.create(coupon, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Coupon coupon, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			coupon.setCuser(sess.getAdminNo());
			couponService.modify(coupon, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
	
	@GetMapping("/serial")
	public ModelAndView serial(@ModelAttribute CouponSerialSearch couponSerialSearch) {
		List<CouponSerial> list = couponService.getSerialList(couponSerialSearch);
		Integer count = couponService.getSerialCount(couponSerialSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, couponSerialSearch);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		
		return mav;
	}
	
	@PostMapping("/createSerial")
	@ResponseBody
	public ResultMessage createSerial(@ModelAttribute CouponSerial couponSerial, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			couponSerial.setCuser(sess.getAdminNo());
			couponService.createSerial(couponSerial, param.getInt("qty"));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "발행되었습니다.");
	}

	@GetMapping("/excel")
	public void downloadExcel(@ModelAttribute CouponSerialSearch couponSerialSearch, HttpServletResponse response) {
		couponSerialSearch.setPageSize(Integer.MAX_VALUE);
		
		List<CouponSerial> list = couponService.getSerialList(couponSerialSearch);

		SXSSFWorkbook workbook = new SXSSFWorkbook();
		workbook.setCompressTempFiles(true);
		SXSSFSheet sheet = workbook.createSheet();
		sheet.setRandomAccessWindowSize(100);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle bodyStyle1 = workbook.createCellStyle();
		bodyStyle1.setAlignment(HorizontalAlignment.CENTER);

		CellStyle bodyStyle2 = workbook.createCellStyle();
		bodyStyle2.setAlignment(HorizontalAlignment.LEFT);

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle bodyStyleDate = workbook.createCellStyle();
		bodyStyleDate.setAlignment(HorizontalAlignment.LEFT);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy.MM.dd HH:mm:ss"));
		Row headerRow = sheet.createRow(0);
		
		String[] headerTexts = {"Serial", "발행일"};
		int[] headerWidth = {5000, 5000};

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

			CouponSerial serial = list.get(i);

			int idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyle1);
			cell.setCellValue(serial.getSerial());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleDate);
			cell.setCellValue(serial.getCdate());
		}

		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=coupon_serial.xlsx");

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
