package com.pntmall.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pntmall.admin.domain.Code;
import com.pntmall.admin.domain.MemGrade;
import com.pntmall.admin.domain.Order;
import com.pntmall.admin.domain.OrderAddr;
import com.pntmall.admin.domain.OrderItem;
import com.pntmall.admin.domain.OrderMemo;
import com.pntmall.admin.domain.OrderRefund;
import com.pntmall.admin.domain.OrderSearch;
import com.pntmall.admin.domain.OrderShip;
import com.pntmall.admin.domain.OrderStatusCode;
import com.pntmall.admin.domain.PaymentLog;
import com.pntmall.admin.etc.AdminPaging;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.admin.service.CodeService;
import com.pntmall.admin.service.MemGradeService;
import com.pntmall.admin.service.OrderService;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.azure.StorageUploader;
import com.pntmall.common.payment.TossService;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.HttpRequestHelper;
import com.pntmall.common.utils.Utils;

@Controller
@RequestMapping("/order/order")
public class OrderController {
	public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
		
	@Autowired
	private CodeService codeService;
		
	@Autowired
	private MemGradeService memGradeService;
		
    @Autowired
    private StorageUploader storageUploader;

    @GetMapping("/list")
	public ModelAndView list(@ModelAttribute OrderSearch orderSearch) {
		Utils.savePath();
		
		if(StringUtils.isEmpty(orderSearch.getSdate())) orderSearch.setSdate(Utils.getTimeStampString("yyyy.MM.dd"));
		if(StringUtils.isEmpty(orderSearch.getEdate())) orderSearch.setEdate(Utils.getTimeStampString("yyyy.MM.dd"));
		logger.debug("-----------------" + orderSearch.toString());

		List<OrderStatusCode> statusList = orderService.getOrderStatusCodeList();
		List<Code> payTypeList = codeService.getList2("013");
		List<MemGrade> gradeList = memGradeService.getList();
		List<Order> list = orderService.getList(orderSearch);
		Integer count = orderService.getCount(orderSearch);
		AdminPaging paging = new AdminPaging(Utils.getUrl(), count, orderSearch);
		
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("statusList", statusList);
		mav.addObject("payTypeList", payTypeList);
		mav.addObject("gradeList", gradeList);
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("paging", paging);
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView edit(String orderid) {
		Order order = orderService.getOrderInfo(orderid);
		OrderAddr orderAddr = orderService.getOrderAddrInfo(orderid);
		
		List<OrderShip> shipList = orderService.getOrderShipList(orderid);
		for(OrderShip orderShip : shipList) {
			List<OrderItem> itemList = orderService.getOrderItemList(orderShip);
			orderShip.setItems(itemList);
		}
		
		OrderMemo orderMemo = orderService.getOrderMemoInfo(orderid);
		List<OrderItem> returnItemList = orderService.getReturnItemList(orderid);
		List<Code> mtel1List = codeService.getList2("011");
		List<Code> tel1List = codeService.getList2("012");
		List<Code> msgList = codeService.getList2("010");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
		mav.addObject("order", order);
		mav.addObject("orderMemo", orderMemo);
		mav.addObject("orderAddr", orderAddr);
		mav.addObject("shipList", shipList);
		mav.addObject("returnItemList", returnItemList);
		mav.addObject("mtel1List", mtel1List);
		mav.addObject("tel1List", tel1List);
		mav.addObject("msgList", msgList);
    	
    	if("013003".equals(order.getPayType())) {
			PaymentLog paymentLog = orderService.getVirtualAccountInfo(orderid);
			mav.addObject("paymentLog", paymentLog);
		}

		return mav;
	}

	@GetMapping("/redit")
	public ModelAndView redit(String orderid) {
		Order order = orderService.getOrderInfo(orderid);
		Order orgOrder = orderService.getOrderInfo(order.getOrgOrderid());
		OrderAddr orderAddr = orderService.getOrderAddrInfo(orderid);
		OrderRefund orderRefund = orderService.getRefundInfo(orderid);
		
		List<OrderShip> shipList = orderService.getOrderShipList(orderid);
		for(OrderShip orderShip : shipList) {
			List<OrderItem> itemList = orderService.getOrderItemList(orderShip);
			orderShip.setItems(itemList);
		}
		
		OrderMemo orderMemo = orderService.getOrderMemoInfo(orderid);
		List<OrderItem> orgItemList = orderService.getOrgItemList(order.getOrgOrderid());
		List<Code> mtel1List = codeService.getList2("011");
		List<Code> tel1List = codeService.getList2("012");
		List<Code> msgList = codeService.getList2("010");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("retrivePath", Utils.retrivePath());
		mav.addObject("order", order);
		mav.addObject("orgOrder", orgOrder);
		mav.addObject("orderMemo", orderMemo);
		mav.addObject("orderAddr", orderAddr);
		mav.addObject("orderRefund", orderRefund);
		mav.addObject("shipList", shipList);
		mav.addObject("orgItemList", orgItemList);
		mav.addObject("mtel1List", mtel1List);
		mav.addObject("tel1List", tel1List);
		mav.addObject("msgList", msgList);
    	mav.addObject("bankOptions", TossService.getBankOptions(orderRefund == null ? "" : orderRefund.getBank()));
    	
    	if("013003".equals(order.getPayType())) {
			PaymentLog paymentLog = orderService.getVirtualAccountInfo(orderid);
			mav.addObject("paymentLog", paymentLog);
		}

    	if(order.getGubun() == 2) {
    		List<Code> reasonList = codeService.getList2("021");
    		mav.addObject("reasonList", reasonList);
    	} else {
    		List<Code> reasonList = codeService.getList2("021");
    		mav.addObject("reasonList", reasonList);
    	}
		return mav;
	}
	
	
	@PostMapping("/addMemo")
	@ResponseBody
	public ResultMessage addMemo(@ModelAttribute OrderMemo orderMemo, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}
	
		AdminSession sess = AdminSession.getInstance();

		try {
			orderMemo.setCuser(sess.getAdminNo());
			orderService.createMemo(orderMemo);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/addAddr")
	@ResponseBody
	public ResultMessage addAddr(@ModelAttribute OrderAddr orderAddr, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}
	
		Order order = orderService.getOrderInfo(orderAddr.getOrderid());
		if("110,120,210,220,310,320".indexOf(order.getStatus()) == -1) {
			return new ResultMessage(false, "주소정보를 변경할 수 없는 상태입니다.");
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
	
	@PostMapping("/addRefund")
	@ResponseBody
	public ResultMessage addRefund(@ModelAttribute OrderRefund orderRefund, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}
	
		Order order = orderService.getOrderInfo(orderRefund.getOrderid());
		if("210,220,230,240,310,320,330,340".indexOf(order.getStatus()) == -1) {
			return new ResultMessage(false, "환불정보를 변경할 수 없는 상태입니다.");
		}
		
		AdminSession sess = AdminSession.getInstance();

		try {
			orderRefund.setCuser(sess.getAdminNo());
			orderService.createRefundInfo(orderRefund);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, "오류가 발생했습니다.");
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/cancel")
	@ResponseBody
	public ResultMessage cancel(HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		Param param = new Param(request.getParameterMap());
		String orderid = param.get("orderid");
		
		Order order = orderService.getOrderInfo(orderid);
		if(order == null || "110,120".indexOf(order.getStatus()) == -1) {
			return new ResultMessage(false, "취소할 수 없는 상태입니다.");
		}
		
		AdminSession sess = AdminSession.getInstance();

		try {
			OrderRefund orderRefund = new OrderRefund();
			orderRefund.setBank(param.get("refundBank"));
			orderRefund.setAccount(param.get("refundAccount"));
			orderRefund.setDepositor(param.get("refundDepositor"));
			orderRefund.setCuser(sess.getAdminNo());
			orderService.cancel(order, orderRefund);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, e.getMessage());
		}
		
		return new ResultMessage(true, "취소되었습니다.");
	}
	
	@PostMapping("/createReturn")
	@ResponseBody
	public ResultMessage createReturn(HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		try {
			orderService.createReturn(new Param(request.getParameterMap()));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, e.getMessage());
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/createExchange")
	@ResponseBody
	public ResultMessage createExchange(HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		try {
			orderService.createExchange(new Param(request.getParameterMap()));
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, e.getMessage());
		}
		
		return new ResultMessage(true, "등록되었습니다.");
	}
	
	@PostMapping("/status")
	@ResponseBody
	public ResultMessage status(@ModelAttribute Order order, HttpServletRequest request) {
		String updateAuth = (String) request.getAttribute("updateAuth");
		if(!"Y".equals(updateAuth)) {
			return new ResultMessage(false, "수정/삭제 권한이 없습니다.");
		}

		try {
			orderService.modifyStatus(order);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, e.getMessage());
		}
		
		return new ResultMessage(true, "주문상태가 변경되었습니다.");
	}

	@GetMapping("/excel")
	@ResponseBody
	public void excel(@ModelAttribute OrderSearch orderSearch) {
		orderSearch.setPageSize(Integer.MAX_VALUE);
		List<Order> list = orderService.getList(orderSearch);

		SXSSFWorkbook workbook = new SXSSFWorkbook();
		workbook.setCompressTempFiles(true);
		SXSSFSheet sheet = workbook.createSheet();
		sheet.setRandomAccessWindowSize(100);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle styleCenter = workbook.createCellStyle();
		styleCenter.setAlignment(HorizontalAlignment.CENTER);

		CellStyle styleRight = workbook.createCellStyle();
		styleRight.setAlignment(HorizontalAlignment.RIGHT);

		CellStyle styleLeft= workbook.createCellStyle();
		styleLeft.setAlignment(HorizontalAlignment.LEFT);

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle bodyStyleDate = workbook.createCellStyle();
		bodyStyleDate.setAlignment(HorizontalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));

		Row headerRow = sheet.createRow(0);
		String[] headerTexts = { 
				"주문번호",	"일시", "첫주문", "제품명", "ID", "주문자", 
				"등급", "총제품금액", "총결제금액", "배송유형", "주문상태", 
				"디바이스", 	"결제타입", "받는분 성명", "받는분 우편번호", "받는분 주소",
				"받는분 전화번호", "받는분 기타연락처", "품목명", "박스수량", "베송메세지1",
				"박스타입", "운임구분", "기본운임", "택배사", "송장번호"
			};
		
		int[] headerWidth = {
				4000, 4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 4000,
				4000, 4000, 4000, 4000, 4000, 4000,
				4000, 4000, 4000, 4000, 4000, 4000,
				4000, 4000, 4000, 4000, 4000, 4000
			};

		for (int i = 0; i < headerTexts.length; i++) {
			String text = headerTexts[i];

			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(text);

			sheet.setColumnWidth(i, headerWidth[i]);
		}

		Cell cell;
		for(int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 1);

			Order order = list.get(i);

			int idx = 0;

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getOrderid());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.getTimeStampString(order.getOdate(), "yyyy.MM.dd HH:mm:ss"));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getFirstOrderYn());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleLeft);
			cell.setCellValue(order.getPname() + (order.getItemCount() > 1 ? " 외 " + (order.getItemCount() - 1) : ""));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getMemId());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getOname());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getGradeName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(order.getAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(Utils.formatMoney(order.getPayAmt()));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getOrderGubunName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getStatusName());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getDevice());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getPayTypeName());
			
			OrderAddr orderAddr = orderService.getOrderAddrInfo(order.getOrderid());
			
			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderAddr.getSname());
			
			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderAddr.getSzip());
			
			cell = row.createCell(idx++);
			cell.setCellStyle(styleLeft);
			cell.setCellValue(orderAddr.getSaddr1() + " " + orderAddr.getSaddr2());
			
			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderAddr.getSmtel1() + orderAddr.getSmtel2());
			
			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderAddr.getStel1() + orderAddr.getStel2());
			
			cell = row.createCell(idx++);
			cell.setCellStyle(styleLeft);
			cell.setCellValue(order.getPname() + (order.getItemCount() > 1 ? " 외 " + (order.getItemCount() - 1) : ""));

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(1);

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getMemo());

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(1);

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue(1);

			cell = row.createCell(idx++);
			cell.setCellStyle(styleRight);
			cell.setCellValue("3,000");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("018002");

			cell = row.createCell(idx++);
			cell.setCellStyle(styleCenter);
			cell.setCellValue("");
		}

		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=order_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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
	
	@GetMapping("/excel2")
	@ResponseBody
	public void excel2(@ModelAttribute OrderSearch orderSearch) {
		orderSearch.setPageSize(Integer.MAX_VALUE);
		List<Order> list = orderService.getList(orderSearch);

		SXSSFWorkbook workbook = new SXSSFWorkbook();
		workbook.setCompressTempFiles(true);
		SXSSFSheet sheet = workbook.createSheet();
		sheet.setRandomAccessWindowSize(100);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle styleCenter = workbook.createCellStyle();
		styleCenter.setAlignment(HorizontalAlignment.CENTER);

		CellStyle styleRight = workbook.createCellStyle();
		styleRight.setAlignment(HorizontalAlignment.RIGHT);

		CellStyle styleLeft= workbook.createCellStyle();
		styleLeft.setAlignment(HorizontalAlignment.LEFT);

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle bodyStyleDate = workbook.createCellStyle();
		bodyStyleDate.setAlignment(HorizontalAlignment.CENTER);
		bodyStyleDate.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss"));

		Row headerRow = sheet.createRow(0);
		String[] headerTexts = { 
				"주문번호", "주문일", "구매확정일","주문유형", "배송유형", "주문상태",
				"회원명", "회원ID", "이메일", "휴대폰번호", "등급", 
				"병의원코드", "병의원명", "디바이스", "첫주문", "메모", 
				"상담자ID", "주문 상품명", "주문 품목 개수", "제품코드", "SAP코드", 
				"일반/냉장", "제품명", "증정품 정보", "수량", "판매가", 
				"제품금액", "제품 SAP단가", "SAP단가 * 수량", "쿠폰할인금액", "등급할인금액", 
				"매출가", "병원출하가", "병원출하가 * 수량", "기반품/교환수량", "반품/교환수량", 
				"제품총가격", "등급총할인", "배송비", "배송비할인", "쿠폰 총 할인", 
				"포인트사용", "적립 예정 제품 포인트", "총 적립예정 포인트", "총결제금액", "결제수단", 
				"주문자명", "주문자휴대폰", "주문자이메일", "받는분성명", "받는분우편번호", 
				"받는분주소", "받는분상세주소", "휴대폰번호", "전화번호", "품목명", 
				"박스수량", "배송메세지", "박스타입", "운임구분", "기본운임", 
				"택배사", "송장번호", "배송 시작 일자", "배송 완료 일자", "환불금액", 
				"환불포인트", "환불 계좌", "취소 상품 수량", "교환 상품 수량", "반품 상품 수량"

			};
		
		int[] headerWidth = {
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000, 
				4000, 4000, 4000, 4000, 4000,
				4000
			};

		for (int i = 0; i < headerTexts.length; i++) {
			String text = headerTexts[i];

			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(text);

			sheet.setColumnWidth(i, headerWidth[i]);
		}

		Cell cell;
		int rowNo = 0;
		for(Order order : list) {
			Row row = sheet.createRow(++rowNo);

			OrderMemo orderMemo = orderService.getOrderMemoInfo(order.getOrderid());
			OrderAddr orderAddr = orderService.getOrderAddrInfo(order.getOrderid());
			
			cell = row.createCell(0);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getOrderid());	// 주문번호

			cell = row.createCell(1);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.getTimeStampString(order.getOdate(), "yyyy.MM.dd HH:mm"));	// 주문일

			cell = row.createCell(2);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(Utils.getTimeStampString(order.getCdate(), "yyyy.MM.dd HH:mm"));	// 구매확정일

			cell = row.createCell(3);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getGubunName());	// 주문유형
			
			cell = row.createCell(4);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getOrderGubunName());	// 배송유형
			
			cell = row.createCell(5);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getStatusName());	// 주문상태

			cell = row.createCell(6);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getMemName());	// 회원명

			cell = row.createCell(7);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getMemId());	// 회원ID

			cell = row.createCell(8);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getEmail());	// 이메일

			cell = row.createCell(9);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getMtel1() + order.getMtel2());	// 휴대폰번호

			cell = row.createCell(10);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getGradeName());	// 등급

			cell = row.createCell(11);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getClinicId());	// 병의원코드
			
			cell = row.createCell(12);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getClinicName().replaceAll("&amp;", "&"));	// 병의원명
			
			cell = row.createCell(13);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getDevice());	// 디바이스

			cell = row.createCell(14);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getFirstOrderYn());	// 첫주문

			cell = row.createCell(15);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderMemo != null ? orderMemo.getMemo() : "");	// 메모

			cell = row.createCell(16);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderMemo != null ? orderMemo.getAdminId() : "");	// 상담자ID

			cell = row.createCell(17);
			cell.setCellStyle(styleLeft);
			cell.setCellValue(order.getPname() + (order.getItemCount() > 1 ? " 외 " + (order.getItemCount() - 1) : ""));		// 주문 상품명
			
			cell = row.createCell(18);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getItemCount());	// 주문 품목 개수
			
			cell = row.createCell(36);
			cell.setCellStyle(styleRight);
			cell.setCellValue(order.getAmt());	// 제품총가격
			
			cell = row.createCell(37);
			cell.setCellStyle(styleRight);
			cell.setCellValue(order.getGradeDiscount());	// 등급총할인
			
			cell = row.createCell(38);
			cell.setCellStyle(styleRight);
			cell.setCellValue(order.getShipAmt());	// 배송비
			
			cell = row.createCell(39);
			cell.setCellStyle(styleRight);
			cell.setCellValue(order.getShipDiscount());	// 배송비할인
			
			cell = row.createCell(40);
			cell.setCellStyle(styleRight);
			cell.setCellValue(order.getCouponDiscount());	// 쿠폰 총 할인
			
			cell = row.createCell(41);
			cell.setCellStyle(styleRight);
			cell.setCellValue(order.getPoint());	// 포인트사용
			
			cell = row.createCell(43);
			cell.setCellStyle(styleRight);
			cell.setCellValue(order.getSumPoint() == null ? 0 : order.getSumPoint());	// 총 적립예정 포인트
			
			cell = row.createCell(44);
			cell.setCellStyle(styleRight);
			cell.setCellValue(order.getPayAmt());	// 총결제금액

			cell = row.createCell(45);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getPayTypeName());	// 결제수단

			cell = row.createCell(46);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getOname());	// 주문자명

			cell = row.createCell(47);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getOmtel1() + order.getOmtel2());	// 주문자휴대폰

			cell = row.createCell(48);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(order.getOemail());	// 주문자이메일

			cell = row.createCell(49);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderAddr.getSname());	// 받는분성명
			
			cell = row.createCell(50);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderAddr.getSzip());	// 받는분우편번호
			
			cell = row.createCell(51);
			cell.setCellStyle(styleLeft);
			cell.setCellValue(orderAddr.getSaddr1());	// 받는분주소
			
			cell = row.createCell(52);
			cell.setCellStyle(styleLeft);
			cell.setCellValue(orderAddr.getSaddr2());	// 받는분상세주소
			
			cell = row.createCell(53);
			cell.setCellStyle(styleCenter);	
			cell.setCellValue(orderAddr.getSmtel1() + orderAddr.getSmtel2());	// 휴대폰번호
				
			cell = row.createCell(54);
			cell.setCellStyle(styleCenter);
			cell.setCellValue(orderAddr.getStel1() + orderAddr.getStel2());	// 전화번호
			
			cell = row.createCell(55);
			cell.setCellStyle(styleLeft);
			cell.setCellValue(order.getPname() + (order.getItemCount() > 1 ? " 외 " + (order.getItemCount() - 1) : ""));	// 품목명 

			cell = row.createCell(57);
			cell.setCellStyle(styleLeft);
			cell.setCellValue(orderAddr.getMsg());	// 배송메세지 

			if(order.getGubun() == 3) {	// 반품 
				OrderRefund orderRefund = orderService.getRefundInfo(order.getOrderid());

				if(orderRefund != null) {
					cell = row.createCell(65);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderRefund.getAmt() == null ? 0 : orderRefund.getAmt());	// 환불금액
	
					cell = row.createCell(66);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderRefund.getPoint() == null ? 0 : orderRefund.getPoint());	// 환불포인트
	
					cell = row.createCell(67);
					cell.setCellStyle(styleCenter);
					cell.setCellValue(orderRefund.getAccount() == null ? "" : orderRefund.getAccount());	// 환불 계좌
				}
			}
			
			List<OrderShip> shipList = orderService.getOrderShipList(order.getOrderid());
			for(int i = 0; i < shipList.size(); i++) {
				if(i > 0) row = sheet.createRow(++rowNo);
				
				OrderShip orderShip = shipList.get(i);
				
				cell = row.createCell(56);
				cell.setCellStyle(styleRight);
				cell.setCellValue(1);	// 박스수량
				
				cell = row.createCell(58);
				cell.setCellStyle(styleRight);
				cell.setCellValue(1);	// 박스타입
				
				cell = row.createCell(59);
				cell.setCellStyle(styleRight);
				cell.setCellValue(1);	// 운임구분
				
				cell = row.createCell(60);
				cell.setCellStyle(styleRight);
				cell.setCellValue(3000);	// 기본운임
				
				cell = row.createCell(61);
				cell.setCellStyle(styleCenter);
				cell.setCellValue(orderShip.getShipper());	// 택배사
				
				cell = row.createCell(62);
				cell.setCellStyle(styleCenter);
				cell.setCellValue(orderShip.getInvoice());	// 송장번호
				
				cell = row.createCell(63);
				cell.setCellStyle(styleCenter);
				cell.setCellValue(Utils.formatDate(orderShip.getDate150(), "yyyy.MM.dd"));	// 배송 시작 일자

				cell = row.createCell(64);
				cell.setCellStyle(styleCenter);
				cell.setCellValue(Utils.formatDate(orderShip.getDate150(), "yyyy.MM.dd"));	// 배송 완료 일자
			
				List<OrderItem> itemList = orderService.getOrderItemList(orderShip);
				for(int j = 0; j < itemList.size(); j++) {
					if(j > 0) row = sheet.createRow(++rowNo);
					
					OrderItem orderItem = itemList.get(j);
					
					cell = row.createCell(19);
					cell.setCellStyle(styleCenter);
					cell.setCellValue(orderItem.getPno());	// 제품코드

					cell = row.createCell(20);
					cell.setCellStyle(styleCenter);
					cell.setCellValue(orderItem.getMatnr());	// SAP코드

					cell = row.createCell(21);
					cell.setCellStyle(styleCenter);
					cell.setCellValue("Y".equals(orderItem.getColdYn()) ? "냉장" : "일반");	// 일반/냉장

					cell = row.createCell(22);
					cell.setCellStyle(styleLeft);
					cell.setCellValue(orderItem.getPname());	// 제품명

					cell = row.createCell(23);
					cell.setCellStyle(styleLeft);
					cell.setCellValue(orderItem.getGift());	// 증정품 정보

					cell = row.createCell(24);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getQty());	// 수량

					cell = row.createCell(25);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getSalePrice());	// 판매가

					cell = row.createCell(26);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getSalePrice() / orderItem.getQty());	// 제품금액

					cell = row.createCell(27);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getNetpr());	// 제품 SAP단가

					cell = row.createCell(28);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getNetpr() * orderItem.getQty());	// SAP단가 * 수량

					cell = row.createCell(29);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getDiscount());	// 쿠폰할인금액

					cell = row.createCell(30);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getSalePrice() - orderItem.getMemPrice());	// 등급할인금액

					cell = row.createCell(31);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getApplyPrice());	// 매출가

					cell = row.createCell(32);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getSupplyPrice());	// 병원출하가

					cell = row.createCell(33);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getSupplyPrice() * orderItem.getQty());	// 병원출하가 * 수량

					cell = row.createCell(34);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getReturnQty() + orderItem.getExchangeQty());	// 기반품/교환수량

					if(order.getGubun() == 2 || order.getGubun() == 3) {
						cell = row.createCell(35);
						cell.setCellStyle(styleRight);
						cell.setCellValue(orderItem.getQty());	// 반품/교환수량
					}
					
					cell = row.createCell(42);
					cell.setCellStyle(styleRight);
					cell.setCellValue(orderItem.getPoint());	// 적립 예정 제품 포인트
					
					if("190,191".indexOf(order.getStatus()) != -1) {
						cell = row.createCell(68);
						cell.setCellStyle(styleRight);
						cell.setCellValue(orderItem.getQty());	// 취소 상품 수량
					}

					if(order.getGubun() == 2) {
						cell = row.createCell(69);
						cell.setCellStyle(styleRight);
						cell.setCellValue(orderItem.getQty());	// 교환 상품 수량
					}

					if(order.getGubun() == 3) {
						cell = row.createCell(70);
						cell.setCellStyle(styleRight);
						cell.setCellValue(orderItem.getQty());	// 반품 상품 수량
					}
					
					cell = row.createCell(11);
					cell.setCellStyle(styleCenter);
					cell.setCellValue(order.getClinicId());	// 병의원코드
					
					cell = row.createCell(12);
					cell.setCellStyle(styleCenter);
					cell.setCellValue(order.getClinicName().replaceAll("&amp;", "&"));	// 병의원명
				}
			}
		}

		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=order_" + Utils.getTimeStampString("yyyyMMdd") + ".xlsx");

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

	@GetMapping("/xml")
	@ResponseBody
	public void xml() {
		HttpServletResponse response = HttpRequestHelper.getCurrentResponse();
//		response.setContentType("application/octet-stream");
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment;filename=drpack" + Utils.getTimeStampString("yyyyMMddHHmmss") + ".zip");

		List<Order> list = orderService.getListForXml();
		
		try {
			ZipOutputStream zipOs = new ZipOutputStream(response.getOutputStream());
		
			for(Order order : list) {
				List<OrderItem> itemList = orderService.getOrderItemList(order);
//				OrderAddr addr = orderService.getOrderAddrInfo(order.getOrderid());
				
				zipOs.putNextEntry(new ZipEntry(order.getOrderid() + ".xml"));
				
				StringBuffer buff = new StringBuffer()
					.append("<?xml version=\"1.0\" encoding=\"euc-kr\"?>\n")
					.append("<OrderInfo>\n")
					.append("<OrderNum>" + order.getOrderid() + "</OrderNum>\n")
//					.append("<OrderDt>" + Utils.getTimeStampString(order.getOdate(), "yyyyMMdd") + "</OrderDt>\n")
					.append("<OrderDt>" + Utils.getTimeStampString("yyyyMMdd") + "</OrderDt>\n")
					.append("<PtntNum>" + order.getMemId() + "</PtntNum>\n")
					.append("<PtntNm>" + order.getOname() + "</PtntNm>\n")
//					.append("<PtntAddr>" + addr.getSzip() + " " + addr.getSaddr1() + " " + addr.getSaddr2() + "</PtntAddr>\n")
//					.append("<PtntTel>" + addr.getSmtel1() + addr.getSmtel2() + "</PtntTel>\n")
					.append("<InOutClsf>O</InOutClsf>\n")
					.append("<DataClsf>N</DataClsf>\n");
				
				for(OrderItem item : itemList) {
					buff.append("<MedItem>\n")
						.append("<MedCd>" + item.getPno() + "</MedCd>\n")
						.append("<MedNm>" + item.getPname() + "</MedNm>\n")
						.append("<TakeDays>" + (item.getDoseMonth() * item.getQty() * 30) + "</TakeDays>\n")
						.append("<Dose>" + item.getDosage() + "</Dose>\n")
						.append("<DayTakeCnt>" + item.getDoseCnt() + "</DayTakeCnt>\n")
						.append("<DrtsCd>" + item.getDoseMethod() + "</DrtsCd>\n")
						.append("</MedItem>\n");
				}
				
				buff.append("</OrderInfo>");
				
				byte[] b = buff.toString().getBytes("euc-kr");
				zipOs.write(b, 0, b.length);
				zipOs.closeEntry();
				
				order.setStatus("140");
				orderService.modifyStatus(order);
			}
			
			zipOs.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@PostMapping("/upload")
	@ResponseBody
	public ResultMessage upload(MultipartHttpServletRequest request) {
		AdminSession sess = AdminSession.getInstance();
		String uploadUrl = null;
		String[] passibleExts = { "xlsx" };

		try {
        	storageUploader.initStorageUploder(request);
       		storageUploader.setPassibleExts(passibleExts);

        	uploadUrl = storageUploader.upload("excel", "excel");

        	logger.debug("----------------- " + uploadUrl);
			if(uploadUrl == null) {
				return new ResultMessage(false, "업로드중 오류가 발생했습니다.");
			}
			
			orderService.uploadInvoice(uploadUrl, sess.getAdminNo());
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, e.getMessage());
		}

		return new ResultMessage(true, "등록되었습니다.");
	}
	
}
