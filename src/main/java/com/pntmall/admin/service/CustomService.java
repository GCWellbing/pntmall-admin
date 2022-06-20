package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Custom;
import com.pntmall.admin.domain.CustomProduct;
import com.pntmall.admin.domain.CustomSearch;
import com.pntmall.common.type.Param;

@Service
public class CustomService {
    public static final Logger logger = LoggerFactory.getLogger(CustomService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Custom> getList(CustomSearch customSearch) {
    	return sst.selectList("Custom.list",customSearch);
    }

    public Integer getCount(CustomSearch customSearch) {
    	return sst.selectOne("Custom.count",customSearch);
    }


    public Custom getInfo(Integer cno) {
    	return sst.selectOne("Custom.info", cno);
    }

    public List<CustomProduct> getProductList(Integer cno) {
    	return sst.selectList("Custom.productList",cno);
    }

    @Transactional
    public void create(Custom custom, Param param) {
    	sst.insert("Custom.insert", custom);
    	Integer cno = custom.getCno();
    	// 전시 제품
    	String[] pno = param.getValues("pno");
    	String[] ages = param.getValues("ages");

    	for(int i = 0; i < pno.length; i++) {
    		CustomProduct customProduct = new CustomProduct();
    		customProduct.setCno(cno);
    		customProduct.setPno(Integer.parseInt(pno[i]));
    		customProduct.setAges(Integer.parseInt(ages[i]));
    		customProduct.setRank(i);
    		sst.insert("Custom.insertProduct", customProduct);
    	}

    }

    @Transactional
    public void modify(Custom custom, Param param) {
    	sst.update("Custom.update", custom);
    	Integer cno = custom.getCno();

    	// 전시 제품
    	sst.delete("Custom.deleteProduct", cno);
    	String[] pno = param.getValues("pno");
    	String[] ages = param.getValues("ages");

    	for(int i = 0; i < pno.length; i++) {
    		CustomProduct customProduct = new CustomProduct();
    		customProduct.setCno(cno);
    		customProduct.setPno(Integer.parseInt(pno[i]));
    		customProduct.setAges(Integer.parseInt(ages[i]));
    		customProduct.setRank(i);
    		sst.insert("Custom.insertProduct", customProduct);
    	}
    }


}
