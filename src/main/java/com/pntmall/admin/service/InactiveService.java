package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.MemberSearch;
import com.pntmall.admin.etc.AdminSession;
import com.pntmall.common.type.Param;

@Service
public class InactiveService {
    public static final Logger logger = LoggerFactory.getLogger(InactiveService.class);

    @Autowired
    private SqlSessionTemplate sst;

    @Autowired
	private MemberService memberService;

    public List<Member> getList(MemberSearch memberSearch) {
    	return sst.selectList("Inactive.list",memberSearch);
    }

    public Integer getCount(MemberSearch memberSearch) {
    	return sst.selectOne("Inactive.count",memberSearch);
    }

    @Transactional
    public void activeMember(Param param) {
		String[] chkMemNos = param.getValues("chkMemNo");
		for(String memNo :  chkMemNos) {
			Member member = new Member();
			member.setMemNo(Integer.parseInt(memNo));
			sst.update("Inactive.activeMember", member);
			sst.update("Inactive.insertMemberLog", member);
		}

    }

    @Transactional
    public void secedeMember(Param param) {
    	AdminSession sess = AdminSession.getInstance();

    	String secedeRsn = param.get("secedeRsn");
    	String secedeMemo = param.get("secedeMemo");
		String[] chkMemNos = param.getValues("chkMemNo");
		for(String memNo :  chkMemNos) {
			Member member = new Member();
			member.setMemNo(Integer.parseInt(memNo));
			member.setSecedeRsn(secedeRsn);
			member.setSecedeMemo(secedeMemo);
			member.setUuser(sess.getAdminNo());
			memberService.updateSecede(member);
		}

    }

}
