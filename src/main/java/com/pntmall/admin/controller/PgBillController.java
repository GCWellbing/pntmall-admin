package com.pntmall.admin.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.PgBill;
import com.pntmall.admin.domain.PgBillSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.PgBillService;
import com.pntmall.admin.service.SapService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.azure.StorageUploader;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/order/pg")
public class PgBillController {
	public static final Logger logger = LoggerFactory.getLogger(PgBillController.class);
	
	@Autowired
	private PgBillService pgBillService;
		
    @Autowired
    private StorageUploader storageUploader;
		
    @Autowired
    private SapService sapService;
		
	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute PgBillSearch pgBillSearch) {
		Utils.savePath();
		
		if(StringUtils.isEmpty(pgBillSearch.getSdate())) pgBillSearch.setSdate(Utils.getTimeStampString("yyyy.MM.dd"));
		if(StringUtils.isEmpty(pgBillSearch.getEdate())) pgBillSearch.setEdate(Utils.getTimeStampString("yyyy.MM.dd"));

		List<PgBill> list = pgBillService.getList(pgBillSearch);
		Integer count = pgBillService.getCount(pgBillSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, pgBillSearch);
		
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		return mav;
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public ResultMessage upload(MultipartHttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		String uploadUrl = null;
		String[] passibleExts = { "xlsx" };

		try {
        	storageUploader.initStorageUploder(request);
       		storageUploader.setPassibleExts(passibleExts);

        	uploadUrl = storageUploader.upload("excel", "excel");

        	logger.debug("----------------- " + uploadUrl);
			if(uploadUrl == null) {
				return new ResultMessage(false, "업로드중 오류가 발생했습니다.");
			}
			
			pgBillService.create(uploadUrl, sess.getAdminNo());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, e.getMessage());
		}

		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/sap")
	@ResponseBody
	public ResultMessage sap(HttpServletRequest request) {
		try {
			sapService.settlement(new Param(request.getParameterMap()));
		} catch(Exception e) {
			return new ResultMessage(false, e.getMessage());
		}
		return new ResultMessage(true, "전송되었습니다.");
	}
	
	@GetMapping("/excel")
	@ResponseBody
	public void excel(@ModelAttribute PgBillSearch pgBillSearch) {
		pgBillSearch.setPageSize(Integer.MAX_VALUE);

		if(StringUtils.isEmpty(pgBillSearch.getSdate())) pgBillSearch.setSdate(Utils.getTimeStampString("yyyy.MM.dd"));
		if(StringUtils.isEmpty(pgBillSearch.getEdate())) pgBillSearch.setEdate(Utils.getTimeStampString("yyyy.MM.dd"));
	
		List<PgBill> list = pgBillService.getList(pgBillSearch);

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

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle bodyStyleDate = workbook.createCellStyle();
		bodyStyleDate.setAlignment(HorizontalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));

		Row headerRow = sheet.createRow(0);
		String[] headerTexts = { 
				"No.",	"지급일자", "매출일자", "상점ID", "결제수단", "결제상태", 
				"코드", "결제기관", "결제일자", "주문번호", "금액", 
				"수수료", "당기발생액", "승인번호", "구매자", "구매자ID",
				"매입/취소요청일", "비고", "상품정보", "거래번호", "SAP전송일",
				"SAP전송결과"
			};
		
		int[] headerWidth = {
				4000, 4000, 4000, 4000, 4000, 4000, 
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

			PgBill pgBill = list.get(i);

			int idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.formatMoney(pgBill.getSno()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getIssueDate());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getSaleDate());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getShopId());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getPayType());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getPayStatus());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getCode());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getPayOrgan());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getPayDate());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getOrderid());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.formatMoney(pgBill.getAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.formatMoney(pgBill.getFee()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.formatMoney(pgBill.getIssueAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getAuthNo());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getBuyer());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getBuyerId());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getReqDate());
				
			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getBigo());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getPname());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getDealNo());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getSapDate() != null ? Utils.getTimeStampString(pgBill.getSapDate(), "yyyy.MM.dd HH:mm:ss") : "");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(pgBill.getSapResult());

		}

		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=PG Bill " + Utils.formatDate(new Date(), "yyyyMMdd") + ".xlsx");

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
