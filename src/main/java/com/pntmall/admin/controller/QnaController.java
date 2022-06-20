package com.pntmall.admin.controller;

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
import com.pntmall.admin.domain.Qna;
import com.pntmall.admin.domain.QnaImg;
import com.pntmall.admin.domain.QnaSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.QnaService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping({"/cs/qna","/clinic/qna"})
public class QnaController {
	public static final Logger logger = LoggerFactory.getLogger(QnaController.class);

    @Autowired
	private QnaService qnaService;

    @Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute QnaSearch qnaSearch, HttpServletRequest request) {
		Utils.savePath("qna");
		if(("/cs/qna/list").equals(request.getRequestURI())){
			qnaSearch.setClinicYn("N");
		}else if(("/clinic/qna/list").equals(request.getRequestURI())){
			qnaSearch.setClinicYn("Y");
		}else {
			qnaSearch.setClinicYn("");
		}

		logger.debug("list"+qnaSearch+"---"+request.getRequestURI());

		List<Code> cateList1 = codeService.getList2("014");
		List<Code> cateList2 = codeService.getList2("015");

		List<Qna> list = qnaService.getList(qnaSearch);
		Integer count = qnaService.getCount(qnaSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, qnaSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("cateList1", cateList1);
		mav.addObject("cateList2", cateList2);
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("qnaSearch", qnaSearch);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(@ModelAttribute QnaSearch qnaSearch, HttpServletRequest request) {
		if(("/cs/qna/list").equals(request.getRequestURI())){
			qnaSearch.setClinicYn("N");
		}else if(("/clinic/qna/list").equals(request.getRequestURI())){
			qnaSearch.setClinicYn("Y");
		}else {
			qnaSearch.setClinicYn("");
		}

		String mode = "modify";
		Qna qna = qnaService.getInfo(qnaSearch);
		List<QnaImg> qnaImgList = qnaService.getQnaImgList(qnaSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("qna", qna);
		mav.addObject("qnaImgList", qnaImgList);
		mav.addObject("retrivePath", Utils.retrivePath("qna"));

		return mav;
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Qna qna, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			qna.setStatus(qna.getAnswer().length() > 0 ? "A":"Q");

			qna.setCuser(sess.getAdminNo());
			qna.setUuser(sess.getAdminNo());
			qnaService.modify(qna);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


}
