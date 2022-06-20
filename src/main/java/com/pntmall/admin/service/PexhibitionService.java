package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Pexhibition;
import com.pntmall.admin.domain.PexhibitionSearch;
import com.pntmall.admin.domain.SeProduct;
import com.pntmall.common.type.Param;

@Service
public class PexhibitionService {
    public static final Logger logger = LoggerFactory.getLogger(PexhibitionService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Pexhibition> getList(PexhibitionSearch pexhibitionSearch) {
    	return sst.selectList("Pexhibition.list",pexhibitionSearch);
    }

    public Integer getCount(PexhibitionSearch pexhibitionSearch) {
    	return sst.selectOne("Pexhibition.count",pexhibitionSearch);
    }


    public Pexhibition getInfo(Integer seno) {
    	return sst.selectOne("Pexhibition.info", seno);
    }

    public List<SeProduct> getProductList(Integer seno) {
    	return sst.selectList("Pexhibition.productList",seno);
    }

    @Transactional
    public void create(Pexhibition pexhibition, Param param) {
    	sst.insert("Pexhibition.insert", pexhibition);
    	Integer seno = pexhibition.getSeno();

    	// 전시 제품
    	String[] pno = param.getValues("pno");
    	for(int i = 0; i < pno.length; i++) {
    		SeProduct seProduct = new SeProduct();
    		seProduct.setSeno(seno);
    		seProduct.setPno(Integer.parseInt(pno[i]));
    		seProduct.setRank(i);
    		sst.insert("Pexhibition.insertProduct", seProduct);
    	}
    }

    @Transactional
    public void modify(Pexhibition pexhibition, Param param) {
    	sst.update("Pexhibition.update", pexhibition);
    	Integer seno = pexhibition.getSeno();

    	// 전시 제품
    	sst.delete("Pexhibition.deleteProduct", seno);
    	String[] pno = param.getValues("pno");
    	for(int i = 0; i < pno.length; i++) {
    		SeProduct seProduct = new SeProduct();
    		seProduct.setSeno(seno);
    		seProduct.setPno(Integer.parseInt(pno[i]));
    		seProduct.setRank(i);
    		sst.insert("Pexhibition.insertProduct", seProduct);
    	}
    }


}
