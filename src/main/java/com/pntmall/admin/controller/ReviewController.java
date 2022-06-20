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

import com.pntmall.admin.domain.Category;
import com.pntmall.admin.domain.Review;
import com.pntmall.admin.domain.ReviewImg;
import com.pntmall.admin.domain.ReviewSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CategoryService;
import com.pntmall.admin.service.ReviewService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping({"/etc/review/","/etc/best/","/content/review/","/etc/reviewAdd/"})
public class ReviewController {
	public static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
	private ReviewService reviewService;

    @Autowired
	private CategoryService categoryService;

	@GetMapping("/getCate")
	@ResponseBody
	public ResultMessage getCate(Integer cate) {
		List<Category> cateList = categoryService.getList(cate);
		Param param = new Param();
		param.put("cateList", cateList);
		return new ResultMessage(true, "조회되었습니다.",param);
	}

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute ReviewSearch reviewSearch, HttpServletRequest request) {
		Utils.savePath("review");
		logger.debug("list"+reviewSearch+"---"+request.getRequestURI());
		logger.debug("Utils.retrivePath() :::: "+Utils.retrivePath());

		List<Category> cateList1 = categoryService.getList(0);
		List<Category> cateList2 = new ArrayList<Category>();
		if(reviewSearch.getCate1() != null) {
			cateList2 = categoryService.getList(reviewSearch.getCate1());
		}

		List<Review> list = reviewService.getList(reviewSearch);
		Integer count = reviewService.getCount(reviewSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, reviewSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("cateList1", cateList1);
		mav.addObject("cateList2", cateList2);
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		mav.addObject("reviewSearch", reviewSearch);

		return mav;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Review review, HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());

		try {
//			review.setCuser(fs.getMemNo());
			reviewService.create(review,param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "등록되었습니다.");
	}

	@GetMapping("/edit")
	public ModelAndView edit(@ModelAttribute ReviewSearch reviewSearch, HttpServletRequest request) {
		logger.debug("edit Utils.retrivePath() :::: "+Utils.retrivePath("review"));
		String mode = "modify";
		Review review = reviewService.getInfo(reviewSearch);
		List<ReviewImg> reviewImgList = reviewService.getReviewImgList(reviewSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath("review"));
		mav.addObject("mode", mode);
		mav.addObject("review", review);
		mav.addObject("reviewImgList", reviewImgList);


		return mav;
	}

	@GetMapping("/add")
	public ModelAndView add(@ModelAttribute ReviewSearch reviewSearch, HttpServletRequest request) {
		logger.debug("edit Utils.retrivePath() :::: "+Utils.retrivePath("review"));
		String mode = request.getParameter("mode");
		Review review = reviewService.getInfo(reviewSearch);
		List<ReviewImg> reviewImgList = reviewService.getReviewImgList(reviewSearch);

		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath("review"));
		mav.addObject("mode", mode);
		mav.addObject("review", review);
		mav.addObject("reviewImgList", reviewImgList);

		return mav;
	}

	@PostMapping("/addModify")
	@ResponseBody
	public ResultMessage addModify(@ModelAttribute Review review, HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		String updateAuth = (String) request.getAttribute("updateAuth");

		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			review.setUuser(sess.getAdminNo());
			reviewService.addModify(review, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}

	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Review review, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		try {
			review.setUuser(sess.getAdminNo());
			reviewService.modify(review);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}


	@PostMapping("/setBestSeq")
	@ResponseBody
	public ResultMessage setBestSeq(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		logger.debug("setBestSeq:::"+param);

		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		List<Review> listReview = new ArrayList<Review>();
        int itemCnt = param.getInt("itemCnt");
        for (int i = 0; i < itemCnt; i++) {
            if (param.getInt("bestSeqOld" + i) != param.getInt("bestSeq" + i)) {
            	Review review = new Review();
            	review.setPno(param.getInt("pno" + i));
            	review.setReviewNo(param.getInt("reviewNo" + i));
            	review.setBestSeq(param.getString("bestSeq" + i).isEmpty() ? null:param.getInt("bestSeq" + i));
            	review.setUuser(sess.getAdminNo());
            	listReview.add(review);
            }
        }

		try {
			reviewService.setBestSeq(listReview);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}

	@PostMapping("/setReviewSeq")
	@ResponseBody
	public ResultMessage setReviewSeq(HttpServletRequest request) {
		Param param = new Param(request.getParameterMap());
		logger.debug("setReviewSeq:::"+param);

		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();

		List<Review> listReview = new ArrayList<Review>();
        int itemCnt = param.getInt("itemCnt");
        for (int i = 0; i < itemCnt; i++) {
            if (param.getInt("reviewSeqOld" + i) != param.getInt("reviewSeq" + i)) {
            	Review review = new Review();
            	review.setPno(param.getInt("pno" + i));
            	review.setReviewNo(param.getInt("reviewNo" + i));
            	review.setReviewSeq(param.getString("reviewSeq" + i).isEmpty() ? null:param.getInt("reviewSeq" + i));
            	review.setUuser(sess.getAdminNo());
            	listReview.add(review);
            }
        }

		try {
			reviewService.setReviewSeq(listReview);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}

		return new ResultMessage(true, "수정되었습니다.");
	}

}
