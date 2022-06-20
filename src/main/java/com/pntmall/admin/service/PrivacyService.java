package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Privacy;
import com.pntmall.admin.domain.PrivacySearch;

@Service
public class PrivacyService {
    public static final Logger logger = LoggerFactory.getLogger(PrivacyService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Privacy> getList(PrivacySearch privacySearch) {
    	return sst.selectList("Privacy.list",privacySearch);
    }

    public Integer getCount(PrivacySearch privacySearch) {
    	return sst.selectOne("Privacy.count",privacySearch);
    }

    public Privacy getInfo(Integer privacyNo) {
    	return sst.selectOne("Privacy.info", privacyNo);
    }

    public void create(Privacy privacy) {
    	sst.insert("Privacy.insert", privacy);
    }

    public void modify(Privacy privacy) {
    	sst.update("Privacy.update", privacy);
    }

}
