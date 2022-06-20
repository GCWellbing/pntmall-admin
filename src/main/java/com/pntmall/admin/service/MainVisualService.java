package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.MainVisual;
import com.pntmall.admin.domain.MainVisualSearch;
import com.pntmall.common.type.Param;

@Service
public class MainVisualService {
    public static final Logger logger = LoggerFactory.getLogger(MainVisualService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<MainVisual> getList(MainVisualSearch mainVisualSearch) {
    	return sst.selectList("MainVisual.list",mainVisualSearch);
    }

    public Integer getCount(MainVisualSearch mainVisualSearch) {
    	return sst.selectOne("MainVisual.count",mainVisualSearch);
    }


    public MainVisual getInfo(Integer mvNo) {
    	return sst.selectOne("MainVisual.info", mvNo);
    }

    @Transactional
    public void create(MainVisual mainVisual, Param param) {
    	sst.insert("MainVisual.insert", mainVisual);

    }

    @Transactional
    public void modify(MainVisual mainVisual, Param param) {
    	sst.update("MainVisual.update", mainVisual);

    }


}
