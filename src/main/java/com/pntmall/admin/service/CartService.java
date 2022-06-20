package com.pntmall.admin.service;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Cart;
import com.pntmall.common.type.Param;

@Service
public class CartService {
    public static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private SqlSessionTemplate sst;

    @Autowired
    private SeqService seqService;

    @Transactional
    public void createDrPack(HttpServletRequest request) {
    	Param param = new Param(request.getParameterMap());
    	
    	// 바로구매 삭제
    	if("Y".equals(param.get("directYn"))) {
    		sst.delete("Cart.deleteDirect", param.getInt("memNo"));
    		sst.update("Cart.setOrderYnToN", param.getInt("memNo"));
    	}
    	
    	// 기존 장바구니 삭제
    	sst.delete("Cart.deleteDrPack", param.getInt("memNo"));
    	
    	String[] pnos = param.getValues("pno");
    	for(String pno : pnos) {
    		Cart cart = new Cart();
    		cart.setCartid(seqService.getId());
    		cart.setGubun(4);
    		cart.setMemNo(param.getInt("memNo"));
    		cart.setPno(Integer.parseInt(pno));
    		cart.setQty(1);
    		cart.setDirectYn("N");
    		cart.setOrderYn("Y");
    		
    		create(cart);
    	}
    }

    public void create(Cart cart) {
    	sst.insert("Cart.insert", cart);
    }

}
