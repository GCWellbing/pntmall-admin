package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Sensitive;
import com.pntmall.admin.domain.SensitiveSearch;

@Service
public class SensitiveService {
    public static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Sensitive> getList(SensitiveSearch sensitiveSearch) {
    	return sst.selectList("Sensitive.list",sensitiveSearch);
    }

    public Integer getCount(SensitiveSearch sensitiveSearch) {
    	return sst.selectOne("Sensitive.count",sensitiveSearch);
    }

    public Sensitive getInfo(Integer sensitiveNo) {
    	return sst.selectOne("Sensitive.info", sensitiveNo);
    }

    public void create(Sensitive sensitive) {
    	sst.insert("Sensitive.insert", sensitive);
    }

    public void modify(Sensitive sensitive) {
    	sst.update("Sensitive.update", sensitive);
    }

}
