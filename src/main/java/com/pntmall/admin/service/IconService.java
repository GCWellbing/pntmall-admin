package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Icon;

@Service
public class IconService {
    public static final Logger logger = LoggerFactory.getLogger(IconService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Icon> getList() {
    	return sst.selectList("Icon.list");
    }

    public Icon getInfo(Integer iconNo) {
    	return sst.selectOne("Icon.info", iconNo);
    }

    public void create(Icon icon) {
    	sst.insert("Icon.insert", icon);
    }

    public void modify(Icon icon) {
    	sst.update("Icon.update", icon);
    }
    
    public void remove(Icon icon) {
    	sst.update("Icon.delete", icon);
    }

    @Transactional
    public void modifyRank(String[] doseNo, Integer adminNo) {
    	for(int i = 0; i < doseNo.length; i++) {
    		Icon icon = new Icon();
    		icon.setIconNo(Integer.parseInt(doseNo[i]));
    		icon.setRank(i + 1);
    		icon.setCuser(adminNo);
    		sst.update("Icon.updateRank", icon);
    	}
    }
}
