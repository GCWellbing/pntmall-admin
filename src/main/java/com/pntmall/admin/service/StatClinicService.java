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
public class StatClinicService {
    public static final Logger logger = LoggerFactory.getLogger(StatClinicService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Param> getVisitList(Param param) {
    	return sst.selectList("StatClinic.visitList",param);
    }

    public List<Param> getVisitMonthList(Param param) {
    	return sst.selectList("StatClinic.visitMonthList",param);
    }

    public List<Param> getClinicVisitList(Param param) {
    	return sst.selectList("StatClinic.clinicVisitList",param);
    }

    public List<Param> getClinicVisitMonthList(Param param) {
    	return sst.selectList("StatClinic.clinicVisitMonthList",param);
    }

    public Param getNewTotal(Param param) {
    	return sst.selectOne("StatClinic.newTotal",param);
    }

    public Param getNewMonthTotal(Param param) {
    	return sst.selectOne("StatClinic.newMonthTotal",param);
    }

    public List<Param> getNewList(Param param) {
    	return sst.selectList("StatClinic.newList",param);
    }

    public List<Param> getNewMonthList(Param param) {
    	return sst.selectList("StatClinic.newMonthList",param);
    }

}
