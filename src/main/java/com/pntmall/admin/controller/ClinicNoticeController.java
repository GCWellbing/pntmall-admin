package com.pntmall.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.pntmall.admin.domain.NoticeImg;
import com.pntmall.admin.domain.NoticeSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.ClinicNoticeService;
import com.pntmall.admin.service.CodeService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/clinic/notice")
public class ClinicNoticeController {
	public static final Logger logger = LoggerFactory.getLogger(ClinicNoticeController.class);

    @Autowired
	private ClinicNoticeService noticeService;

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

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer noticeNo) {
		String mode = "create";
		List<Code> cateList = codeService.getList2("004");
		Notice notice = new Notice();
		List<NoticeImg> noticeImgList = new ArrayList<NoticeImg> ();
		if(noticeNo != null && noticeNo > 0) {
			mode = "modify";
			notice = noticeService.getInfo(noticeNo);
			noticeImgList = noticeService.getNoticeImgList(noticeNo);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("notice", notice);
		mav.addObject("noticeImgList", noticeImgList);
		mav.addObject("cateList", cateList);
		mav.addObject("retrivePath", Utils.retrivePath("notice"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Notice notice, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			notice.setFixYn("Y".equals(notice.getFixYn())?"Y":"N");
			notice.setCuser(sess.getAdminNo());
			noticeService.create(notice, param);
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
		Param param = new Param(request.getParameterMap());

		try {
			notice.setFixYn("Y".equals(notice.getFixYn())?"Y":"N");
			notice.setCuser(sess.getAdminNo());
			noticeService.modify(notice, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}



}
