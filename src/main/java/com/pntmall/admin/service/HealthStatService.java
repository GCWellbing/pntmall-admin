package com.pntmall.admin.service;

import com.pntmall.common.type.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthStatService {
    public static final Logger logger = LoggerFactory.getLogger(HealthStatService.class);

    @Autowired
    private SqlSessionTemplate sst;
    
	public List<Param> getHealthCheckList(Param param) {
    	return sst.selectList("HealthStat.healthCheckList", param);
    }
	public List<Param> getHealthTopicList(Param param) {
    	return sst.selectList("HealthStat.healthTopicList", param);
    }

    public List<Param> getHealthTopicList2(Param param) {
        return sst.selectList("HealthStat.healthTopicList2", param);
    }
}
