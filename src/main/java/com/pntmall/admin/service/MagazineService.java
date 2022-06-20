package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Magazine;
import com.pntmall.admin.domain.MagazineProduct;
import com.pntmall.admin.domain.MagazineSearch;
import com.pntmall.admin.domain.SetProduct;
import com.pntmall.common.type.Param;

@Service
public class MagazineService {
    public static final Logger logger = LoggerFactory.getLogger(MagazineService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Magazine> getList(MagazineSearch magazineSearch) {
    	return sst.selectList("Magazine.list",magazineSearch);
    }

    public Integer getCount(MagazineSearch magazineSearch) {
    	return sst.selectOne("Magazine.count",magazineSearch);
    }


    public Magazine getInfo(Integer mno) {
    	return sst.selectOne("Magazine.info", mno);
    }

    public List<MagazineProduct> getProductList(Integer mno) {
    	return sst.selectList("Magazine.productList",mno);
    }

    @Transactional
    public void create(Magazine magazine, Param param) {
    	sst.insert("Magazine.insert", magazine);
    	Integer mno = magazine.getMno();

    	// 전시 제품
    	String[] pno = param.getValues("pno");
    	for(int i = 0; i < pno.length; i++) {
    		MagazineProduct magazineProduct = new MagazineProduct();
    		magazineProduct.setMno(mno);
    		magazineProduct.setPno(Integer.parseInt(pno[i]));
    		magazineProduct.setRank(i);
    		sst.insert("Magazine.insertProduct", magazineProduct);
    	}

    }

    @Transactional
    public void modify(Magazine magazine, Param param) {
    	sst.update("Magazine.update", magazine);
    	Integer mno = magazine.getMno();

    	// 전시 제품
    	sst.delete("Magazine.deleteProduct", mno);
    	String[] pno = param.getValues("pno");
    	for(int i = 0; i < pno.length; i++) {
    		MagazineProduct magazineProduct = new MagazineProduct();
    		magazineProduct.setMno(mno);
    		magazineProduct.setPno(Integer.parseInt(pno[i]));
    		magazineProduct.setRank(i);
    		sst.insert("Magazine.insertProduct", magazineProduct);
    	}

    }


}
