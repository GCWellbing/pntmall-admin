package com.pntmall.admin.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.PgBill;
import com.pntmall.admin.domain.PgBillSearch;

@Service
public class PgBillService {
    public static final Logger logger = LoggerFactory.getLogger(PgBillService.class);

    @Autowired
    private SqlSessionTemplate sst;
    
    public List<PgBill> getList(PgBillSearch pgBillSearch) {
    	return sst.selectList("PgBill.list", pgBillSearch);
    }
    
    public Integer getCount(PgBillSearch pgBillSearch) {
    	return sst.selectOne("PgBill.count", pgBillSearch);
    }

    @Transactional
    public void create(String url, int adminNo) throws MalformedURLException, IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(new URL(url).openStream());
		
		for(int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			
			for(int j = 2; j < sheet.getPhysicalNumberOfRows(); j++) {
				Row row = sheet.getRow(j);
				PgBill pgBill = new PgBill();
				pgBill.setIssueDate(row.getCell(1).getStringCellValue());
				pgBill.setSaleDate(row.getCell(2).getStringCellValue());
				pgBill.setShopId(row.getCell(3).getStringCellValue());
				pgBill.setPayType(row.getCell(4).getStringCellValue());
				pgBill.setPayStatus(row.getCell(5).getStringCellValue());
				pgBill.setCode(row.getCell(6).getStringCellValue());
				pgBill.setPayOrgan(row.getCell(7).getStringCellValue());
				pgBill.setPayDate(row.getCell(8).getStringCellValue());
				pgBill.setOrderid(row.getCell(9).getStringCellValue());
				pgBill.setAmt((int) row.getCell(10).getNumericCellValue());
				pgBill.setFee((int) row.getCell(11).getNumericCellValue());
				pgBill.setIssueAmt((int) row.getCell(12).getNumericCellValue());
				pgBill.setAuthNo(row.getCell(13).getStringCellValue());
				pgBill.setBuyer(row.getCell(14).getStringCellValue());
				pgBill.setBuyerId(row.getCell(15).getStringCellValue());
				pgBill.setReqDate(row.getCell(16).getStringCellValue());
				pgBill.setBigo(row.getCell(17).getStringCellValue());
				pgBill.setPname(row.getCell(18).getStringCellValue());
				pgBill.setDealNo(row.getCell(19).getStringCellValue());
				pgBill.setCuser(adminNo);
				
		    	sst.insert("PgBill.insert", pgBill);
			}
		}
    }
    
    public List<PgBill> getListForSap(String[] sno) {
    	return sst.selectList("PgBill.listForSap", sno);
    }
    
    public void modifySapResult(PgBill pgBill) {
    	sst.update("PgBill.updateSap", pgBill);
    }
}
