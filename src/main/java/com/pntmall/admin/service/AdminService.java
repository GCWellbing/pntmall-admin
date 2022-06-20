package com.pntmall.admin.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Admin;
import com.pntmall.admin.domain.AdminLog;
import com.pntmall.admin.domain.AdminSearch;
import com.pntmall.admin.domain.MenuAuth;

@Service
public class AdminService {
    public static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Admin> getList(AdminSearch adminSearch) {
    	return sst.selectList("Admin.list", adminSearch);
    }

    public Integer getCount(AdminSearch adminSearch) {
    	return sst.selectOne("Admin.count", adminSearch);
    }

    public Admin getInfo(Integer adminNo) {
    	return sst.selectOne("Admin.info", adminNo);
    }

    public Admin getInfo(String adminId) {
    	return sst.selectOne("Admin.infoById", adminId);
    }

    public boolean isExists(String adminId) {
        return getInfo(adminId) != null;
    }

    public void createAccessLog(AdminLog adminLog) {
    	sst.insert("Admin.insertLog", adminLog);
    }
    
    public void modifyFailCnt(Admin admin) {
    	sst.update("Admin.updateFailCnt", admin);
    }
    
    @Transactional
    public void create(Admin admin, String[] menuNos) {
    	sst.insert("Admin.insert", admin);
    	
    	for(String menuNo :  menuNos) {
    		MenuAuth menuAuth = new MenuAuth();
    		menuAuth.setAdminNo(admin.getAdminNo());
    		menuAuth.setMenuNo(Integer.parseInt(menuNo));
    		
    		sst.insert("Menu.insertMenuAuth", menuAuth);
    	}
    }

    @Transactional
    public void modify(Admin admin, String[] menuNos) {
    	if(StringUtils.isEmpty(admin.getUpdateAuth())) {
    		admin.setUpdateAuth("N");
    	}
    	
    	sst.update("Admin.update", admin);
    	sst.delete("Menu.deleteMenuAuth", admin.getAdminNo());
    	
    	for(String menuNo :  menuNos) {
    		MenuAuth menuAuth = new MenuAuth();
    		menuAuth.setAdminNo(admin.getAdminNo());
    		menuAuth.setMenuNo(Integer.parseInt(menuNo));
    		
    		sst.insert("Menu.insertMenuAuth", menuAuth);
    	}
    }
 
    public void modify(Admin admin) {
    	sst.update("Admin.update", admin);
    }

}
