package com.pntmall.admin.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Seq;
import com.pntmall.common.utils.Utils;

@Service
public class SeqService {
    public static final Logger logger = LoggerFactory.getLogger(SeqService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public Seq create() {
    	Seq seq = new Seq();
    	sst.insert("Seq.insert", seq);
    	return seq;
    }
    
    public String getId() {
    	Seq seq = create();
    	String id = Utils.getTimeStampString("yyMMddHHmmss") + String.format("%04d", seq.getSeq() % 10000);
    	return id;
    }

    @Cacheable(value="com.pntmall.admin.service.SeqService.test", cacheManager="everyMinute")
    public String test() {
    	return sst.selectOne("Seq.test");
    }

    public String test2() {
    	return sst.selectOne("Seq.test");
    }

}
