package com.pntmall.admin.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.AgreeDocu;
import com.pntmall.admin.domain.MainVisual;

@Service
public class AgreeDocuService {
    public static final Logger logger = LoggerFactory.getLogger(AgreeDocuService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public AgreeDocu getInfo() {
    	return sst.selectOne("AgreeDocu.info");
    }

    @Transactional
    public void create(AgreeDocu agreeDocu) {
    	sst.insert("AgreeDocu.insert", agreeDocu);

    }



}
