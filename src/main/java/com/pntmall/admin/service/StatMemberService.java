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
public class StatMemberService {
    public static final Logger logger = LoggerFactory.getLogger(StatMemberService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public Param getJoinTotal(Param param) {
    	return sst.selectOne("StatMember.joinTotal",param);
    }

    public List<Param> getJoinList(Param param) {
    	return sst.selectList("StatMember.joinList",param);
    }

    public List<Param> getJoinMonthList(Param param) {
    	return sst.selectList("StatMember.joinMonthList",param);
    }

    public List<Param> getVisitList(Param param) {
    	return sst.selectList("StatMember.visitList",param);
    }

    public List<Param> getVisitMonthList(Param param) {
    	return sst.selectList("StatMember.visitMonthList",param);
    }

    public Param getAgeTotal(Param param) {
    	return sst.selectOne("StatMember.ageTotal",param);
    }

    public List<Param> getAgeList(Param param) {
    	return sst.selectList("StatMember.ageList",param);
    }

}
