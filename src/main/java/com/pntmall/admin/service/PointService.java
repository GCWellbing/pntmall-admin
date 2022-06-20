package com.pntmall.admin.service;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.Point;
import com.pntmall.admin.domain.PointConfig;
import com.pntmall.admin.domain.PointSearch;
import com.pntmall.common.utils.Utils;

@Service
public class PointService {
    public static final Logger logger = LoggerFactory.getLogger(PointService.class);

    @Autowired
    private SqlSessionTemplate sst;
    
    @Autowired
    private MemberService memberService;

    public Integer getCurrentPoint(Integer memNo) {
    	Point point = getCurrentInfo(memNo);
    	if(point == null) return 0;
    	else return point.getCurPoint();
    }
    
    public Point getCurrentInfo(Integer memNo) {
    	return sst.selectOne("Point.currentInfo", memNo);
    }
    
    public Point getInfoForUse(Integer memNo) {
    	return sst.selectOne("Point.infoForUse", memNo);
    }
    
    @Transactional
    public void addPoint(Point point) {
    	int curPoint = getCurrentPoint(point.getMemNo());
    	
    	if(point.getPoint() >= 0) {
	    	point.setCurPoint(curPoint + point.getPoint());
	    	point.setPrevPoint(curPoint);
	    	if(StringUtils.isEmpty(point.getEdate())) {
		    	Calendar cal = Calendar.getInstance();
		    	cal.add(Calendar.YEAR, 1);	// 유효기간 1년
		    	point.setEdate(Utils.getTimeStampString(cal.getTime(), "yyyy.MM.dd"));
	    	}
	    	
	    	point.setUsePoint(0);
	    	point.setBalance(point.getPoint());
	    	sst.insert("Point.insert", point);
    	} else {
	    	point.setCurPoint(curPoint + point.getPoint());
	    	point.setPrevPoint(curPoint);
	    	point.setUsePoint(0);
	    	point.setBalance(0);
	    	point.setEdate("");
	    	sst.insert("Point.insert", point);
	    	
	    	int _pointAmt = point.getPoint() * -1;
	    	while(true) {
	   			Point p = getInfoForUse(point.getMemNo());
	   			int _usePoint = _pointAmt <= p.getBalance() ? _pointAmt : p.getBalance();
	   			p.setUsePoint(_usePoint);
	   			sst.update("Point.updateUse", p);
	   			p.setOrderid(point.getOrderid());
	   			sst.insert("Point.insertUse", p);
	   			
	   			_pointAmt -= _usePoint;
	   			if(_pointAmt <= 0) break;
	   		}
    	}
    }
    
    public List<Point> getLogList(PointSearch pointSearch) {
    	return sst.selectList("Point.logList", pointSearch);
    }
    
    public Integer getLogCount(PointSearch pointSearch) {
    	return sst.selectOne("Point.logCount", pointSearch);
    }
    
    @Transactional
    public void create(Point point, String memIds) {
    	String[] memId = memIds.split("/");
    	for(String mid : memId) {
    		Member member = memberService.getInfo(mid);
    		point.setMemNo(member.getMemNo());
    		addPoint(point);
    	}
    }

    public List<PointConfig> getConfigList() {
    	return sst.selectList("Point.confList");
    }
    
    public void createConfig(PointConfig pointConfig) {
    	sst.insert("Point.insertConf", pointConfig);
    }
}
