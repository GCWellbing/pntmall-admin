package com.pntmall.admin.service;

import com.pntmall.admin.domain.HealthExcludeProduct;
import com.pntmall.common.type.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HealthExcludeProductService {
	public static final Logger logger = LoggerFactory.getLogger(HealthExcludeProductService.class);

	@Autowired
	private SqlSessionTemplate sst;

	public List<HealthExcludeProduct> getList() {
		return sst.selectList("HealthExcludeProduct.list");
	}

	@Transactional
	public void create(Param param, Integer adminNo) {

		sst.update("HealthExcludeProduct.delete");

		String[] pno = param.getValues("pno");
		String[] gubun = param.getValues("gubun");
		for (int idx = 0; idx < pno.length; idx++) {
			HealthExcludeProduct product = new HealthExcludeProduct();
			product.setPno(Integer.parseInt(pno[idx]));
			product.setGubun(gubun[idx]);
			product.setCuser(adminNo);
			product.setUuser(adminNo);
			sst.insert("HealthExcludeProduct.insert", product);
		}
	}
}
