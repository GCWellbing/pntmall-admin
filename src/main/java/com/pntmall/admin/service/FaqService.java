package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Faq;
import com.pntmall.admin.domain.FaqSearch;

@Service
public class FaqService {
    public static final Logger logger = LoggerFactory.getLogger(FaqService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Faq> getList(FaqSearch faqSearch) {
    	return sst.selectList("Faq.list",faqSearch);
    }

    public Integer getCount(FaqSearch faqSearch) {
    	return sst.selectOne("Faq.count",faqSearch);
    }

    public Faq getInfo(Integer faqNo) {
    	return sst.selectOne("Faq.info", faqNo);
    }

    public void create(Faq faq) {
    	sst.insert("Faq.insert", faq);
    }

    public void modify(Faq faq) {
    	sst.update("Faq.update", faq);
    }

}
