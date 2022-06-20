package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Category;

@Service
public class CategoryService {
    public static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Category> getList(Integer pcateNo) {
    	return sst.selectList("Category.list", pcateNo);
    }

    public Category getInfo(Integer cateNo) {
    	return sst.selectOne("Category.info", cateNo);
    }

    public void create(Category category) {
    	sst.insert("Category.insert", category);
    }

    public void modify(Category category) {
    	sst.update("Category.update", category);
    }
    
    public void remove(Category category) {
    	sst.update("Category.delete", category);
    }
    
    @Transactional
    public void modifyRank(String[] cateNo, Integer adminNo) {
    	for(int i = 0; i < cateNo.length; i++) {
    		Category category = new Category();
    		category.setCateNo(Integer.parseInt(cateNo[i]));
    		category.setRank(i + 1);
    		category.setCuser(adminNo);
    		sst.update("Category.updateRank", category);
    	}
    }
    
    public List<Category> getAllList() {
    	return sst.selectList("Category.allList");
    }
}
