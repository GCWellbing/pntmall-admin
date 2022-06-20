package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Stipulation;
import com.pntmall.admin.domain.StipulationSearch;

@Service
public class StipulationService {
    public static final Logger logger = LoggerFactory.getLogger(StipulationService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Stipulation> getList(StipulationSearch stipulationSearch) {
    	return sst.selectList("Stipulation.list",stipulationSearch);
    }

    public Integer getCount(StipulationSearch stipulationSearch) {
    	return sst.selectOne("Stipulation.count",stipulationSearch);
    }

    public Stipulation getInfo(Integer stipulationNo) {
    	return sst.selectOne("Stipulation.info", stipulationNo);
    }

    public void create(Stipulation stipulation) {
    	sst.insert("Stipulation.insert", stipulation);
    }

    public void modify(Stipulation stipulation) {
    	sst.update("Stipulation.update", stipulation);
    }

}
