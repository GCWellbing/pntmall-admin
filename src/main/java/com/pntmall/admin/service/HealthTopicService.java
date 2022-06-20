package com.pntmall.admin.service;

import com.pntmall.admin.domain.HealthTopic;
import com.pntmall.common.type.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HealthTopicService {
	public static final Logger logger = LoggerFactory.getLogger(HealthTopicService.class);

	@Autowired
	private SqlSessionTemplate sst;

	public List<HealthTopic> getList() {
		return sst.selectList("HealthTopic.list");
	}

	public HealthTopic getInfo(Integer healthNo) {
		return sst.selectOne("HealthTopic.info", healthNo);
	}

	public void create(HealthTopic healthTopic) {
		sst.insert("HealthTopic.insert", healthTopic);
	}

	public void modify(HealthTopic healthTopic) {
		sst.update("HealthTopic.update", healthTopic);
	}

	public void remove(HealthTopic healthTopic) {
		sst.update("HealthTopic.delete", healthTopic);
	}

	@Transactional
	public void modifyRank(Param param, Integer adminNo) {
		String[] healthNos = param.getValues("healthNo");
		for (String healthNo : healthNos) {
			HealthTopic healthTopic = new HealthTopic();
			healthTopic.setHealthNo(Integer.parseInt(healthNo));
			healthTopic.setRank(param.getInt("rank" + healthNo));
			healthTopic.setCuser(adminNo);
			sst.update("HealthTopic.updateRank", healthTopic);
		}
	}

}
