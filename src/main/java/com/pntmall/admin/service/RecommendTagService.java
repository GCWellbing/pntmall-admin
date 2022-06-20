package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.RecommendTag;

@Service
public class RecommendTagService {
    public static final Logger logger = LoggerFactory.getLogger(RecommendTagService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<RecommendTag> getList() {
    	return sst.selectList("RecommendTag.list");
    }

    public RecommendTag getInfo(RecommendTag recommendTag) {
    	return sst.selectOne("RecommendTag.info", recommendTag);
    }

    public void create(RecommendTag recommendTag) {
    	sst.insert("RecommendTag.insert", recommendTag);
    }

    public void modify(RecommendTag recommendTag) {
    	sst.update("RecommendTag.update", recommendTag);
    }

    public void remove(RecommendTag recommendTag) {
    	sst.update("RecommendTag.delete", recommendTag);
    }

    @Transactional
    public void modifyRank(String[] tno, Integer adminNo) {
    	for(int i = 0; i < tno.length; i++) {
    		RecommendTag recommendTag = new RecommendTag();
    		recommendTag.setTno(Integer.parseInt(tno[i]));
    		recommendTag.setRank(i + 1);
    		recommendTag.setCuser(adminNo);
    		sst.update("RecommendTag.updateRank", recommendTag);
    	}
    }
}
