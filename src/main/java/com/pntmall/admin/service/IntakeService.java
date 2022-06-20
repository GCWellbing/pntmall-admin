package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Intake;

@Service
public class IntakeService {
    public static final Logger logger = LoggerFactory.getLogger(IntakeService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Intake> getList() {
    	return getList(null);
    }

    public List<Intake> getList(String content) {
    	return sst.selectList("Intake.list", content);
    }

    public Intake getInfo(Integer intakeNo) {
    	return sst.selectOne("Intake.info", intakeNo);
    }

    public void create(Intake intake) {
    	sst.insert("Intake.insert", intake);
    }

    public void modify(Intake intake) {
    	sst.update("Intake.update", intake);
    }
    
    public void remove(Intake intake) {
    	sst.update("Intake.delete", intake);
    }
}
