package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Pbanner;
import com.pntmall.admin.domain.PbannerProduct;
import com.pntmall.admin.domain.PbannerSearch;
import com.pntmall.common.type.Param;

@Service
public class PbannerService {
    public static final Logger logger = LoggerFactory.getLogger(PbannerService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Pbanner> getList(PbannerSearch pbannerSearch) {
    	return sst.selectList("Pbanner.list",pbannerSearch);
    }

    public Integer getCount(PbannerSearch pbannerSearch) {
    	return sst.selectOne("Pbanner.count",pbannerSearch);
    }

    public Pbanner getInfo(Integer bno) {
    	return sst.selectOne("Pbanner.info", bno);
    }

    public List<PbannerProduct> getProductList(Integer bno) {
    	return sst.selectList("Pbanner.productList",bno);
    }

    @Transactional
    public void create(Pbanner pbanner, Param param) {
    	sst.insert("Pbanner.insert", pbanner);
    	Integer bno = pbanner.getBno();

    	// 전시 제품
    	String[] pno = param.getValues("pno");
    	for(int i = 0; i < pno.length; i++) {
    		PbannerProduct pbannerProduct = new PbannerProduct();
    		pbannerProduct.setBno(bno);
    		pbannerProduct.setPno(Integer.parseInt(pno[i]));
    		sst.insert("Pbanner.insertProduct", pbannerProduct);
    	}
    }

    @Transactional
    public void modify(Pbanner pbanner, Param param) {
    	sst.update("Pbanner.update", pbanner);
    	Integer bno = pbanner.getBno();

    	// 전시 제품
    	sst.delete("Pbanner.deleteProduct", bno);
    	String[] pno = param.getValues("pno");
    	for(int i = 0; i < pno.length; i++) {
    		PbannerProduct pbannerProduct = new PbannerProduct();
    		pbannerProduct.setBno(bno);
    		pbannerProduct.setPno(Integer.parseInt(pno[i]));
    		sst.insert("Pbanner.insertProduct", pbannerProduct);
    	}
    }


}
