package com.pntmall.admin.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Clinic;
import com.pntmall.admin.domain.ClinicAdjust;
import com.pntmall.admin.domain.ClinicAdjustDetail;
import com.pntmall.admin.domain.ClinicAdjustSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.ClinicAdjustService;
import com.pntmall.admin.service.ClinicService;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.SapService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/clinic/adjust")
public class ClinicAdjustController {
	public static final Logger logger = LoggerFactory.getLogger(ClinicAdjustController.class);
	
	@Autowired
	private ClinicAdjustService clinicAdjustService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private SapService sapService;
	
	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute ClinicAdjustSearch clinicAdjustSearch) {
		Utils.savePath();
		
		Integer thisYear = Integer.parseInt(Utils.getTimeStampString("yyyy"));
		
		if(clinicAdjustSearch.getYear() == null) clinicAdjustSearch.setYear(thisYear);
		if(clinicAdjustSearch.getQuarter() == null) clinicAdjustSearch.setQuarter(LocalDate.now().get(IsoFields.QUARTER_OF_YEAR));
//		if(clinicAdjustSearch.getStatus() == null) clinicAdjustSearch.setStatus(10);
		
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

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute ClinicAdjustSearch clinicAdjustSearch) {
		AdminSession sess = AdminSession.getInstance();
		clinicAdjustSearch.setCuser(sess.getAdminNo());
		clinicAdjustSearch.setStatus(null);
		
		try {
			Integer count = clinicAdjustService.getCount(clinicAdjustSearch);
			if(count > 0) {
				return new ResultMessage(false, "?????? ???????????? ?????????????????????.");
			}
			
			clinicAdjustService.create(clinicAdjustSearch);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "????????? ??????????????????.");
		}

		return new ResultMessage(true, "?????????????????????.");
	}
	
	@PostMapping("/modifyFee")
	@ResponseBody
	public ResultMessage modifyFee(HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		param.set("cuser", sess.getAdminNo());
		
		try {
			clinicAdjustService.modifyFee(param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "????????? ??????????????????.");
		}

		return new ResultMessage(true, "?????????????????????.");
	}
	
	@PostMapping("/modifyStatus")
	@ResponseBody
	public ResultMessage modifyStatus(HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		param.set("cuser", sess.getAdminNo());
		
		try {
			clinicAdjustService.modifyStatus(param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "????????? ??????????????????.");
		}

		return new ResultMessage(true, "?????????????????????.");
	}
	
	@GetMapping("/detail")
	public ModelAndView detail(@ModelAttribute ClinicAdjust clinicAdjust) {
		ModelAndView mav = new ModelAndView();

		List<ClinicAdjustDetail> list = clinicAdjustService.getDetailList(clinicAdjust);
		Clinic clinic = clinicService.getInfo(clinicAdjust.getMemNo());
		ClinicAdjust adjustInfo = clinicAdjustService.getInfo(clinicAdjust.getSno());
		
		mav.addObject("list", list);
		mav.addObject("adjustInfo", adjustInfo);
		mav.addObject("clinic", clinic);
		if(StringUtils.isNotEmpty(clinic.getBank())) {
			mav.addObject("bank", codeService.getName(clinic.getBank()));
		}
		
		return mav;
	}

	@PostMapping("/sendSap")
	@ResponseBody
	public ResultMessage sendSap(HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		param.set("cuser", sess.getAdminNo());
		
		try {
			sapService.fee(param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "????????? ??????????????????.");
		}

		return new ResultMessage(true, "?????????????????????.");
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
				"????????????",	"????????????", "?????????ID", "???????????????", "??????????????????", "????????????", 
				"??????????????????",  "??????????????????", "??????????????????", "??????????????????", "?????????????????????",
				"?????????????????????", "????????????", "????????????", "?????????????????????", "???????????????",
				"SAP????????????"
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
			cell.setCellValue(clinicAdjust.getYear() + "??? " + clinicAdjust.getQuarter() + "??????");

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
					statusName = "????????????";
					break;
				case 20 :
					statusName = "??????????????????";
					break;
				case 30 :
					statusName = "???????????????????????????";
					break;
				case 40 :
					statusName = "???????????????????????????";
					break;
				case 50 :
					statusName = "?????????????????????";
					break;
				case 60 :
					statusName = "???????????????";
					break;
				case 70 :
					statusName = "????????????";
					break;
				case 80 :
					statusName = "?????????";
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
		response.setHeader("Content-Disposition", "attachment;filename=adjust_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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
