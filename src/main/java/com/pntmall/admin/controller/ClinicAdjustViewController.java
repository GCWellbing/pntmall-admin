package com.pntmall.admin.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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

import com.pntmall.admin.domain.Clinic;
import com.pntmall.admin.domain.ClinicAdjust;
import com.pntmall.admin.domain.ClinicAdjustDetail;
import com.pntmall.admin.domain.ClinicAdjustSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.service.ClinicAdjustService;
import com.pntmall.admin.service.ClinicService;
import com.pntmall.admin.service.CodeService;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/clinic/adjustview")
public class ClinicAdjustViewController {
	public static final Logger logger = LoggerFactory.getLogger(ClinicAdjustViewController.class);
	
	@Autowired
	private ClinicAdjustService clinicAdjustService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private CodeService codeService;
	
	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute ClinicAdjustSearch clinicAdjustSearch) {
		Utils.savePath();
		
		Integer thisYear = Integer.parseInt(Utils.getTimeStampString("yyyy"));
		
		if(clinicAdjustSearch.getYear1() == null) clinicAdjustSearch.setYear1(thisYear);
		if(clinicAdjustSearch.getQuarter1() == null) clinicAdjustSearch.setQuarter1(LocalDate.now().get(IsoFields.QUARTER_OF_YEAR));
		if(clinicAdjustSearch.getYear2() == null) clinicAdjustSearch.setYear2(thisYear);
		if(clinicAdjustSearch.getQuarter2() == null) clinicAdjustSearch.setQuarter2(LocalDate.now().get(IsoFields.QUARTER_OF_YEAR));
		
		List<ClinicAdjust> list = clinicAdjustService.getList(clinicAdjustSearch);
		Integer count = clinicAdjustService.getCount(clinicAdjustSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, clinicAdjustSearch);
		
		Integer totAdjustAmt = 0;
		for(ClinicAdjust clinicAdjust : list) {
			totAdjustAmt += clinicAdjust.getAdjustAmt();
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("thisYear", thisYear);
		mav.addObject("totAdjustAmt", totAdjustAmt);
		
		return mav;
	}

	@GetMapping("/detail")
	public ModelAndView detail(@ModelAttribute ClinicAdjust clinicAdjust) {
		ModelAndView mav = new ModelAndView();

		List<ClinicAdjustDetail> list = clinicAdjustService.getDetailList(clinicAdjust);
		Clinic clinic = clinicService.getInfo(clinicAdjust.getMemNo());
		
		mav.addObject("list", list);
		mav.addObject("clinic", clinic);
		mav.addObject("bank", codeService.getName(clinic.getBank()));
		
		return mav;
	}

	@GetMapping("/excel")
	@ResponseBody
	public void excel(@ModelAttribute ClinicAdjustSearch clinicAdjustSearch) {
		clinicAdjustSearch.setPageSize(Integer.MAX_VALUE);
		List<ClinicAdjust> list = clinicAdjustService.getList(clinicAdjustSearch);

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

		Row headerRow = sheet.createRow(0);
		String[] headerTexts = { 
				"정산기간",	"병의원명", "병의원ID", "총매출금액", "일반매출금액", "총출하가", 
				"이용수수료율",  "홍보수수료율", "픽업매출금액", "픽업수수료율", "닥터팩매출금액",
				"닥터팩수수요율", "정산금액", "정산상태", "정산상태마감일", "지급완료일",
				"SAP전송결과"
			};
		int[] headerWidth = {
				4000, 4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 4000, 
				4000
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

			ClinicAdjust clinicAdjust = list.get(i);

			int idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinicAdjust.getYear() + "년 " + clinicAdjust.getQuarter() + "분기");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinicAdjust.getClinicName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinicAdjust.getMemId());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(clinicAdjust.getTotSaleAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(clinicAdjust.getSaleAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(clinicAdjust.getTotSupplyAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(clinicAdjust.getFee());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(clinicAdjust.getPromoFee());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(clinicAdjust.getPickupSaleAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(clinicAdjust.getPickupFee());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(clinicAdjust.getDpackSaleAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(clinicAdjust.getDpackFee());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(clinicAdjust.getAdjustAmt()));

			String statusName = "";
			switch(clinicAdjust.getStatus()) {
				case 10 :
					statusName = "정산예정";
					break;
				case 20 :
					statusName = "정산확인요청";
					break;
				case 30 :
					statusName = "세금계산서발행요청";
					break;
				case 40 :
					statusName = "세금계산서수정요청";
					break;
				case 50 :
					statusName = "세금계산서확인";
					break;
				case 60 :
					statusName = "지급준비중";
					break;
				case 70 :
					statusName = "지급완료";
					break;
				case 80 :
					statusName = "미정산";
					break;
			}
			
			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(statusName);

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinicAdjust.getDeadline());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinicAdjust.getPaymentDate());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(clinicAdjust.getSapResult());

		}

		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=adjustview_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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
