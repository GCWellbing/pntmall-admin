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

import com.pntmall.admin.domain.Category;
import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.Product;
import com.pntmall.admin.domain.ProductCategory;
import com.pntmall.admin.domain.ProductDiscount;
import com.pntmall.admin.domain.ProductDose;
import com.pntmall.admin.domain.ProductGift;
import com.pntmall.admin.domain.ProductGrade;
import com.pntmall.admin.domain.ProductIcon;
import com.pntmall.admin.domain.ProductIntake;
import com.pntmall.admin.domain.ProductNutrition;
import com.pntmall.admin.domain.ProductOption;
import com.pntmall.admin.domain.ProductSearch;
import com.pntmall.admin.domain.ProductTag;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CategoryService;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.ProductService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/product/display")
public class ProductController {
	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
    @Autowired
	private ProductService productService;

    @Autowired
	private CategoryService categoryService;
	
    @Autowired
	private CodeService codeService;
	
	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute ProductSearch productSearch) {
		Utils.savePath();
		
		productSearch.setPtype(1);
		List<Product> list = productService.getList(productSearch);
		Integer count = productService.getCount(productSearch);
		List<Code> brandList = codeService.getList2("001");
		List<Category> categoryList = categoryService.getAllList();
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, productSearch);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("brandList", brandList);
		mav.addObject("categoryList", categoryList);
		mav.addObject("paging", paging);
		
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(Integer pno) {
		String mode = "create";
		Product product = new Product();

		ModelAndView mav = new ModelAndView();

		if(pno != null && pno > 0) {
			mode = "modify";
			product = productService.getInfo(pno);
		} else {
			pno = 0;
			mav.addObject("today", Utils.getTimeStampString("yyyy.MM.dd"));
		}
		
		List<ProductGrade> gradeList = productService.getGradeList(pno);
		List<ProductCategory> categoryList = productService.getCategoryList(pno);
		List<ProductTag> tagList = productService.getTagList(pno);
		List<ProductIcon> iconList = productService.getIconList(pno);
		List<ProductGift> giftList = productService.getGiftList(pno);
		List<ProductIntake> intakeList = productService.getIntakeList(pno);
		List<ProductDose> doseList = productService.getDoseList(pno);
		List<ProductNutrition> nutritionList = productService.getNutritionList(pno);
		List<ProductOption> optionList = productService.getOptionList(pno);
		List<ProductDiscount> discountList = productService.getDiscountList(pno);
		
		List<Code> brandList = codeService.getList2("001");
		
		mav.addObject("retrivePath", Utils.retrivePath());
        mav.addObject("product", product);
		mav.addObject("mode", mode);
		mav.addObject("gradeList", gradeList);
		mav.addObject("categoryList", categoryList);
		mav.addObject("tagList", tagList);
		mav.addObject("iconList", iconList);
		mav.addObject("giftList", giftList);
		mav.addObject("intakeList", intakeList);
		mav.addObject("doseList", doseList);
		mav.addObject("nutritionList", nutritionList);
		mav.addObject("optionList", optionList);
		mav.addObject("discountList", discountList);

		mav.addObject("brandList", brandList);
		
		return mav;
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResultMessage create(@ModelAttribute Product product, HttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			product.setCuser(sess.getAdminNo());
			productService.create(product, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modify")
	@ResponseBody
	public ResultMessage modify(@ModelAttribute Product product, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		AdminSession sess = AdminSession.getInstance();
		Param param = new Param(request.getParameterMap());
		
		try {
			product.setCuser(sess.getAdminNo());
			productService.modify(product, param);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}
	
}
