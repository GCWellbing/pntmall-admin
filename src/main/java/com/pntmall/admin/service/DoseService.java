package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Dose;

@Service
public class DoseService {
    public static final Logger logger = LoggerFactory.getLogger(DoseService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Dose> getList() {
    	return getList(null);
    }

    public List<Dose> getList(String content) {
    	return sst.selectList("Dose.list", content);
    }

    public Dose getInfo(Integer doseNo) {
    	return sst.selectOne("Dose.info", doseNo);
    }

    public void create(Dose dose) {
    	sst.insert("Dose.insert", dose);
    }

    public void modify(Dose dose) {
    	sst.update("Dose.update", dose);
    }
    
    public void remove(Dose dose) {
    	sst.update("Dose.delete", dose);
    }

    @Transactional
    public void modifyRank(String[] doseNo, Integer adminNo) {
    	for(int i = 0; i < doseNo.length; i++) {
    		Dose dose = new Dose();
    		dose.setDoseNo(Integer.parseInt(doseNo[i]));
    		dose.setRank(i + 1);
    		dose.setCuser(adminNo);
    		sst.update("Dose.updateRank", dose);
    	}
    }
}
