package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.common.type.Param;

@Service
public class OrderStatService {
    public static final Logger logger = LoggerFactory.getLogger(OrderStatService.class);

    @Autowired
    private SqlSessionTemplate sst;
    
	public List<Param> getSaleRankList(Param param) {
    	return sst.selectList("OrderStat.saleRank", param);
    }

	public List<Param> getSaleList(Param param) {
    	return sst.selectList("OrderStat.saleList" + param.get("gubun"), param);
    }

	public List<Param> getOrderList(Param param) {
		if(param.getInt("gubun") == 1) {
			param.set("odate", "DATE(A.ODATE)");
		} else if(param.getInt("gubun") == 2) {
			param.set("odate", "DATE_FORMAT(A.ODATE, '%H:00')");
		} else {
			param.set("odate", "DATE_FORMAT(A.ODATE, '%Y.%m')");
		}
    	return sst.selectList("OrderStat.orderList", param);
    }

	public List<Param> getOrderAgeList(Param param) {
		if(param.getInt("gubun") == 1) {
			param.set("odate", "DATE(V.ODATE)");
		} else {
			param.set("odate", "DATE_FORMAT(V.ODATE, '%Y.%m')");
		}
    	return sst.selectList("OrderStat.orderAgeList", param);
    }

	public List<Param> getOrderGenderList(Param param) {
		if(param.getInt("gubun") == 1) {
			param.set("odate", "DATE(A.ODATE)");
		} else {
			param.set("odate", "DATE_FORMAT(A.ODATE, '%Y.%m')");
		}
    	return sst.selectList("OrderStat.orderGenderList", param);
    }

		
}
