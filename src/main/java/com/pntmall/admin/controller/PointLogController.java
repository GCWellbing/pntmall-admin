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

import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.Point;
import com.pntmall.admin.domain.PointSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.PointService;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/promotion/pointlog")
public class PointLogController {
	public static final Logger logger = LoggerFactory.getLogger(PointLogController.class);
	
    @Autowired
	private PointService pointService;

    @Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute PointSearch pointSearch) {
		Utils.savePath();
		
		List<Point> list = pointService.getLogList(pointSearch);
		Integer count = pointService.getLogCount(pointSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, pointSearch);
		List<Code> reasonList = codeService.getList2("019");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("reasonList", reasonList);
		
		return mav;
	}
	
	@GetMapping("/excel")
	@ResponseBody
	public void excel(@ModelAttribute PointSearch pointSearch) {
		pointSearch.setPageSize(Integer.MAX_VALUE);
		List<Point> list = pointService.getLogList(pointSearch);
		
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
				"사유",	"아이디", "이름", "등급", "유형", "포인트", 
				"주문번호", "적립/소진일", "만료일"
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

			Point point = list.get(i);

			int idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(point.getReasonName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(point.getMemId());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(point.getMemName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(point.getGradeName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(point.getPoint() >= 0 ? "적립" : "소진");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(point.getPoint()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(point.getOrderid());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.getTimeStampString(point.getCdate(), "yyyy.MM.dd HH:mm:ss"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(point.getEdate());
		}

		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=pointlog " + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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
