package com.pntmall.admin.service;

import com.pntmall.admin.domain.HealthExample;
import com.pntmall.admin.domain.HealthQuestion;
import com.pntmall.common.type.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HealthQuestionService {
	public static final Logger logger = LoggerFactory.getLogger(HealthQuestionService.class);

	@Autowired
	private SqlSessionTemplate sst;

	public HealthQuestion getInfo(Integer healthNo) {
		return sst.selectOne("HealthQuestion.info", healthNo);
	}

	@Transactional
	public void create(HealthQuestion healthQuestion, Param param) {
		HealthExample example = new HealthExample();

		sst.insert("HealthQuestion.insert", healthQuestion);

		if (!healthQuestion.getType().equals("I")) { //유형 : 입력 외
			example.setHealthNo(healthQuestion.getHealthNo());

			String[] exNos = param.getValues("exNo");
			for (String exNo : exNos) {
				example.setExNo(Integer.parseInt(exNo));
				example.setExample(param.get("example" + exNo));
				example.setPoint(param.getInt("point" + exNo));
				example.setProductFun(param.get("productFun" + exNo));
				example.setReport(param.get("report" + exNo));
				example.setWarning(param.get("warning" + exNo));
				example.setRank(param.getInt("rank" + exNo));
				sst.insert("HealthQuestion.insertExample", example);

				String[] pNos = param.getValues("pno" + exNo);
				for (String pNo : pNos) {
					example.setPno(Integer.parseInt(pNo));
					sst.insert("HealthQuestion.insertProduct", example);
				}

				String[] nutritionNos = param.getValues("nutritionNo" + exNo);
				for (String nutritionNo : nutritionNos) {
					example.setNutritionNo(Integer.parseInt(nutritionNo));
					sst.insert("HealthQuestion.insertNutrition", example);
				}
			}
		}
	}

	@Transactional
	public void modify(HealthQuestion healthQuestion, Param param) {
		HealthExample example = new HealthExample();

		sst.update("HealthQuestion.update", healthQuestion);

		sst.update("HealthQuestion.deleteExample", healthQuestion.getHealthNo());
		sst.update("HealthQuestion.deleteProduct", healthQuestion.getHealthNo());
		sst.update("HealthQuestion.deleteNutrition", healthQuestion.getHealthNo());

		if (!healthQuestion.getType().equals("I")) { //유형 : 입력 외
			example.setHealthNo(healthQuestion.getHealthNo());

			String[] exNos = param.getValues("exNo");

			for (String exNo : exNos) {
				example.setExNo(Integer.parseInt(exNo));
				example.setExample(param.get("example" + exNo));
				example.setPoint(param.getInt("point" + exNo));
				example.setProductFun(param.get("productFun" + exNo));
				example.setReport(param.get("report" + exNo));
				example.setWarning(param.get("warning" + exNo));
				example.setRank(param.getInt("rank" + exNo));

				sst.insert("HealthQuestion.insertExample", example);

				String[] pNos = param.getValues("pno" + exNo);
				for (String pNo : pNos) {
					example.setPno(Integer.parseInt(pNo));
					sst.insert("HealthQuestion.insertProduct", example);
				}

				String[] nutritionNos = param.getValues("nutritionNo" + exNo);
				for (String nutritionNo : nutritionNos) {
					example.setNutritionNo(Integer.parseInt(nutritionNo));
					sst.insert("HealthQuestion.insertNutrition", example);
				}
			}
		}
	}

	public List<HealthExample> getExampleList(Integer healthNo) {
		return sst.selectList("HealthQuestion.listExample", healthNo);
	}

	public List<HealthExample> getProductList(Integer healthNo) {
		return sst.selectList("HealthQuestion.listProduct", healthNo);
	}

	public List<HealthExample> getNutritionList(Integer healthNo) {
		return sst.selectList("HealthQuestion.listNutrition", healthNo);
	}

}
