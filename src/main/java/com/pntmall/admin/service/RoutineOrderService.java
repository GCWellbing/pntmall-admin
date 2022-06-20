package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.RoutineOrder;
import com.pntmall.admin.domain.RoutineOrderDate;
import com.pntmall.admin.domain.RoutineOrderItem;
import com.pntmall.admin.domain.RoutineOrderSearch;

@Service
public class RoutineOrderService {
    public static final Logger logger = LoggerFactory.getLogger(RoutineOrderService.class);

    @Autowired
    private SqlSessionTemplate sst;
    
    public List<RoutineOrder> getList(RoutineOrderSearch routineOrderSearch) {
    	return sst.selectList("RoutineOrder.list", routineOrderSearch);
    }
    
    public Integer getCount(RoutineOrderSearch routineOrderSearch) {
    	return sst.selectOne("RoutineOrder.count", routineOrderSearch);
    }
    
    public RoutineOrder getInfo(String orderid) {
    	return sst.selectOne("RoutineOrder.info", orderid);
    }
    
    public List<RoutineOrderItem> getItemList(String orderid) {
    	return sst.selectList("RoutineOrder.itemList", orderid);
    }
    
    public List<RoutineOrderDate> getDateList(String orderid) {
    	return sst.selectList("RoutineOrder.dateList", orderid);
    }
    
    @Transactional
    public void modifyDate(RoutineOrderDate routineOrderDate) {
    	sst.update("RoutineOrder.updateDate", routineOrderDate);
    	sst.insert("RoutineOrder.insertDateLog", routineOrderDate.getDno());
    }
}
