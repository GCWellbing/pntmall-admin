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

import com.pntmall.admin.domain.Product;
import com.pntmall.admin.domain.ProductSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.ProductService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/product/gift")
public class GiftController {
	public static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
    @Autowired
	private ProductService productService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute ProductSearch productSearch) {
		Utils.savePath();
		
		productSearch.setPtype(2);
		List<Product> list = productService.getList(productSearch);
		Integer count = productService.getCount(productSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, productSearch);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(Integer pno) {
		String mode = "create";
		Product product = new Product();

		if(pno != null && pno > 0) {
			mode = "modify";
			product = productService.getInfo(pno);
		} else {
			pno = 0;
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
        mav.addObject("product", product);
		mav.addObject("mode", mode);
		
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
