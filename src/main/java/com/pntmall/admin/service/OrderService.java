package com.pntmall.admin.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pntmall.admin.domain.Order;
import com.pntmall.admin.domain.OrderAddr;
import com.pntmall.admin.domain.OrderGift;
import com.pntmall.admin.domain.OrderItem;
import com.pntmall.admin.domain.OrderMemo;
import com.pntmall.admin.domain.OrderRefund;
import com.pntmall.admin.domain.OrderSearch;
import com.pntmall.admin.domain.OrderShip;
import com.pntmall.admin.domain.OrderStatusCode;
import com.pntmall.admin.domain.PaymentLog;
import com.pntmall.admin.domain.Point;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.common.directSend.DirectsendKakaoAllimApi;
import com.pntmall.common.directSend.SendKatalk;
import com.pntmall.common.payment.KakaopayService;
import com.pntmall.common.payment.NpayService;
import com.pntmall.common.payment.TossService;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Service
public class OrderService {
    public static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Value("${directSend.front.returnUrl}")
	String directSendFrontReturnUrl;

    @Autowired
    private SqlSessionTemplate sst;
    
    @Autowired
    private TossService tossService;

    @Autowired
    private KakaopayService kakaopayService;

    @Autowired
    private NpayService npayService;

    @Autowired
    private SeqService seqService;

    @Autowired
    private PointService pointService;

    @Autowired
    private SapService sapService;

    @Autowired
    DirectsendKakaoAllimApi directsendKakaoAllimApi;

    public List<Order> getList(OrderSearch orderSearch) {
    	return sst.selectList("Order.list", orderSearch);
    }
    
    public Integer getCount(OrderSearch orderSearch) {
    	return sst.selectOne("Order.count", orderSearch);
    }
    
    public List<OrderStatusCode> getOrderStatusCodeList() {
    	return sst.selectList("Order.statusList");
    }
    
    public Order getOrderInfo(String orderid) {
    	return sst.selectOne("Order.orderInfo", orderid);
    }

    public OrderAddr getOrderAddrInfo(String orderid) {
    	return sst.selectOne("Order.addrInfo", orderid);
    }

    public OrderMemo getOrderMemoInfo(String orderid) {
    	return sst.selectOne("Order.memoInfo", orderid);
    }

    public void createMemo(OrderMemo orderMemo) {
    	sst.insert("Order.insertMemo", orderMemo);
    }
    
    public List<OrderShip> getOrderShipList(String orderid) {
    	return sst.selectList("Order.shipList", orderid);
    }
    
    public List<OrderItem> getOrderItemList(Order order) {
    	OrderShip orderShip = new OrderShip();
    	orderShip.setOrderid(order.getOrderid());
    	return getOrderItemList(orderShip);
    }
    
    public List<OrderItem> getOrderItemList(OrderShip orderShip) {
    	return sst.selectList("Order.itemList", orderShip);
    }
    
    public List<OrderStatusCode> getOrderStatusLogList(String orderid) {
    	return sst.selectList("Order.statusLogList", orderid);
    }
    
    public PaymentLog getPaymentLog(String orderid) {
    	PaymentLog paymentLog = new PaymentLog();
    	paymentLog.setOrderid(orderid);
    	paymentLog.setGubun(1);
    	
    	return  sst.selectOne("Order.paymentLog", paymentLog);
    }
    
    public PaymentLog getVirtualAccountInfo(String orderid) {
    	PaymentLog paymentLog = getPaymentLog(orderid);

    	try {
    		if(paymentLog != null) {
	        	ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(paymentLog.getLog());
				
				paymentLog.setBank(node.get("virtualAccount").get("bank").asText());
				paymentLog.setAccountNumber(node.get("virtualAccount").get("accountNumber").asText());
    		}
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
    	
    	return paymentLog;
    }
    
    public void createAddr(OrderAddr orderAddr) {
    	sst.insert("Order.insertAddr", orderAddr);
    }
    
    public void createRefundInfo(OrderRefund orderRefund) {
    	sst.insert("Order.insertRefund", orderRefund);
    }
    
    @SuppressWarnings("unchecked")
	@Transactional
    public void cancel(Order order, OrderRefund orderRefund) throws Exception {
    	String orgStatus = order.getStatus();

    	// 환불계좌 등록
    	if(!"".equals(orderRefund.getBank())) {
    		sst.update("Order.insertRefund", orderRefund);
    	}
    	
    	order.setStatus("190");
    	sst.update("Order.updateStatus", order);
    	sst.insert("Order.insertStatusLog", order);
    	
    	// 배송비 쿠폰 복원
    	if(StringUtils.isNotEmpty(order.getShipMcouponid())) {
    		sst.update("Coupon.refund", order.getShipMcouponid());
    	}
    	
    	// 상품쿠폰 복원
    	List<OrderItem> itemList = getOrderItemList(order);
    	for(OrderItem orderItem : itemList) {
    		if(StringUtils.isNotEmpty(orderItem.getMcouponid())) {
        		sst.update("Coupon.refund", orderItem.getMcouponid());
    		}
    	}
    	
    	// 포인트 환불
    	if(order.getPoint() > 0) {
    		Calendar cal = Calendar.getInstance();
    		cal.add(Calendar.YEAR, 1);
    		
    		Point point = sst.selectOne("Point.currentInfo", order.getMemNo());
    		Point p = new Point();
    		p.setMemNo(order.getMemNo());
    		p.setCurPoint(point.getCurPoint() + order.getPoint());
    		p.setPrevPoint(point.getCurPoint());
    		p.setPoint(order.getPoint());
    		p.setReason("019004");
    		p.setEdate(Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
    		p.setUsePoint(0);
    		p.setBalance(order.getPoint());
    		p.setCuser(order.getCuser());
    		p.setOrderid(order.getOrderid());
    		sst.insert("Point.insert", p);
    	}
    	
    	// 결제취소
    	if("120".equals(orgStatus) && order.getPayAmt() > 0) {
    		String log = "";
    		PaymentLog paymentLog = getPaymentLog(order.getOrderid());
    		
    		if(paymentLog == null) {
    			throw new Exception("결제정보가 없습니다.");
    		}
    		
    		Gson gson = new Gson();
    		Param p = new Param(gson.fromJson(paymentLog.getLog(), Map.class));

    		if("013004".equals(order.getPayType())) {	// npay
				try {
					JSONObject logJson = (JSONObject) (new JSONParser()).parse(paymentLog.getLog());
					JSONObject body = (JSONObject) logJson.get("body");
					
					p = new Param();
					p.set("paymentId", body.get("paymentId"));
					p.set("cancelAmount", order.getPayAmt());
					p.set("cancelReason", "관리자취소");
					p.set("cancelRequester", "2");	// 1:구매자 2:가맹점관리자
					p.set("taxScopeAmount", order.getPayAmt());
					p.set("taxExScopeAmount", 0);
					
					JSONObject json = npayService.cancel(p);
		    		int responseCode = (Integer) json.get("response_code");
		    		String code = (String) json.get("code");
				    if(responseCode == 200 && "Success".equals(code)) {
				    	log = json.toJSONString();
				    } else {
	                 	logger.error(order.getOrderid() + " : " + json.toJSONString());
						throw new RuntimeException("결제 취소중 오류가 발생했습니다.");
				    }
				} catch(Exception e) {
					logger.error(e.getMessage(), e);
					throw new RuntimeException("결제 취소중 오류가 발생했습니다.");
				}
    		} else if("013005".equals(order.getPayType())) {	// kakaopay
				p.set("cancel_amount", order.getPayAmt());
				p.set("cancel_tax_free_amount", 0);
				
				JSONObject json = kakaopayService.cancel(p);
	    		int responseCode = (Integer) json.get("response_code");
			    if(responseCode == 200) {
			    	log = json.toJSONString();
			    } else {
                 	logger.error(order.getOrderid() + " : " + json.toJSONString());
					throw new RuntimeException("결제 취소중 오류가 발생했습니다.");
			    }
    		} else {	// toss
    			// 환불계좌정보
    			Param bank = null;
    			if("013003".equals(order.getPayType()) && "120".equals(orgStatus)) {
	    			bank = new Param();
	    			bank.set("bank", orderRefund.getBank());
	    			bank.set("accountNumber", orderRefund.getAccount());
	    			bank.set("holderName", orderRefund.getDepositor());
    			}
    			
    			Param result = tossService.cancel(order.getOrderGubun(), p.get("paymentKey"), "관리자 취소", order.getPayAmt(), bank);
    			
    			if("".equals(result.get("code"))) {
    				log = result.get("message");
    			} else {
    				throw new Exception("Toss 결제 취소 오류가 발생했습니다.");
    			}
    		}
    		
    		PaymentLog plog = new PaymentLog();
    		plog.setOrderid(order.getOrderid());
    		plog.setGubun(2);
    		plog.setPayType(order.getPayType());
    		plog.setPayAmt(order.getPayAmt());
    		plog.setLog(log);
    		sst.insert("Order.insertPaymentLog", plog);
    		
	   		// 결제정보 SAP전송
	   		try {
		   		int plogNo = plog.getPlogNo();
		   	    sapService.billCollection(plogNo);
	   		} catch(Exception e) {
	   			logger.error(e.getMessage(), e);
	   		}
    		
    	}
    	
   		try {
			SendKatalk sendKatalk = new SendKatalk();
			sendKatalk.setUserTemplateNo(118);
			sendKatalk.setName(order.getOname());
			sendKatalk.setMobile(order.getOmtel1() + order.getOmtel2());
			sendKatalk.setNote1(order.getOrderid());
			sendKatalk.setNote2(Utils.formatMoney(order.getPayAmt()));
			sendKatalk.setNote3(directSendFrontReturnUrl);
			directsendKakaoAllimApi.send(sendKatalk);
   		} catch(Exception e) {
   			logger.error(e.getMessage(), e);
   		}
    	
    }
    
    @Transactional
    public void createReturn(Param param) {
    	AdminSession as = AdminSession.getInstance();
    	
    	String orderid = seqService.getId();
    	String orgOrderid = param.get("orderid");
    	
    	String[] idxs = param.getValues("idx");
    	
    	for(String idx : idxs) {
    		int itemNo = param.getInt("itemNo" + idx);
    		int qty = param.getInt("returnQty" + idx);
    		
    		if(qty > 0) {
				OrderItem orgItem = this.getItemInfo(itemNo);
				if(orgItem.getQty() - orgItem.getReturnQty() - orgItem.getExchangeQty() < qty) {
					throw new RuntimeException("반품 가능수량을 초과했습니다.");
				}

				OrderItem orderItem = new OrderItem();
				orderItem.setOrderid(orderid);
				orderItem.setShipNo(1);
				orderItem.setPno(orgItem.getPno());
				orderItem.setQty(qty);
				orderItem.setPname(orgItem.getPname());
				orderItem.setOrgItemNo(itemNo);
				orderItem.setSalePrice(orgItem.getSalePrice() * qty / orgItem.getQty());
				orderItem.setMemPrice(orgItem.getMemPrice() * qty / orgItem.getQty());
				orderItem.setDiscount(orgItem.getDiscount() * qty / orgItem.getQty());
				orderItem.setApplyPrice(orgItem.getApplyPrice() * qty / orgItem.getQty());
				orderItem.setPoint(orgItem.getPoint() * qty / orgItem.getQty());
				orderItem.setReturnQty(0);

				sst.insert("Order.insertItem", orderItem);

				// gift
				List<OrderGift> giftList = this.getGiftList(itemNo);
				for(OrderGift orderGift : giftList) {
					orderGift.setItemNo(orderItem.getItemNo());
					orderGift.setQty(qty);
					sst.insert("Order.insertGift", orderGift);
				}

				// 반품수량 업데이트
				orgItem.setQty(qty);
	    		sst.update("Order.updateReturnQty", orgItem);
    		}
    	}
    	
    	OrderShip orderShip = new OrderShip();
    	orderShip.setOrderid(orderid);
    	orderShip.setShipNo(1);
    	orderShip.setShipGubun(1);
    	
		// 주문배송 등록
		sst.insert("Order.insertShip", orderShip);
    	
		Order order = new Order();
		order.setOrderid(orderid);
		order.setGubun(3);	// 반품
		order.setDevice("P");
		order.setStatus("310");
		order.setOrgOrderid(orgOrderid);
		order.setCuser(as.getAdminNo());
		sst.insert("Order.insertReturnOrder", order);
		
		// addr
		OrderAddr orderAddr = new OrderAddr();
		orderAddr.setOrderid(orderid);
		orderAddr.setSname(param.get("sname"));
		orderAddr.setSzip(param.get("szip"));
		orderAddr.setSaddr1(param.get("saddr1"));
		orderAddr.setSaddr2(param.get("saddr2"));
		orderAddr.setSmtel1(param.get("smtel1"));
		orderAddr.setSmtel2(param.get("smtel2"));
		orderAddr.setStel1(param.get("stel1"));
		orderAddr.setStel2(param.get("stel2"));
		orderAddr.setMsg(param.get("msg"));
		orderAddr.setCuser(as.getAdminNo());
		sst.insert("Order.insertAddr", orderAddr);
		
		// 주문상태로그
		sst.insert("Order.insertStatusLog", order);
    }
    
    @Transactional
    public void createExchange(Param param) {
    	AdminSession as = AdminSession.getInstance();
    	
    	String orderid = seqService.getId();
    	String orgOrderid = param.get("orderid");
    	
    	String[] idxs = param.getValues("idx");
    	
    	for(String idx : idxs) {
    		int itemNo = param.getInt("itemNo" + idx);
    		int qty = param.getInt("returnQty" + idx);
    		
    		if(qty > 0) {
				OrderItem orgItem = this.getItemInfo(itemNo);
				if(orgItem.getQty() - orgItem.getReturnQty() - orgItem.getExchangeQty() < qty) {
					throw new RuntimeException("교환 가능수량을 초과했습니다.");
				}

				OrderItem orderItem = new OrderItem();
				orderItem.setOrderid(orderid);
				orderItem.setShipNo(1);
				orderItem.setPno(orgItem.getPno());
				orderItem.setQty(qty);
				orderItem.setPname(orgItem.getPname());
				orderItem.setOrgItemNo(itemNo);
				orderItem.setSalePrice(orgItem.getSalePrice() * qty / orgItem.getQty());
				orderItem.setMemPrice(orgItem.getMemPrice() * qty / orgItem.getQty());
				orderItem.setDiscount(orgItem.getDiscount() * qty / orgItem.getQty());
				orderItem.setApplyPrice(orgItem.getApplyPrice() * qty / orgItem.getQty());
				orderItem.setPoint(orgItem.getPoint() * qty / orgItem.getQty());
				orderItem.setReturnQty(0);

				sst.insert("Order.insertItem", orderItem);

				// gift
				List<OrderGift> giftList = this.getGiftList(itemNo);
				for(OrderGift orderGift : giftList) {
					orderGift.setItemNo(orderItem.getItemNo());
					orderGift.setQty(qty);
					sst.insert("Order.insertGift", orderGift);
				}

				// 교환수량 업데이트
				orgItem.setQty(qty);
	    		sst.update("Order.updateExchangeQty", orgItem);
    		}
    	}
    	
    	OrderShip orderShip = new OrderShip();
    	orderShip.setOrderid(orderid);
    	orderShip.setShipNo(1);
    	orderShip.setShipGubun(1);
    	
		// 주문배송 등록
		sst.insert("Order.insertShip", orderShip);
    	
		Order order = new Order();
		order.setOrderid(orderid);
		order.setGubun(2);	// 교환
		order.setDevice("P");
		order.setStatus("210");
		order.setOrgOrderid(orgOrderid);
		order.setCuser(as.getAdminNo());
		sst.insert("Order.insertReturnOrder", order);
		
		// addr
		OrderAddr orderAddr = new OrderAddr();
		orderAddr.setOrderid(orderid);
		orderAddr.setSname(param.get("sname"));
		orderAddr.setSzip(param.get("szip"));
		orderAddr.setSaddr1(param.get("saddr1"));
		orderAddr.setSaddr2(param.get("saddr2"));
		orderAddr.setSmtel1(param.get("smtel1"));
		orderAddr.setSmtel2(param.get("smtel2"));
		orderAddr.setStel1(param.get("stel1"));
		orderAddr.setStel2(param.get("stel2"));
		orderAddr.setMsg(param.get("msg"));
		orderAddr.setCuser(as.getAdminNo());
		sst.insert("Order.insertAddr", orderAddr);
		
		// 주문상태로그
		sst.insert("Order.insertStatusLog", order);
    }
    
    public List<OrderItem> getReturnItemList(String orderid) {
    	return sst.selectList("Order.returnItemList", orderid);
    }

    public List<OrderItem> getOrgItemList(String orgOrderid) {
    	return sst.selectList("Order.orgItemList", orgOrderid);
    }
    
    public OrderRefund getRefundInfo(String orderid) {
    	return sst.selectOne("Order.refundInfo", orderid);
    }
    
    @SuppressWarnings("unchecked")
	@Transactional
    public void modifyStatus(Order order) {
    	AdminSession sess = AdminSession.getInstance();
    	
    	int cuser = sess.getAdminNo();
    	order.setCuser(cuser);
    	sst.update("Order.updateStatus", order);
    	sst.insert("Order.insertStatusLog", order);
    	
    	if("220".equals(order.getStatus())) {	// 교환접수
        	String orderid = seqService.getId();
        	String orgOrderid = order.getOrderid();

        	List<OrderItem> itemList = this.getOrderItemList(order);
        	List<OrderItem> itemList1 = new ArrayList<OrderItem>();
        	List<OrderItem> itemList2 = new ArrayList<OrderItem>();
        	
        	for(OrderItem orderItem : itemList) {
        		if("Y".equals(orderItem.getColdYn())) {
        			itemList2.add(orderItem);
        		} else {
        			itemList1.add(orderItem);
        		}
        	}
        	
    		List<OrderItem>[] listArray = new ArrayList[2];
    		listArray[0] = itemList1;
    		listArray[1] = itemList2;

    		int shipNo = 1;
    		for(int i = 0; i < listArray.length; i++) {
    			List<OrderItem> list = listArray[i];
    			for(OrderItem orderItem : list) {
    				List<OrderGift> giftList = this.getGiftList(orderItem.getItemNo());

    				orderItem.setOrderid(orderid);
    				orderItem.setShipNo(shipNo);
    				orderItem.setOrgItemNo(orderItem.getItemNo());
    				sst.insert("Order.insertItem", orderItem);
    				
    				for(OrderGift orderGift : giftList) {
    					orderGift.setItemNo(orderItem.getItemNo());
    					sst.insert("Order.insertGift", orderGift);
    				}
    			}
    			
    			if(list.size() > 0) {
    		   		OrderShip orderShip = new OrderShip();
    		   		orderShip.setOrderid(orderid);
    		   		orderShip.setShipNo(shipNo);
    		   		orderShip.setShipGubun(i + 1);
    		   		orderShip.setDeliveryYn("N");
    		   		sst.insert("Order.insertShip", orderShip);
    				
    				shipNo++;
    			}
    		}

    		Order o = this.getOrderInfo(orgOrderid);
    		o.setOrderid(orderid);
    		o.setGroupid(orderid);
    		o.setOrgOrderid(orgOrderid);
    		o.setGubun(4);
    		o.setStatus("130");
    		o.setEscrow("N");
    		sst.insert("Order.insert", o);
    		
    		o.setCuser(sess.getAdminNo());
    		sst.insert("Order.copyAddr", o);
    	} else if("290".equals(order.getStatus())) {	// 교환 취소
    		order = getOrderInfo(order.getOrderid());
    		List<OrderItem> itemList = getOrderItemList(order);
    		
    		for(OrderItem orderItem : itemList) {
    			orderItem.setItemNo(orderItem.getOrgItemNo());
    			orderItem.setQty(orderItem.getQty() * -1);
    			sst.update("Order.updateExchangeQty", orderItem);
    		}
    	} else if("390".equals(order.getStatus())) {	// 반품 취소
    		order = getOrderInfo(order.getOrderid());
    		List<OrderItem> itemList = getOrderItemList(order);
    		
    		for(OrderItem orderItem : itemList) {
    			orderItem.setItemNo(orderItem.getOrgItemNo());
    			orderItem.setQty(orderItem.getQty() * -1);
    			sst.update("Order.updateReturnQty", orderItem);
    		}
    	} else if("380".equals(order.getStatus())) {	// 반품완료
    		OrderRefund orderRefund = getRefundInfo(order.getOrderid());
    		if(orderRefund == null || orderRefund.getAmt() == null || orderRefund.getPoint() == null) {
    			throw new RuntimeException("환불정보가 없습니다.");
    		}
    		
    		order = getOrderInfo(order.getOrderid());
    		
    		// 포인트 환불
    		if(orderRefund.getPoint() != 0) {
	    		Point point = new Point();
	    		point.setReason("019005");
	    		point.setPoint(orderRefund.getPoint());
	    		point.setMemNo(order.getMemNo());
	    		point.setOrderid(order.getOrderid());
	    		point.setCuser(cuser);
	    		
	    		pointService.addPoint(point);
    		}
    		
    		// PG환불
    		if(orderRefund.getAmt() > 0) {
        		order = getOrderInfo(order.getOrderid());
        		PaymentLog paymentLog = getPaymentLog(order.getOrgOrderid());
        		String log = "";
        		
        		if(paymentLog == null) {
        			throw new RuntimeException("결제정보가 없습니다.");
        		}
        		
        	    Gson gson = new Gson();
        		Param p = new Param(gson.fromJson(paymentLog.getLog(), Map.class));

        		if("013004".equals(paymentLog.getPayType())) {
    				try {
    					JSONObject logJson = (JSONObject) (new JSONParser()).parse(paymentLog.getLog());
    					JSONObject body = (JSONObject) logJson.get("body");
    					
    					p = new Param();
    					p.set("paymentId", body.get("paymentId"));
    					p.set("cancelAmount", orderRefund.getAmt());
    					p.set("cancelReason", "관리자취소");
    					p.set("cancelRequester", "2");	// 1:구매자 2:가맹점관리자
    					p.set("taxScopeAmount", orderRefund.getAmt());
    					p.set("taxExScopeAmount", 0);
    					
    					JSONObject json = npayService.cancel(p);
    		    		int responseCode = (Integer) json.get("response_code");
    		    		String code = (String) json.get("code");
    				    if(responseCode == 200 && ("Success".equals(code) || "CancelNotComplete".equals(code))) {
    				    	log = json.toJSONString();
    				    } else {
    	                 	logger.error(order.getOrderid() + " : " + json.toJSONString());
    						throw new RuntimeException("결제 취소중 오류가 발생했습니다.");
    				    }
    				} catch(Exception e) {
    					logger.error(e.getMessage(), e);
    					throw new RuntimeException("결제 취소중 오류가 발생했습니다.");
    				}
        		} else if("013005".equals(paymentLog.getPayType())) {
    				p.set("cancel_amount", orderRefund.getAmt());
    				p.set("cancel_tax_free_amount", 0);
    				
    				JSONObject json = kakaopayService.cancel(p);
    	    		int responseCode = (Integer) json.get("response_code");
    			    if(responseCode == 200) {
    					log = json.toJSONString();
    			    } else {
                     	logger.error(order.getOrderid() + " : " + json.toJSONString());
    					throw new RuntimeException("결제 취소중 오류가 발생했습니다.");
    			    }
        		} else {	// toss
        			try {
	        			// 환불계좌정보
	        			Param bank = null;
	        			if("013003".equals(paymentLog.getPayType())) {
	    	    			bank = new Param();
	    	    			bank.set("bank", orderRefund.getBank());
	    	    			bank.set("accountNumber", orderRefund.getAccount());
	    	    			bank.set("holderName", orderRefund.getDepositor());
	        			}
	        			
	        			Param result = tossService.cancel(order.getOrderGubun(), p.get("paymentKey"), "관리자 환불", orderRefund.getAmt(), bank);
	        			
	        			if("".equals(result.get("code"))) {
	        				log = result.get("message");
	        			} else {
	        				throw new RuntimeException("Toss 결제 취소 오류가 발생했습니다.");
	        			}
        			} catch(Exception e) {
        				throw new RuntimeException(e.getMessage());
        			}
        		}
        		
        		PaymentLog plog = new PaymentLog();
        		plog.setOrderid(order.getOrderid());
        		plog.setGubun(2);
        		plog.setPayType(order.getPayType());
        		plog.setPayAmt(orderRefund.getAmt());
        		plog.setLog(log);
        		sst.insert("Order.insertPaymentLog", plog);

        		// 알림톡
           		try {
        			SendKatalk sendKatalk = new SendKatalk();
        			sendKatalk.setUserTemplateNo(121);
        			sendKatalk.setName(order.getOname());
        			sendKatalk.setMobile(order.getOmtel1() + order.getOmtel2());
        			sendKatalk.setNote1(order.getOrderid());
        			sendKatalk.setNote2(Utils.formatMoney(order.getPayAmt()));
        			sendKatalk.setNote3(directSendFrontReturnUrl);
        			directsendKakaoAllimApi.send(sendKatalk);
           		} catch(Exception e) {
           			logger.error(e.getMessage(), e);
           		}
    		}
    		
    	}
    }

	public OrderItem getItemInfo(Integer itemNo) {
		return sst.selectOne("Order.itemInfo", itemNo);
	}

	public List<OrderGift> getGiftList(Integer itemNo) {
		return sst.selectList("Order.giftList", itemNo);
	}
	
	public List<Order> getListForXml() {
		return sst.selectList("Order.listForXml");
	}
	
    @Transactional
    public void uploadInvoice(String url, int adminNo) throws MalformedURLException, IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(new URL(url).openStream());
		
		for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			
			for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
				Row row = sheet.getRow(j);
				OrderShip orderShip = new OrderShip();
				orderShip.setOrderid(row.getCell(0).getStringCellValue());
				orderShip.setShipNo(1);
				orderShip.setShipper(row.getCell(1).getStringCellValue());
				orderShip.setInvoice(row.getCell(2).getCellType() == CellType.NUMERIC ? row.getCell(2).getNumericCellValue() + "" : row.getCell(2).getStringCellValue());
//				orderShip.setInvoice(row.getCell(2).getStringCellValue());
				sst.update("Order.updateInvoice", orderShip);

				Order order = new Order();
				order.setOrderid(row.getCell(0).getStringCellValue());
				order.setStatus("150");
				order.setCuser(adminNo);
				sst.update("Order.updateStatus", order);
				sst.insert("Order.insertStatusLog", order);
				
				// 알림톡
		   		try {
		   			Order _order = this.getOrderInfo(order.getOrderid());
					SendKatalk sendKatalk = new SendKatalk();
					sendKatalk.setUserTemplateNo(250);
					sendKatalk.setName(_order.getOname());
					sendKatalk.setMobile(_order.getOmtel1() + _order.getOmtel2());
					sendKatalk.setNote1(Utils.getTimeStampString("yyyy.MM.dd"));
					sendKatalk.setNote2("");
					sendKatalk.setNote3(directSendFrontReturnUrl);
					directsendKakaoAllimApi.send(sendKatalk);
		   		} catch(Exception e) {
		   			logger.error(e.getMessage(), e);
		   		}
			}
		}
    }

    public PaymentLog getPaymentLogInfoByNo(int plogNo) {
    	return sst.selectOne("Order.paymemtLogInfoByNo", plogNo);
    }

	public void modifyPaymentLogSap(PaymentLog paymentLog) {
		sst.update("Order.updatePaymentLogSap", paymentLog);
	}
	
	public Param getTotalOrderInfo(Integer memNo) {
		return sst.selectOne("Order.totOrderInfo", memNo);
	}

}
