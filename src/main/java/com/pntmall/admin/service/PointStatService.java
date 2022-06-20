package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.common.type.Param;

@Service
public class PointStatService {
    public static final Logger logger = LoggerFactory.getLogger(PointStatService.class);

    @Autowired
    private SqlSessionTemplate sst;
    
	public List<Param> getList(Param param) {
		if(param.getInt("gubun") == 1) {
			param.set("cdate", "DATE(A.CDATE)");
		} else if(param.getInt("gubun") == 2) {
			param.set("cdate", "DATE_FORMAT(A.CDATE, '%Y.%m')");
		}
    	return sst.selectList("PointStat.list", param);
    }
	
		
}
