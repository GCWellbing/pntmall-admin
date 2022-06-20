package com.pntmall.admin.service;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.ClinicAdjust;
import com.pntmall.admin.domain.ClinicAdjustDetail;
import com.pntmall.admin.domain.ClinicAdjustSearch;
import com.pntmall.common.directSend.DirectsendKakaoAllimApi;
import com.pntmall.common.directSend.SendKatalk;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;

@Service
public class ClinicAdjustService {
    public static final Logger logger = LoggerFactory.getLogger(ClinicAdjustService.class);

    @Value("${directSend.clinic.returnUrl}")
	String directSendClinicReturnUrl;

    @Autowired
    private SqlSessionTemplate sst;

    @Autowired
    private DirectsendKakaoAllimApi directsendKakaoAllimApi;

	public List<ClinicAdjust> getList(ClinicAdjustSearch clinicAdjustSearch) {
    	return sst.selectList("ClinicAdjust.list", clinicAdjustSearch);
    }

    public Integer getCount(ClinicAdjustSearch clinicAdjustSearch) {
    	return sst.selectOne("ClinicAdjust.count", clinicAdjustSearch);
    }

    public ClinicAdjust getInfo(Integer sno) {
    	return sst.selectOne("ClinicAdjust.info", sno);
    }

    public void create(ClinicAdjustSearch clinicAdjustSearch) {
    	sst.insert("ClinicAdjust.insert", clinicAdjustSearch);
    }

    @Transactional
    public void modifyFee(Param param) {
    	String[] snos = param.getValues("sno");
    	for(String sno : snos) {
    		ClinicAdjust clinicAdjust = new ClinicAdjust();
    		clinicAdjust.setSno(Integer.parseInt(sno));
    		clinicAdjust.setTotSupplyAmt(param.getInt("totSupplyAmt" + sno));
    		clinicAdjust.setFee(param.getFloat("fee" + sno));
    		clinicAdjust.setPromoFee(param.getFloat("promoFee" + sno));
    		clinicAdjust.setPickupFee(param.getFloat("pickupFee" + sno));
    		clinicAdjust.setDpackFee(param.getFloat("dpackFee" + sno));
    		clinicAdjust.setCuser(param.getInt("cuser"));

    		sst.update("ClinicAdjust.updateFee", clinicAdjust);
    	}
    }

    @Transactional
    public void modifyStatus(Param param) {
    	String[] snos = param.getValues("sno");

		for(String sno : snos) {
    		ClinicAdjust clinicAdjust = new ClinicAdjust();
    		clinicAdjust.setSno(Integer.parseInt(sno));
    		clinicAdjust.setStatus(param.getInt("status"));
    		clinicAdjust.setDeadline(param.get("deadline"));
    		clinicAdjust.setCuser(param.getInt("cuser"));

    		sst.update("ClinicAdjust.updateStatus", clinicAdjust);
    		sst.update("ClinicAdjust.insertStatusLog", clinicAdjust);

    		// sendtalk
    		try {
    			ClinicAdjust ca = getInfo(Integer.parseInt(sno));

	    		if(param.getInt("status") == 20) {	// 정산예정
	    			SendKatalk sendKatalk = new SendKatalk();
	    			sendKatalk.setUserTemplateNo(157);
	    			sendKatalk.setMobile(ca.getAlarmTel1() + ca.getAlarmTel2());
	    			sendKatalk.setName(Utils.unSafeHTML(ca.getClinicName()));
	    			sendKatalk.setNote1(ca.getYear() + " " + ca.getQuarter() + "분기");
	    			sendKatalk.setNote2(Utils.formatMoney(ca.getAdjustAmt()));
	    			sendKatalk.setNote3(ca.getDeadline());
	    			sendKatalk.setNote4(directSendClinicReturnUrl);
    				directsendKakaoAllimApi.send(sendKatalk);
	    		} else if(param.getInt("status") == 30) {	// 세금계산서발행요청
	    			SendKatalk sendKatalk = new SendKatalk();
	    			sendKatalk.setUserTemplateNo(160);
	    			sendKatalk.setMobile(ca.getAlarmTel1() + ca.getAlarmTel2());
	    			sendKatalk.setName(Utils.unSafeHTML(ca.getClinicName()));
		   			sendKatalk.setNote1(ca.getYear() + " " + ca.getQuarter() + "분기");
	    			sendKatalk.setNote2(Utils.formatMoney(ca.getAdjustAmt()));
	    			sendKatalk.setNote3(ca.getDeadline());
	    			sendKatalk.setNote4(directSendClinicReturnUrl);
    				directsendKakaoAllimApi.send(sendKatalk);
	    		} else if(param.getInt("status") == 40) {	// 세금계산서수정요청
	    			SendKatalk sendKatalk = new SendKatalk();
	    			sendKatalk.setUserTemplateNo(166);
	    			sendKatalk.setMobile(ca.getAlarmTel1() + ca.getAlarmTel2());
	    			sendKatalk.setName(Utils.unSafeHTML(ca.getClinicName()));
	    			sendKatalk.setNote1(ca.getYear() + " " + ca.getQuarter() + "분기");
	    			sendKatalk.setNote2(directSendClinicReturnUrl);
    				directsendKakaoAllimApi.send(sendKatalk);
	    		} else if(param.getInt("status") == 50) {	// 세금계산서확인
	    			SendKatalk sendKatalk = new SendKatalk();
	    			sendKatalk.setUserTemplateNo(163);
	    			sendKatalk.setMobile(ca.getAlarmTel1() + ca.getAlarmTel2());
	    			sendKatalk.setName(Utils.unSafeHTML(ca.getClinicName()));
	    			sendKatalk.setNote1(ca.getYear() + " " + ca.getQuarter() + "분기");
	    			sendKatalk.setNote2(directSendClinicReturnUrl);
    				directsendKakaoAllimApi.send(sendKatalk);
	    		} else if(param.getInt("status") == 70) {	// 지급완료
	    			SendKatalk sendKatalk = new SendKatalk();
	    			sendKatalk.setUserTemplateNo(169);
	    			sendKatalk.setMobile(ca.getAlarmTel1() + ca.getAlarmTel2());
	    			sendKatalk.setName(Utils.unSafeHTML(ca.getClinicName()));
	    			sendKatalk.setNote1(ca.getYear() + " " + ca.getQuarter() + "분기");
	    			sendKatalk.setNote2(Utils.formatMoney(ca.getAdjustAmt()));
	    			sendKatalk.setNote3(directSendClinicReturnUrl);
    				directsendKakaoAllimApi.send(sendKatalk);
	    		} else if(param.getInt("status") == 80) {	// 미정산
	    			SendKatalk sendKatalk = new SendKatalk();
	    			sendKatalk.setUserTemplateNo(172);
	    			sendKatalk.setMobile(ca.getAlarmTel1() + ca.getAlarmTel2());
	    			sendKatalk.setName(Utils.unSafeHTML(ca.getClinicName()));
	    			sendKatalk.setNote1(ca.getYear() + " " + ca.getQuarter() + "분기");
	    			sendKatalk.setNote2(Utils.formatMoney(ca.getAdjustAmt()));
	    			sendKatalk.setNote3(directSendClinicReturnUrl);
    				directsendKakaoAllimApi.send(sendKatalk);
	    		}
    		} catch(Exception e) {
    			logger.error(e.getMessage(), e);
    		}
    	}
    }

    public List<ClinicAdjustDetail> getDetailList(ClinicAdjust clinicAdjust) {
    	return sst.selectList("ClinicAdjust.detailList", clinicAdjust);
    }

    public List<ClinicAdjust> getListForSap(Param param) {
    	String[] sno = param.getValues("sno");
    	List<String> list = Arrays.asList(sno);

    	return getListForSap(list);
    }

    public List<ClinicAdjust> getListForSap(List<String> list) {
    	return sst.selectList("ClinicAdjust.listForSap", list);
    }

    public void modifySap(ClinicAdjust clinicAdjust) {
    	sst.update("ClinicAdjust.updateSap", clinicAdjust);
    }

}
