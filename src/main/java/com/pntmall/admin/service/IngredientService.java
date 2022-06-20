package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Ingredient;
import com.pntmall.admin.domain.IngredientSearch;
import com.pntmall.common.type.Param;

@Service
public class IngredientService {
    public static final Logger logger = LoggerFactory.getLogger(IngredientService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Ingredient> getList(IngredientSearch ingredientSearch) {
    	return sst.selectList("Ingredient.list",ingredientSearch);
    }

    public Integer getCount(IngredientSearch ingredientSearch) {
    	return sst.selectOne("Ingredient.count",ingredientSearch);
    }


    public Ingredient getInfo(Integer ino) {
    	return sst.selectOne("Ingredient.info", ino);
    }

    @Transactional
    public void create(Ingredient ingredient, Param param) {
    	sst.insert("Ingredient.insert", ingredient);

    }

    @Transactional
    public void modify(Ingredient ingredient, Param param) {
    	sst.update("Ingredient.update", ingredient);

    }


}
