package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Clinic;
import com.pntmall.admin.domain.ClinicImg;
import com.pntmall.admin.domain.ClinicJoin;
import com.pntmall.admin.domain.ClinicSearch;
import com.pntmall.admin.domain.Member;
import com.pntmall.admin.domain.SapVendor;
import com.pntmall.admin.domain.SapVendorOut;
import com.pntmall.common.directSend.DirectsendKakaoAllimApi;
import com.pntmall.common.type.Param;

@Service
public class ClinicService {
    public static final Logger logger = LoggerFactory.getLogger(ClinicService.class);

    @Autowired
    private SqlSessionTemplate sst;

    @Autowired
	private SapService sapService;

    @Autowired
	private MemberService memberService;

    @Autowired
	DirectsendKakaoAllimApi directsendKakaoAllimApi;

    public List<Clinic> getList(ClinicSearch ClinicSearch) {
    	return sst.selectList("Clinic.list",ClinicSearch);
    }

    public Integer getCount(ClinicSearch ClinicSearch) {
    	return sst.selectOne("Clinic.count",ClinicSearch);
    }

    public Clinic getInfo(Integer memNo) {
    	return sst.selectOne("Clinic.info", memNo);
    }

    public Clinic getInfo(String memId) {
    	return sst.selectOne("Clinic.infoById", memId);
    }

    public List<ClinicJoin> getClinicJoinList(Integer memNo) {
    	return sst.selectList("Clinic.getClinicJoinList",memNo);
    }

    public List<ClinicImg> getClinicImgList(Integer memNo) {
    	return sst.selectList("Clinic.getClinicImgList",memNo);
    }

    @Transactional
    public void modify(Clinic clinic, Param param) {
    	sst.update("Clinic.update", clinic);
    	sst.update("Clinic.updateClinic", clinic);

    	Integer memNo = clinic.getMemNo();

    	// 이미지
    	sst.delete("Clinic.deleteClinicImg", memNo);
    	String[] gubun = param.getValues("gubun");
    	String[] attach = param.getValues("attach");
    	String[] attachOrgName = param.getValues("attachOrgName");

    	for(int i = 0; i < gubun.length; i++) {
    		ClinicImg clinicImg = new ClinicImg();
    		clinicImg.setMemNo(memNo);
    		clinicImg.setGubun(gubun[i]);
    		clinicImg.setAttach(attach[i]);
    		clinicImg.setAttachOrgName(attachOrgName[i]);
    		clinicImg.setCuser(clinic.getCuser());
    		sst.insert("Clinic.insertClinicImg", clinicImg);
    	}

    }

    @Transactional
    public void createJoinMulti(ClinicJoin clinicJoin) {
    	String[] chkMemNo = clinicJoin.getChkMemNo().split(",");

    	for(int i = 0; i < chkMemNo.length; i++) {
    		clinicJoin.setMemNo(Integer.parseInt(chkMemNo[i]));
    		createJoin(clinicJoin);
    	}
    }

    //@Transactional
    public void createJoin(ClinicJoin clinicJoin) {
    	sst.insert("Clinic.insertJoin", clinicJoin);
    	SapVendor sapVendor = sst.selectOne("Clinic.infoSap", clinicJoin.getMemNo());

    	//SAP연동
    	if("006002".equals(clinicJoin.getApprove())) {
	    	SapVendorOut sapVendorOut = new SapVendorOut();
	    	try {
	    		sapVendorOut = sapService.setVendor(sapVendor);
	    		logger.info("------------ sapVendorOut " + sapVendorOut);

	    		if(!("A".equals(sapVendorOut.getPntResult())) ) {
	    			throw new RuntimeException(clinicJoin.getMemNo()+" 인터페이스 에러!!"+sapVendorOut.getPntMsg());
	    		}
	    		sapVendorOut.setMemNo(clinicJoin.getMemNo());
	    		sst.insert("Clinic.updateClinicVendor", sapVendorOut);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("인터페이스 에러!!"+e.getMessage());
			}

    	}
    	//탈퇴
    	if("006004".equals(clinicJoin.getApprove())) {
    		Member member = new Member();
    		member.setMemNo(clinicJoin.getMemNo());
    		member.setSecedeMemo(clinicJoin.getReason());
    	    member.setSecedeRsn("020008");
    	    member.setUuser(clinicJoin.getCuser());
    	    memberService.updateSecede(member);

    	    //자유게시판
    	    sst.delete("Clinic.deleteBbs", member);
    	    sst.delete("Clinic.deleteBbsComment", member);
    	}

    }

    public List<Clinic> getRecommendList(ClinicSearch ClinicSearch) {
    	return sst.selectList("Clinic.recommendList",ClinicSearch);
    }

    public Member infoMem(Integer memNo) {
    	return sst.selectOne("Clinic.infoMem", memNo);
    }

}
