package com.pntmall.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.pntmall.admin.domain.OrderAddr;
import com.pntmall.admin.domain.RoutineOrder;
import com.pntmall.admin.domain.RoutineOrderDate;
import com.pntmall.admin.domain.RoutineOrderItem;
import com.pntmall.admin.domain.RoutineOrderSearch;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.OrderService;
import com.pntmall.admin.service.RoutineOrderService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/order/routine")
public class RoutineOrderController {
	public static final Logger logger = LoggerFactory.getLogger(RoutineOrderController.class);
	
	@Autowired
	private RoutineOrderService routineOrderService;
		
	@Autowired
	private OrderService orderService;

	@Autowired
	private CodeService codeService;

	@GetMapping("/list")
	public ModelAndView list(@ModelAttribute RoutineOrderSearch routineOrderSearch) {
		Utils.savePath();
		
		if(StringUtils.isEmpty(routineOrderSearch.getSdate())) routineOrderSearch.setSdate(Utils.getTimeStampString("yyyy.MM.dd"));
		if(StringUtils.isEmpty(routineOrderSearch.getEdate())) routineOrderSearch.setEdate(Utils.getTimeStampString("yyyy.MM.dd"));

		List<RoutineOrder> list = routineOrderService.getList(routineOrderSearch);
		Integer count = routineOrderService.getCount(routineOrderSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, routineOrderSearch);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(String orderid) {
		RoutineOrder routineOrder = routineOrderService.getInfo(orderid);
		OrderAddr orderAddr = orderService.getOrderAddrInfo(orderid);
		List<RoutineOrderItem> itemList = routineOrderService.getItemList(orderid);
		List<RoutineOrderDate> dateList = routineOrderService.getDateList(orderid);
		List<Code> mtel1List = codeService.getList2("011");
		List<Code> tel1List = codeService.getList2("012");
		List<Code> msgList = codeService.getList2("010");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
		mav.addObject("routineOrder", routineOrder);
		mav.addObject("orderAddr", orderAddr);
		mav.addObject("itemList", itemList);
		mav.addObject("dateList", dateList);
		mav.addObject("mtel1List", mtel1List);
		mav.addObject("tel1List", tel1List);
		mav.addObject("msgList", msgList);
		mav.addObject("today", Utils.getTimeStampString("yyyy.MM.dd"));

		return mav;
	}
	
	@PostMapping("/addAddr")
	@ResponseBody
	public ResultMessage addAddr(@ModelAttribute OrderAddr orderAddr, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}
	
		AdminSession sess = AdminSession.getInstance();

		try {
			orderAddr.setCuser(sess.getAdminNo());
			orderService.createAddr(orderAddr);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/modifyDate")
	@ResponseBody
	public ResultMessage modifyDate(@ModelAttribute RoutineOrderDate routineOrderDate, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}
	
		AdminSession sess = AdminSession.getInstance();

		try {
			routineOrderDate.setCuser(sess.getAdminNo());
			routineOrderService.modifyDate(routineOrderDate);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}

	@PostMapping("/cancel")
	@ResponseBody
	public ResultMessage cancel(@ModelAttribute RoutineOrderDate routineOrderDate, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}
	
		AdminSession sess = AdminSession.getInstance();

		try {
			routineOrderDate.setStatus("190");
			routineOrderDate.setCuser(sess.getAdminNo());
			routineOrderService.modifyDate(routineOrderDate);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "수정되었습니다.");
	}

}
