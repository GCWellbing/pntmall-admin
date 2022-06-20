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
import com.pntmall.admin.domain.Faq;
import com.pntmall.admin.domain.FaqSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.FaqService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/cs/faq")
public class FaqController {
	public static final Logger logger = LoggerFactory.getLogger(FaqController.class);

    @Autowired
	private FaqService faqService;

    @Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute FaqSearch faqSearch) {
		Utils.savePath("faq");
		logger.debug("list", faqSearch);
		List<Code> cateList = codeService.getList2("003");

		List<Faq> list = faqService.getList(faqSearch);
		Integer count = faqService.getCount(faqSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, faqSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("cateList", cateList);
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);

		return mav;
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer faqNo) {
		String mode = "create";
		List<Code> cateList = codeService.getList2("003");
		Faq faq = new Faq();
		if(faqNo != null && faqNo > 0) {
			mode = "modify";
			faq = faqService.getInfo(faqNo);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("faq", faq);
		mav.addObject("cateList", cateList);
		mav.addObject("retrivePath", Utils.retrivePath("faq"));

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Faq faq) {
		AdminSession sess = AdminSession.getInstance();

		try {
			faq.setCuser(sess.getAdminNo());
			faqService.create(faq);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Faq faq, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			faq.setCuser(sess.getAdminNo());
			faqService.modify(faq);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


}
