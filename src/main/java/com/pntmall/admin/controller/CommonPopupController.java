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

import com.pntmall.admin.domain.Admin;
import com.pntmall.admin.domain.Category;
import com.pntmall.admin.domain.Dose;
import com.pntmall.admin.domain.Intake;
import com.pntmall.admin.domain.MemGrade;
import com.pntmall.admin.domain.Nutrition;
import com.pntmall.admin.domain.OrderStatusCode;
import com.pntmall.admin.domain.Product;
import com.pntmall.admin.domain.ProductSearch;
import com.pntmall.admin.domain.SapProduct;
import com.pntmall.admin.domain.SapProductSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.AdminService;
import com.pntmall.admin.service.CategoryService;
import com.pntmall.admin.service.DoseService;
import com.pntmall.admin.service.IntakeService;
import com.pntmall.admin.service.MemGradeService;
import com.pntmall.admin.service.NutritionService;
import com.pntmall.admin.service.OrderService;
import com.pntmall.admin.service.ProductService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.payment.TossService;
import com.pntmall.common.utils.SecurityUtils;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/popup")
public class CommonPopupController {
	public static final Logger logger = LoggerFactory.getLogger(CommonPopupController.class);

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
	private ProductService productService;

    @Autowired
	private CategoryService categoryService;

    @Autowired
	private IntakeService intakeService;

    @Autowired
	private DoseService doseService;

    @Autowired
	private NutritionService nutritionService;

    @Autowired
	private MemGradeService memGradeService;

    @Autowired
	private OrderService orderService;

    @Autowired
	private AdminService adminService;

    @GetMapping("/sap")
    public ModelAndView sap(@ModelAttribute SapProductSearch sapProductSearch) {
    	List<SapProduct> list = productService.getSapList(sapProductSearch);
    	Integer count = productService.getSapCount(sapProductSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, sapProductSearch);

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("list", list);
    	mav.addObject("count", count);
		mav.addObject("paging", paging);

    	return mav;
    }

    @GetMapping("/category")
    public ModelAndView category() {
    	List<Category> list = categoryService.getAllList();

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("list", list);

    	return mav;
    }

    @GetMapping("/intake")
    public ModelAndView intake(String content) {
    	List<Intake> list = intakeService.getList(content);

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("list", list);

    	return mav;
    }

    @GetMapping("/dose")
    public ModelAndView dose(String content) {
    	List<Dose> list = doseService.getList(content);

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("list", list);

    	return mav;
    }

    @GetMapping("/nutrition")
    public ModelAndView nutrition(String name) {
    	List<Nutrition> list = nutritionService.getList(name);

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("list", list);

    	return mav;
    }

    @GetMapping("/gift")
    public ModelAndView gift(@ModelAttribute ProductSearch productSearch) {
    	productSearch.setPtype(2);
    	productSearch.setStatus("S");
    	return productList(productSearch);
    }

    @GetMapping("/product")
    public ModelAndView product(@ModelAttribute ProductSearch productSearch) {
    	productSearch.setPtype(1);
    	return productList(productSearch);
    }

    @GetMapping("/productInfo")
    public ModelAndView productInfo(@ModelAttribute ProductSearch productSearch) {
    	productSearch.setPtype(1);
    	return productList(productSearch);
    }

    @GetMapping("/productDoctorPack")
    public ModelAndView productDoctorPack(@ModelAttribute ProductSearch productSearch) {
    	productSearch.setPtype(1);
    	return productList(productSearch);
    }

    private ModelAndView productList(ProductSearch productSearch) {
    	List<Product> list = productService.getList(productSearch);
    	Integer count = productService.getCount(productSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, productSearch);

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("list", list);
    	mav.addObject("count", count);
		mav.addObject("paging", paging);

    	return mav;
    }

    @GetMapping("/memGrade")
    public ModelAndView memGrade(String name) {
    	List<MemGrade> list = memGradeService.getList();

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("list", list);

    	return mav;
    }

    @GetMapping("/statusLog")
    public ModelAndView statusLog(String orderid) {
    	List<OrderStatusCode> list = orderService.getOrderStatusLogList(orderid);

    	ModelAndView mav = new ModelAndView();
    	mav.addObject("list", list);

    	return mav;
    }

    @GetMapping("/refundAccount")
    public ModelAndView refundAccount(String orderid) {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("bankOptions", TossService.getBankOptions());
    	return mav;
    }

    @GetMapping("myPage")
    public ModelAndView myPage(HttpServletRequest request) {
    	AdminSession sess = AdminSession.getInstance();
    	ModelAndView mav = new ModelAndView();
    	
    	Admin info = adminService.getInfo(sess.getAdminNo());
    	mav.addObject("info", info);
    	
    	return mav;
    }

    @PostMapping("myPageProc")
    @ResponseBody
    public ResultMessage myPageProc(Admin admin) {
    	AdminSession sess = AdminSession.getInstance();
    	
    	if(!sess.isLogin()) {
			return new ResultMessage(false, "로그아웃 상태입니다.");
    	}
    	
    	Admin info = adminService.getInfo(sess.getAdminNo());
    	
    	try {
        	String encPasswd = securityUtils.encodeSHA512(admin.getPasswd());
        	if(!encPasswd.equals(info.getPasswd())) {
    			return new ResultMessage(false, "기존패스워드가 일치하지 않습니다.");
        	}
        	
        	admin.setAdminNo(sess.getAdminNo());
			admin.setPasswd(securityUtils.encodeSHA512(admin.getPasswd2()));
			admin.setCuser(sess.getAdminNo());
			adminService.modify(admin);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
    }

	@GetMapping("/cate2")
	public ModelAndView cate2(Integer pcateNo) {
		List<Category> list = categoryService.getList(pcateNo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		
		return mav;
	}
	
}
