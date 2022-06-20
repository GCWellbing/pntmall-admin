package com.pntmall.admin.service;

import java.util.ArrayList;
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
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.common.ResultMessage;
import com.pntmall.common.type.Param;

@Service
public class SecessionService {
    public static final Logger logger = LoggerFactory.getLogger(SecessionService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Member> getList(MemberSearch memberSearch) {
    	return sst.selectList("Secession.list",memberSearch);
    }

    public Integer getCount(MemberSearch memberSearch) {
    	return sst.selectOne("Secession.count",memberSearch);
    }

    public Member getInfo(Integer memberNo) {
    	Member member = sst.selectOne("Secession.info", memberNo);

		if(member.getSecedeRsn() != null) {
			String[] secedeRsn = member.getSecedeRsn().split(",");
			member.setSecedeRsnName(sst.selectOne("Secession.secedeRsnName", secedeRsn));
		}

    	return member;
    }

    @Transactional
    public void deleteMember(Param param) {
    	AdminSession sess = AdminSession.getInstance();

		String[] chkMemNos = param.getValues("chkMemNo");
		for(String memNo :  chkMemNos) {
			Member member = new Member();
			member.setUuser(sess.getAdminNo());
			member.setMemNo(Integer.parseInt(memNo));
			sst.update("Secession.deleteMember", member);
			sst.update("Secession.deleteClinic", member);
		}

    }

}
