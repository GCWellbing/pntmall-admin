package com.pntmall.admin.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.AppPush;
import com.pntmall.admin.domain.AppPushExcel;
import com.pntmall.admin.domain.AppPushSearch;
import com.pntmall.admin.domain.AppPushTarget;

@Service
public class AppPushService {
    public static final Logger logger = LoggerFactory.getLogger(AppPushService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<AppPush> getList(AppPushSearch appPushSearch) {
    	return sst.selectList("AppPush.list", appPushSearch);
    }

    public Integer getCount(AppPushSearch appPushSearch) {
    	return sst.selectOne("AppPush.count", appPushSearch);
    }

    public AppPush getInfo(Integer pno) {
    	return sst.selectOne("AppPush.info", pno);
    }

    @Transactional
    public void create(AppPush appPush) throws MalformedURLException, IOException {
    	sst.insert("AppPush.insert", appPush);
    	sst.insert("AppPush.insertLog", appPush);
    	
    	if(appPush.getTargetGubun() == 2) {	// excel
    		int pno = appPush.getPno();
    		sst.delete("AppPush.deleteExcel", pno);
    		
    		XSSFWorkbook workbook = new XSSFWorkbook(new URL(appPush.getExcel()).openStream());
    		
    		for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
    			Sheet sheet = workbook.getSheetAt(i);
    			
    			for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
    				Row row = sheet.getRow(j);
    				
    				AppPushExcel appPushExcel = new AppPushExcel();
    				appPushExcel.setPno(pno);
    				Cell cell = row.getCell(0);
    				if(cell.getCellType() == CellType.NUMERIC) {
    					appPushExcel.setMemId(Integer.toString((int)row.getCell(0).getNumericCellValue()));
    				} else {
    					appPushExcel.setMemId(row.getCell(0).getStringCellValue());
    				}
    				sst.update("AppPush.insertExcel", appPushExcel);
    			}
    		}
    	}
    }

    @Transactional
    public void modify(AppPush appPush) throws MalformedURLException, IOException {
    	sst.insert("AppPush.update", appPush);
    	
    	if(appPush.getTargetGubun() == 2) {	// excel
    		int pno = appPush.getPno();
    		sst.delete("AppPush.deleteExcel", pno);
    		
    		XSSFWorkbook workbook = new XSSFWorkbook(new URL(appPush.getExcel()).openStream());
    		
    		for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
    			Sheet sheet = workbook.getSheetAt(i);
    			
    			for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
    				Row row = sheet.getRow(j);
    				
    				AppPushExcel appPushExcel = new AppPushExcel();
    				appPushExcel.setPno(pno);
    				Cell cell = row.getCell(0);
    				if(cell.getCellType() == CellType.NUMERIC) {
    					appPushExcel.setMemId(Integer.toString((int)row.getCell(0).getNumericCellValue()));
    				} else {
    					appPushExcel.setMemId(row.getCell(0).getStringCellValue());
    				}
    				sst.update("AppPush.insertExcel", appPushExcel);
    			}
    		}
    		
    	}
    }
    
    @Transactional
    public void modifyStatus(AppPush appPush) throws MalformedURLException, IOException {
    	sst.insert("AppPush.updateStatus", appPush);
    	sst.insert("AppPush.insertLog", appPush);
    }
    
    public Integer getTargetCount1(AppPush appPush) {
    	return sst.selectOne("AppPush.targetCount1", appPush);
    }

    public Integer getTargetCount2(Integer pno) {
    	return sst.selectOne("AppPush.targetCount2", pno);
    }

    public List<AppPushTarget> getTargetList(AppPushTarget appPushTarget) {
    	return sst.selectList("AppPush.targetList", appPushTarget);
    }

    public Integer getTargetCount(AppPushTarget appPushTarget) {
    	return sst.selectOne("AppPush.targetCount", appPushTarget);
    }
}
