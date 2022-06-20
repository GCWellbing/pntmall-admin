package com.pntmall.admin.controller;

import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.HealthExample;
import com.pntmall.admin.domain.HealthQuestion;
import com.pntmall.admin.domain.HealthTopic;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.HealthQuestionService;
import com.pntmall.admin.service.HealthTopicService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/health/topic")
public class HealthTopicController {
	public static final Logger logger = LoggerFactory.getLogger(HealthTopicController.class);

	@Autowired
	private HealthTopicService healthTopicService;

	@Autowired
	private HealthQuestionService healthQuestionService;

	@Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list() {
		List<HealthTopic> list = healthTopicService.getList();
		List<Code> typeList = codeService.getList2("023"); //주제유형

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("typeList", typeList);

		return mav;
	}

	@GetMapping("/info")
	@ResponseBody
	public ResultMessage info(Integer healthNo) {
		HealthTopic info = healthTopicService.getInfo(healthNo);
		Param param = new Param();
		param.set("info", info);

		return new ResultMessage(true, "", param);
	}

	@GetMapping("/edit")
	public ModelAndView edit(Integer healthNo) {
		String mode = "createQuestion";
		HealthQuestion info = healthQuestionService.getInfo(healthNo);
		HealthTopic topic = healthTopicService.getInfo(healthNo);
		List<HealthExample> list = new ArrayList<>();
		List<HealthExample> productList = new ArrayList<>();
		List<HealthExample> nutritionList = new ArrayList<>();

		if (topic != null && info != null) {
			mode = "modifyQuestion";
			info = healthQuestionService.getInfo(healthNo);
			list = healthQuestionService.getExampleList(healthNo);
			productList = healthQuestionService.getProductList(healthNo);
			nutritionList = healthQuestionService.getNutritionList(healthNo);
		}

		List<Code> typeList = codeService.getList2("024"); //건강기능

		ModelAndView mav = new ModelAndView();
		mav.addObject("mode", mode);
		mav.addObject("topic", topic);
		mav.addObject("info", info);
		mav.addObject("list", list);
		mav.addObject("productList", productList);
		mav.addObject("nutritionList", nutritionList);
		mav.addObject("typeList", typeList);

		return mav;
	}


	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute HealthTopic healthTopic) {
		AdminSession sess = AdminSession.getInstance();

		try {
			healthTopic.setCuser(sess.getAdminNo());
			healthTopicService.create(healthTopic);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute HealthTopic healthTopic, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if (!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			healthTopic.setCuser(sess.getAdminNo());
			healthTopicService.modify(healthTopic);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}

	@PostMapping("/modifyRank")
	@ResponseBody
	public ResultMessage modifyRank(HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if (!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());

		try {
			healthTopicService.modifyRank(param, sess.getAdminNo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}

	@PostMapping("/remove")
	@ResponseBody
	public ResultMessage remove(@ModelAttribute HealthTopic healthTopic, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if (!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			healthTopic.setCuser(sess.getAdminNo());
			healthTopicService.remove(healthTopic);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "삭제되었습니다.");
	}


	@PostMapping("/createQuestion")
	@ResponseBody
	public ResultMessage createQuestion(@ModelAttribute HealthQuestion healthQuestion, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		try {
			healthQuestion.setCuser(sess.getAdminNo());
			healthQuestionService.create(healthQuestion, param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@PostMapping("/modifyQuestion")
	@ResponseBody
	public ResultMessage modifyQuestion(@ModelAttribute HealthQuestion healthQuestion, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if (!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		try {
			healthQuestion.setCuser(sess.getAdminNo());
			healthQuestionService.modify(healthQuestion, param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}
}
