package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.MonthlyRecom;
import com.pntmall.admin.domain.SetProduct;
import com.pntmall.common.type.Param;

@Service
public class MonthlyRecomService {
    public static final Logger logger = LoggerFactory.getLogger(MonthlyRecomService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<MonthlyRecom> getList() {
    	return sst.selectList("MonthlyRecom.list");
    }


    @Transactional
    public void create(MonthlyRecom monthlyRecom, Param param) {
    	// 전시 제품
    	sst.delete("MonthlyRecom.delete");

    	String[] pno = param.getValues("pno");
		String[] gradeNo = param.getValues("gradeNo");
    	for(int i = 0; i < pno.length; i++) {
    		monthlyRecom.setPno(Integer.parseInt(pno[i]));
    		monthlyRecom.setStatus("S");
    		monthlyRecom.setRank(i);
			monthlyRecom.setGradeNo(Integer.valueOf(gradeNo[i]));
    		sst.insert("MonthlyRecom.insert", monthlyRecom);
    	}

    }



}
