package com.pntmall.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.Notice;
import com.pntmall.admin.domain.NoticeComment;
import com.pntmall.admin.domain.NoticeSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.NoticeService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/cs/notice")
public class NoticeController {
	public static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
	private NoticeService noticeService;

    @Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute NoticeSearch noticeSearch) {
		Utils.savePath("notice");
		logger.debug("list noticeSearch:"+noticeSearch.getFromCdate()+"::"+noticeSearch.getToCdate());
		List<Code> cateList = codeService.getList2("004");

		List<Notice> list = noticeService.getList(noticeSearch);
		Integer count = noticeService.getCount(noticeSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, noticeSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("cateList", cateList);
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("noticeSearch", noticeSearch);
		mav.addObject("retrivePath", Utils.retrivePath("notice"));

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer noticeNo) {
		String mode = "create";
		List<Code> cateList = codeService.getList2("004");
		Notice notice = new Notice();
		List<NoticeComment> noticeCommentList = new ArrayList<NoticeComment>();
		if(noticeNo != null && noticeNo > 0) {
			mode = "modify";
			notice = noticeService.getInfo(noticeNo);
			noticeCommentList = noticeService.getNoticeCommentList(noticeNo);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("notice", notice);
		mav.addObject("cateList", cateList);
		mav.addObject("noticeCommentList", noticeCommentList);
		mav.addObject("retrivePath", Utils.retrivePath("notice"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Notice notice) {
		AdminSession sess = AdminSession.getInstance();

		try {
			notice.setFixYn("Y".equals(notice.getFixYn())?"Y":"N");
			notice.setCuser(sess.getAdminNo());
			noticeService.create(notice);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Notice notice, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			notice.setFixYn("Y".equals(notice.getFixYn())?"Y":"N");
			notice.setCuser(sess.getAdminNo());
			noticeService.modify(notice);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


	@PostMapping("/setStatusNoticeComment")
	@ResponseBody
	public ResultMessage setStatusNoticeComment(@ModelAttribute NoticeComment noticeComment) {
		AdminSession sess = AdminSession.getInstance();
		try {
			noticeComment.setCuser(sess.getAdminNo());
			noticeService.setStatusNoticeComment(noticeComment);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "처리되었습니다.");
	}

	@GetMapping("/downloadExcel")
	public void downloadExcel(Integer noticeNo, HttpServletResponse response) {
		logger.debug("downloadExcel search:");

		List<NoticeComment> noticeCommentList = noticeService.getNoticeCommentList(noticeNo);

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
			    createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));
		Row headerRow = sheet.createRow(0);
		String[] headerTexts = {"공지사항 No", "댓글 No", "내용", "공개여부", "등록자", "등록일", "수정자", "수정일"};
		int[] headerWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};

		for (int i = 0; i < headerTexts.length; i++) {
			String text = headerTexts[i];

			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(text);

			sheet.setColumnWidth(i, headerWidth[i]);
		}

		Cell cell;
		for (int i=0; i<noticeCommentList.size(); i++) {
			Row row = sheet.createRow(i + 1);

			NoticeComment noticeComment = noticeCommentList.get(i);

			int idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyle1);
			cell.setCellValue(noticeComment.getNoticeNo());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyle1);
			cell.setCellValue(noticeComment.getCommentNo());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyle1);
			cell.setCellValue(noticeComment.getComment());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyle1);
			cell.setCellValue(noticeComment.getStatusName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyle1);
			cell.setCellValue(noticeComment.getCuserName());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleDate);
			cell.setCellValue(noticeComment.getCdate());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyle1);
			cell.setCellValue(noticeComment.getUuser());

			cell = row.createCell(idx++);
			cell.setCellStyle(bodyStyleDate);
			cell.setCellValue(noticeComment.getUdate());

		}

		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=PustItemList.xlsx");

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
