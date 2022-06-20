package com.pntmall.admin.service;

import com.pntmall.admin.domain.PopUp;
import com.pntmall.admin.domain.PopUpSearch;
import com.pntmall.common.type.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PopUpService {
    public static final Logger logger = LoggerFactory.getLogger(PopUpService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<PopUp> getList(PopUpSearch popUpSearch) {
        return sst.selectList("PopUp.list", popUpSearch);
    }

    public Integer getCount(PopUpSearch popUpSearch) {
        return sst.selectOne("PopUp.count", popUpSearch);
    }

    public PopUp getInfo(Integer popupid) {
        return sst.selectOne("PopUp.info", popupid);
    }

    @Transactional
    public void create(PopUp popUp, Param param) {
        sst.insert("PopUp.insert", popUp);
    }

    @Transactional
    public void modify(PopUp popUp, Param param) {
        sst.update("PopUp.update", popUp);
    }
}
