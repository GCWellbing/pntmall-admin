package com.pntmall.admin.service;

import com.pntmall.admin.domain.MyHealth;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyHealthService {
    public static final Logger logger = LoggerFactory.getLogger(MyHealthService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<MyHealth> getList(MyHealth myHealth) {
    	return sst.selectList("MyHealth.list",myHealth);
    }

    public Integer getCount(MyHealth myHealth) {
    	return sst.selectOne("MyHealth.count",myHealth);
    }

}
