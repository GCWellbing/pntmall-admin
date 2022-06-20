package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.BankCard;
import com.pntmall.admin.domain.Code;

@Service
public class CodeService {
    public static final Logger logger = LoggerFactory.getLogger(CodeService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Code> getList1() {
    	return sst.selectList("Code.list1");
    }

    public List<Code> getList2(String code1) {
    	return sst.selectList("Code.list2", code1);
    }

    public Code getInfo(Code code) {
    	return sst.selectOne("Code.info", code);
    }

    public String getName(String code) {
    	Code c = new Code();
    	c.setCode1(code.substring(0, 3));
    	c.setCode2(code.substring(3));
    	c = sst.selectOne("Code.info", c);
    	return c.getName();
    }

    public void create1(Code code) {
    	sst.insert("Code.insert1", code);
    }

    public void create2(Code code) {
    	sst.insert("Code.insert2", code);
    }

    public void modify(Code code) {
    	sst.update("Code.update", code);
    }
    
    public void remove(Code code) {
    	sst.update("Code.delete", code);
    }
    
    @Transactional
    public void modifyRank(String code1, String[] code2, Integer adminNo) {
    	for(int i = 0; i < code2.length; i++) {
    		Code code = new Code();
    		code.setCode1(code1);
    		code.setCode2(code2[i]);
    		code.setRank(i + 1);
    		code.setCuser(adminNo);
    		sst.update("Code.updateRank", code);
    	}
    }

    public List<BankCard> getBankCardList(BankCard bankCard) {
    	return sst.selectList("Code.bankCardList", bankCard);
    }
    
    public BankCard getBankCardInfo(BankCard bankCard) {
    	List<BankCard> list = getBankCardList(bankCard);
    	if(list.size() > 0) return list.get(0);
    	else return null;
    }
    
    public String getSapCd(BankCard bankCard) {
    	BankCard b = getBankCardInfo(bankCard);
    	if(b != null) return b.getSapCd();
    	else return null;
    }

}
