package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Menu;

@Service
public class CacheService {
    public static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    private SqlSessionTemplate sst;

    @Cacheable(value="com.pntmall.admin.service.MenuCacheService.getAuthList", key="#adminNo", cacheManager="every5Minutes")
    public List<Menu> getAuthList(int adminNo) {
        return sst.selectList("Menu.authList", adminNo);
    }

    @Cacheable(value="com.pntmall.admin.service.MenuCacheService.findMenuList", key="#url", cacheManager="every5Minutes")
    public List<Menu> findMenuList(String url) {
        return sst.selectList("Menu.findMenuList", url);
    }
}
