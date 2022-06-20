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

@Service
public class MemberService {
    public static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Member> getList(MemberSearch memberSearch) {
    	return sst.selectList("Member.list",memberSearch);
    }

    public Integer getCount(MemberSearch memberSearch) {
    	return sst.selectOne("Member.count",memberSearch);
    }

    public Member getInfo(Integer memberNo) {
    	return sst.selectOne("Member.info", memberNo);
    }

    public Member getInfo(String memId) {
    	return sst.selectOne("Member.infoById", memId);
    }

    public List<MemberCs> getCsList(Integer memberNo) {
    	return sst.selectList("Member.cslist",memberNo);
    }

    public void modify(Member member) {
    	sst.update("Member.update", member);
    }

    public void createCs(MemberCs memberCs) {
    	sst.insert("Member.insertCs", memberCs);
    }

    public void modifyCs(MemberCs memberCs) {
    	sst.update("Member.updateCs", memberCs);
    }

    @Transactional
    public void updateSecede(Member member) {
    	//회원정보
    	sst.update("Member.updateSecede", member);
    	//SNS연결정보
    	sst.delete("Member.deleteSns", member);
     	//배송지정보
    	sst.delete("Member.deleteAddress", member);
    	//포인트정보
    	sst.delete("Member.deletePoint", member);
    	//쿠폰정보
    	sst.delete("Member.deleteMCoupon", member);

    }

}
