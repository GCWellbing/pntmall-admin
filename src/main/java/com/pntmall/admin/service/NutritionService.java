package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Nutrition;

@Service
public class NutritionService {
    public static final Logger logger = LoggerFactory.getLogger(NutritionService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Nutrition> getList() {
    	return getList("");
    }

    public List<Nutrition> getList(String name) {
    	Nutrition nutrition = new Nutrition();
    	nutrition.setName(name);
    	return getList(nutrition);
    }

    public List<Nutrition> getList(Nutrition nutrition) {
    	return sst.selectList("Nutrition.list", nutrition);
    }

    public Nutrition getInfo(Integer nutritionNo) {
    	return sst.selectOne("Nutrition.info", nutritionNo);
    }

    public void create(Nutrition nutrition) {
    	sst.insert("Nutrition.insert", nutrition);
    }

    public void modify(Nutrition nutrition) {
    	sst.update("Nutrition.update", nutrition);
    }
    
    public void remove(Nutrition nutrition) {
    	sst.update("Nutrition.delete", nutrition);
    }
}
