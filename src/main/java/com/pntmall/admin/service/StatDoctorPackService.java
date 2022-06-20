package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.MemberCs;
import com.pntmall.admin.domain.MemberSearch;
import com.pntmall.common.type.Param;

@Service
public class StatDoctorPackService {
    public static final Logger logger = LoggerFactory.getLogger(StatDoctorPackService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Param> getDailyList(Param param) {
    	return sst.selectList("StatMember.dailyList",param);
    }

    public List<Param> getWeekList(Param param) {
    	return sst.selectList("StatMember.weekList",param);
    }

    public List<Param> getTimeList(Param param) {
    	return sst.selectList("StatMember.timeList",param);
    }

    public List<Param> getMonthList(Param param) {
    	return sst.selectList("StatMember.monthList",param);
    }

}
