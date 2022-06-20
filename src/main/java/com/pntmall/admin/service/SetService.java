package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Set;
import com.pntmall.admin.domain.SetProduct;
import com.pntmall.admin.domain.SetSearch;
import com.pntmall.common.type.Param;

@Service
public class SetService {
    public static final Logger logger = LoggerFactory.getLogger(SetService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Set> getList(SetSearch setSearch) {
    	return sst.selectList("Set.list",setSearch);
    }

    public Integer getCount(SetSearch setSearch) {
    	return sst.selectOne("Set.count",setSearch);
    }

    public Set getInfo(Integer sno) {
    	return sst.selectOne("Set.info", sno);
    }

    public List<SetProduct> getProductList(Integer sno) {
    	return sst.selectList("Set.productList",sno);
    }

    @Transactional
    public void create(Set set, Param param) {
    	sst.insert("Set.insert", set);
    	Integer sno = set.getSno();

    	// 전시 제품
    	String[] pno = param.getValues("pno");
    	for(int i = 0; i < pno.length; i++) {
    		SetProduct setProduct = new SetProduct();
    		setProduct.setSno(sno);
    		setProduct.setPno(Integer.parseInt(pno[i]));
    		setProduct.setRank(i);
    		sst.insert("Set.insertProduct", setProduct);
    	}
    }

    @Transactional
    public void modify(Set set, Param param) {
    	sst.update("Set.update", set);
    	Integer sno = set.getSno();

    	// 전시 제품
    	sst.delete("Set.deleteProduct", sno);
    	String[] pno = param.getValues("pno");
    	for(int i = 0; i < pno.length; i++) {
    		SetProduct setProduct = new SetProduct();
    		setProduct.setSno(sno);
    		setProduct.setPno(Integer.parseInt(pno[i]));
    		setProduct.setRank(i);
    		sst.insert("Set.insertProduct", setProduct);
    	}
    }


}
